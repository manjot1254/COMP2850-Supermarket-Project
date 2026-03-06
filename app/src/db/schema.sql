-- Tables must be created in a specific order

-- E-Commerce Side
CREATE TABLE categories (
  category_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  description text
);

CREATE TABLE users (
  user_id INT PRIMARY KEY AUTO_INCREMENT,   
  first_name VARCHAR(255) NOT NULL,
  last_name VARCHAR(255),
  email_address VARCHAR(255) NOT NULL UNIQUE,
  phone_number VARCHAR(255) NOT NULL,
  password_hash VARCHAR(255) NOT NULL UNIQUE,
  role VARCHAR(255) NOT NULL,
  created_at DATETIME NOT NULL
);

CREATE TABLE products (
  product_id INT PRIMARY KEY AUTO_INCREMENT,
  category_id INT NOT NULL,
  name VARCHAR(255) NOT NULL,
  location VARCHAR(255) NOT NULL,
  description TEXT,
  price DECIMAL(10,2) NOT NULL,
  volume_per_unit DECIMAL(8,2) NOT NULL,
  image_url VARCHAR(255),
  created_at DATETIME NOT NULL,

  FOREIGN KEY (category_id)
    REFERENCES categories(category_id) 
);

CREATE TABLE user_favourites (
  user_id INTEGER,
  product_id INTEGER,
  created_at DATETIME NOT NULL
);

CREATE TABLE addresses (
  address_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  line_1 VARCHAR(255),
  line_2 VARCHAR(255),
  town VARCHAR(255),
  country VARCHAR(255),
  postcode VARCHAR(255),

  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE orders (
  order_id INT PRIMARY KEY AUTO_INCREMENT,
  user_id INT NOT NULL,
  address_id INT,
  total_price DECIMAL(10,2),
  delivery_slot DATETIME,
  order_status VARCHAR(255),
  created_at DATETIME NOT NULL,
  updated_at DATETIME,

  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
  FOREIGN KEY (address_id)
    REFERENCES addresses(address_id)
);

CREATE TABLE payments (
  payment_id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT NOT NULL,
  payment_method VARCHAR(255),
  amount DECIMAL(10,2),
  status VARCHAR(255),
  transaction_reference VARCHAR(255),

  FOREIGN KEY (order_id)
    REFERENCES orders(order_id)
);

CREATE TABLE order_items (
  order_item_id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity_ordered INT,
  price DECIMAL(10,2),
  substituted_product_id INT,

  -- Check if valid substitution
  CHECK(substituted_product_id != product_id)

  FOREIGN KEY (order_id)
    REFERENCES orders(order_id)
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
  FOREIGN KEY (substituted_product_id)
    REFERENCES products(product_id)
);

CREATE TABLE shopping_basket (
  basket_id INT PRIMARY KEY AUTO_INCREMENT,
  order_id INT NOT NULL,
  created_at DATETIME NOT NULL,
  updated_at DATETIME,

  FOREIGN KEY (order_id)
    REFERENCES orders(order_id)
);

CREATE TABLE shopping_basket_items (
  basket_item_id INT PRIMARY KEY AUTO_INCREMENT,
  basket_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity_added INT,
  price DECIMAL(10,2),

  FOREIGN KEY (basket_id)
    REFERENCES shopping_basket(basket_id)
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

-- Warehousing
CREATE TABLE stock (
  -- Must be dynamically updated - multiple users will be updating simultaenously
  stock_id INT PRIMARY KEY AUTO_INCREMENT,
  product_id INT NOT NULL,
  warehouse_id INT NOT NULL,
  quantity INT,
  updated_at DATETIME,

  -- Check valid stock
  CHECK(quantity>=0)

  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
);

CREATE TABLE stock_movements (
  movement_id INT PRIMARY KEY AUTO_INCREMENT,
  warehouse_id INT NOT NULL,
  product_id INT NOT NULL,
  quantity_change INT,
  reason VARCHAR(255),
  created_at DATETIME NOT NULL,

  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

CREATE TABLE picking_list (
  -- User ID link for picker (warehouse worker)
  list_id INT PRIMARY KEY AUTO_INCREMENT,
  warehouse_id INT,
  user_id INT,
  status VARCHAR(255),
  created_at DATETIME NOT NULL,
  updated_at DATETIME,

  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE picking_list_items (
  list_item_id INT PRIMARY KEY AUTO_INCREMENT,
  list_id INT NOT NULL,
  user_id INT NOT NULL,
  product_id INT NOT NULL,
  picked_at DATETIME,
  quantity_picked INT,
  quantity_required INT,
  status VARCHAR(255),

  -- Check valid quantity ordered
  CHECK(quantity_required >= 0)

  FOREIGN KEY (list_id)
    REFERENCES picking_list(list_id)
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

CREATE TABLE warehouse (
  warehouse_id INT PRIMARY KEY AUTO_INCREMENT,
  name VARCHAR(255),
  location VARCHAR(255)
);

CREATE TABLE warehouse_capacity (
  warehouse_id INT,
  current_volume INT,
  max_volume INT,
  status VARCHAR(255)
);

CREATE TABLE deliveries (
  -- Manager must be able to update the stock (so links to user table)
  delivery_id INT PRIMARY KEY AUTO_INCREMENT,
  warehouse_id INT,
  user_id INT,
  supplier VARCHAR(255),
  received_at DATETIME,
  
  FOREIGN KEY (warehouse_id)
    REFERENCES warehouse(warehouse_id)
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE delivery_items (
  delivery_item_id INT PRIMARY KEY AUTO_INCREMENT,
  delivery_id INT NOT NULL,
  product_id INT,
  quantity_delivered INT,

  FOREIGN KEY (delivery_id)
    REFERENCES deliveries(delivery_id)
  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
);

-- Management & Marketing
CREATE TABLE product_views (
  -- Track user roles who viewed i.e. customer or worker? - used for recommendations
  view_id INT PRIMARY KEY AUTO_INCREMENT,
  product_id INT,
  user_id INT,
  viewed_at DATETIME,

  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);

CREATE TABLE product_ratings (
  rating_id INT PRIMARY KEY AUTO_INCREMENT,
  product_id INT,
  user_id INT,
  rating INT,
  review_text text,
  created_at DATETIME NOT NULL,

  -- Check if valid rating
  CHECK (RATING >= 0)
  CHECK (RATING <= 5)

  FOREIGN KEY (product_id)
    REFERENCES products(product_id)
  FOREIGN KEY (user_id)
    REFERENCES users(user_id)
);
