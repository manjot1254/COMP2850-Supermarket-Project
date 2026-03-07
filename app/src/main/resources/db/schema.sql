CREATE TABLE IF NOT EXISTS categories (
  category_id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(255),
  description text
);

CREATE TABLE IF NOT EXISTS users (
  user_id INTEGER PRIMARY KEY AUTOINCREMENT,   
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255),
  email_address VARCHAR(255) NOT NULL UNIQUE,
  phone_number VARCHAR(255) NOT NULL,
  password_hash VARCHAR(255) NOT NULL,
  role VARCHAR(100) NOT NULL,
  created_at DATETIME NOT NULL
);

CREATE TABLE IF NOT EXISTS user_sessions (
  session_id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  ip_address VARCHAR(50),
  created_at DATETIME NOT NULL,
  expires_at DATETIME NOT NULL,

  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS products (
  product_id INTEGER PRIMARY KEY AUTOINCREMENT,
  category_id INTEGER NOT NULL,
  name VARCHAR(255) NOT NULL,
  location VARCHAR(255) NOT NULL,
  description TEXT,
  barcode VARCHAR(50) UNIQUE NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  volume_per_unit DECIMAL(8,2) NOT NULL,
  image_url VARCHAR(255),
  created_at DATETIME NOT NULL,

  FOREIGN KEY (category_id)
    REFERENCES categories(category_id) 
);

CREATE TABLE IF NOT EXISTS user_favourites (
  user_id INTEGER,
  product_id INTEGER,
  created_at DATETIME NOT NULL,

  PRIMARY KEY (user_id, product_id),

  FOREIGN KEY (user_id)
    REFERENCES users(user_id),

  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

CREATE TABLE IF NOT EXISTS addresses (
  address_id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  line_1 VARCHAR(255),
  line_2 VARCHAR(255),
  town VARCHAR(100),
  country VARCHAR(100),
  postcode VARCHAR(100),

  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS orders (
  order_id INTEGER PRIMARY KEY AUTOINCREMENT,
  user_id INTEGER NOT NULL,
  address_id INTEGER,
  list_id INTEGER,
  total_price DECIMAL(10,2),
  delivery_slot DATETIME,
  order_status VARCHAR(255),
  created_at DATETIME NOT NULL,
  updated_at DATETIME,

  FOREIGN KEY (user_id)
    REFERENCES users(user_id),
  FOREIGN KEY (address_id)
    REFERENCES addresses(address_id),
  FOREIGN KEY (list_id)
    REFERENCES picking_list(list_id)
);

CREATE TABLE IF NOT EXISTS payments (
  payment_id INTEGER PRIMARY KEY AUTOINCREMENT,
  order_id INTEGER NOT NULL,
  payment_method VARCHAR(255),
  amount DECIMAL(10,2),
  payment_status VARCHAR(255),
  transaction_reference VARCHAR(255),

  FOREIGN KEY (order_id)
    REFERENCES orders(order_id)
);

CREATE TABLE IF NOT EXISTS order_items (
  order_item_id INTEGER PRIMARY KEY AUTOINCREMENT,
  order_id INTEGER NOT NULL,
  product_id INTEGER NOT NULL,
  quantity_ordered INTEGER,
  price DECIMAL(10,2),
  substituted_product_id INTEGER,

  -- Check if valid substitution and order
  CHECK(substituted_product_id != product_id),
  CHECK(quantity_ordered > 0),

  FOREIGN KEY (order_id)
    REFERENCES orders(order_id),
  FOREIGN KEY (product_id)
    REFERENCES products(product_id),
  FOREIGN KEY (substituted_product_id)
    REFERENCES products(product_id)
);

CREATE TABLE IF NOT EXISTS shopping_basket (
  basket_id INTEGER PRIMARY KEY AUTOINCREMENT,
  order_id INTEGER NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME,

  FOREIGN KEY (order_id)
    REFERENCES orders(order_id)
);

CREATE TABLE IF NOT EXISTS shopping_basket_items (
  basket_item_id INTEGER PRIMARY KEY AUTOINCREMENT,
  basket_id INTEGER NOT NULL,
  product_id INTEGER NOT NULL,
  quantity_added INTEGER,
  price DECIMAL(10,2),

  FOREIGN KEY (basket_id)
    REFERENCES shopping_basket(basket_id),
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

-- Warehousing
CREATE TABLE IF NOT EXISTS warehouse (
  warehouse_id INTEGER PRIMARY KEY AUTOINCREMENT,
  name VARCHAR(255),
  location VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS stock (
  -- Must be dynamically updated - multiple users will be updating simultaenously
  stock_id INTEGER PRIMARY KEY AUTOINCREMENT,
  product_id INTEGER NOT NULL,
  warehouse_id INTEGER NOT NULL,
  quantity INTEGER,
  updated_at DATETIME,

  -- Check valid stock
  CHECK(quantity>=0),

  -- One row per product per warehouse (prevents unnecessary duplicates)
  UNIQUE(product_id, warehouse_id),

  FOREIGN KEY (product_id)
    REFERENCES products(product_id),
  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
);

CREATE TABLE IF NOT EXISTS stock_movements (
  movement_id INTEGER PRIMARY KEY AUTOINCREMENT,
  warehouse_id INTEGER NOT NULL,
  product_id INTEGER NOT NULL,
  quantity_change INTEGER,
  reason VARCHAR(255),
  created_at DATETIME NOT NULL,

  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id),
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

CREATE TABLE IF NOT EXISTS picking_list (
  -- User ID link for picker (warehouse worker)
  list_id INTEGER PRIMARY KEY AUTOINCREMENT,
  warehouse_id INTEGER,
  user_id INTEGER,
  list_status VARCHAR(255),
  created_at DATETIME NOT NULL,
  updated_at DATETIME,

  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id),
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS picking_list_items (
  list_item_id INTEGER PRIMARY KEY AUTOINCREMENT,
  list_id INTEGER NOT NULL,
  user_id INTEGER NOT NULL,
  product_id INTEGER NOT NULL,
  picked_at DATETIME,
  quantity_picked INTEGER,
  quantity_required INTEGER,
  item_status VARCHAR(255),

  -- Check valid quantity ordered
  CHECK(quantity_required >= 0),

  FOREIGN KEY (list_id)
    REFERENCES picking_list(list_id),
  FOREIGN KEY (user_id)
    REFERENCES users(user_id),
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

CREATE TABLE IF NOT EXISTS warehouse_capacity (
  warehouse_id INTEGER PRIMARY KEY,
  current_volume INTEGER,
  max_volume INTEGER,
  capacity_status VARCHAR(255),

  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
);

CREATE TABLE IF NOT EXISTS deliveries (
  -- Manager must be able to update the stock (so links to user table)
  delivery_id INTEGER PRIMARY KEY AUTOINCREMENT,
  warehouse_id INTEGER,
  user_id INTEGER,
  supplier VARCHAR(255),
  received_at DATETIME,
  
  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id),
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS delivery_items (
  delivery_item_id INTEGER PRIMARY KEY AUTOINCREMENT,
  delivery_id INTEGER NOT NULL,
  product_id INTEGER,
  quantity_delivered INTEGER,

  FOREIGN KEY (delivery_id)
    REFERENCES deliveries(delivery_id),
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

-- Management & Marketing
CREATE TABLE IF NOT EXISTS product_views (
  -- Track user roles who viewed i.e. customer or worker? - used for recommendations
  view_id INTEGER PRIMARY KEY AUTOINCREMENT,
  product_id INTEGER,
  user_id INTEGER,
  viewed_at DATETIME,

  FOREIGN KEY (product_id)
    REFERENCES products(product_id),
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE IF NOT EXISTS product_ratings (
  rating_id INTEGER PRIMARY KEY AUTOINCREMENT,
  product_id INTEGER,
  user_id INTEGER,
  rating INTEGER,
  review_text text,
  created_at DATETIME NOT NULL,

  -- Check if valid rating
  CHECK(rating >= 0),
  CHECK(rating <= 5),

  FOREIGN KEY (product_id)
    REFERENCES products(product_id),
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);
