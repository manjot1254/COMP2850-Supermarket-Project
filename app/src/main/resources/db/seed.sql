INSERT OR IGNORE INTO categories (name, description)
VALUES
('Fruit', 'Fresh fruit products'),
('Vegetables', 'Fresh vegetables'),
('Dairy', 'Milk and dairy products'),
('Bakery', 'Bread and baked goods'),
('Meat', 'Fresh meat products'),
('Frozen', 'Frozen foods'),
('Drinks', 'Beverages and juices'),
('Pantry', 'Dry food and cooking ingredients');


INSERT OR IGNORE INTO users (first_name, last_name, email_address, phone_number, password_hash, role, created_at)
VALUES
('Joe', 'Smith', 'joe@warehouse.com', '07111111111', 'hashedpassword', 'worker', DATE('now')),
('Emma', 'Brown', 'emma@manager.com', '07222222222', 'hashedpassword', 'manager', DATE('now')),
('Aisha', 'Khan', 'aisha@email.com', '07333333333', 'hashedpassword', 'customer', DATE('now')),
('Amelia', 'Wilson', 'amelia@email.com', '07444444444', 'hashedpassword', 'customer', DATE('now')),
('John', 'Taylor', 'john@email.com', '07555555555', 'hashedpassword', 'customer', DATE('now')),
('Liam', 'Green', 'liam@warehouse.com', '07666666666', 'hashedpassword', 'worker', DATE('now'));


INSERT OR IGNORE INTO warehouse (name, location)
VALUES
('North Warehouse', 'Leeds'),
('South Warehouse', 'Manchester');


INSERT OR IGNORE INTO products (category_id, name, location, description, price, volume_per_unit, created_at)
VALUES
(1,'Bananas','Aisle 1','Fresh bananas',1.20,1.00,DATE('now')),
(1,'Apples','Aisle 1','Red apples',1.50,1.00,DATE('now')),
(1,'Oranges','Aisle 1','Sweet oranges',1.60,1.00,DATE('now')),
(1,'Strawberries','Aisle 1','Fresh strawberries',2.50,0.50,DATE('now')),
(2,'Carrots','Aisle 2','Organic carrots',0.90,1.00,DATE('now')),
(2,'Broccoli','Aisle 2','Fresh broccoli',1.10,0.50,DATE('now')),
(2,'Potatoes','Aisle 2','White potatoes',2.00,2.00,DATE('now')),
(2,'Onions','Aisle 2','Brown onions',1.30,1.00,DATE('now')),
(3,'Milk','Aisle 3','Semi skimmed milk',1.50,1.00,DATE('now')),
(3,'Butter','Aisle 3','Salted butter',2.00,0.25,DATE('now')),
(3,'Cheddar Cheese','Aisle 3','Mature cheddar',2.80,0.30,DATE('now')),
(3,'Yogurt','Aisle 3','Greek yogurt',1.20,0.20,DATE('now')),
(4,'White Bread','Aisle 5','White loaf',1.00,0.50,DATE('now')),
(4,'Brown Bread','Aisle 5','Wholemeal loaf',1.20,0.50,DATE('now')),
(4,'Croissants','Aisle 5','Butter croissants',2.50,0.40,DATE('now')),
(4,'Bagels','Aisle 5','Pack of bagels',1.80,0.45,DATE('now')),
(5,'Chicken Breast','Aisle 6','Fresh chicken breast',4.50,0.50,DATE('now')),
(5,'Beef Mince','Aisle 6','Lean beef mince',5.00,0.50,DATE('now')),
(5,'Pork Chops','Aisle 6','Fresh pork chops',4.20,0.60,DATE('now')),
(6,'Frozen Pizza','Aisle 7','Cheese pizza',3.00,0.70,DATE('now')),
(6,'Frozen Peas','Aisle 7','Garden peas',1.50,0.50,DATE('now')),
(6,'Ice Cream','Aisle 7','Vanilla ice cream',2.80,1.00,DATE('now')),
(7,'Orange Juice','Aisle 8','Fresh orange juice',2.00,1.00,DATE('now')),
(7,'Apple Juice','Aisle 8','Pure apple juice',2.00,1.00,DATE('now')),
(7,'Cola','Aisle 8','Soft drink cola',1.50,1.50,DATE('now')),
(7,'Mineral Water','Aisle 8','Still mineral water',0.90,1.50,DATE('now')),
(8,'Pasta','Aisle 9','Dry pasta',1.20,0.50,DATE('now')),
(8,'Rice','Aisle 9','Long grain rice',1.80,1.00,DATE('now')),
(8,'Tomato Sauce','Aisle 9','Pasta sauce',1.50,0.50,DATE('now')),
(8,'Olive Oil','Aisle 9','Extra virgin olive oil',5.00,0.75,DATE('now'));


INSERT OR IGNORE INTO stock (product_id, warehouse_id, quantity, updated_at)
VALUES
(1,1,120,DATE('now')),
(2,1,80,DATE('now')),
(3,1,60,DATE('now')),
(4,1,40,DATE('now')),
(5,1,90,DATE('now')),
(6,1,50,DATE('now')),
(7,1,200,DATE('now')),
(8,1,150,DATE('now')),
(9,1,100,DATE('now')),
(10,1,70,DATE('now')),
(1,2,60,DATE('now')),
(2,2,50,DATE('now')),
(3,2,40,DATE('now')),
(4,2,30,DATE('now')),
(5,2,70,DATE('now')),
(6,2,20,DATE('now')),
(7,2,90,DATE('now')),
(8,2,110,DATE('now')),
(9,2,80,DATE('now')),
(10,2,40,DATE('now'));


INSERT OR IGNORE INTO orders (user_id, address_id, total_price, delivery_slot, order_status, created_at)
VALUES
(3,NULL,8.70,DATETIME('now'),'pending',DATETIME('now')),
(4,NULL,5.20,DATETIME('now'),'processing',DATETIME('now'));


INSERT OR IGNORE INTO order_items (order_id, product_id, quantity_ordered, price)
VALUES
(1,1,3,1.20),
(1,9,2,1.50),
(2,3,2,1.60);