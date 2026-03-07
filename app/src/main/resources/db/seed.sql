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
('Liam', null, 'liam@warehouse.com', '07666666666', 'hashedpassword', 'worker', DATE('now'));

INSERT OR IGNORE INTO user_sessions (user_id, ip_address, created_at, expires_at)
VALUES
(1, '192.168.1.1', DATE('now'), DATE('now', '+1 hour')),
(2, '192.168.1.2', DATE('now'), DATE('now', '+1 hour')),
(3, '192.168.1.3', DATE('now'), DATE('now', '+1 hour')),
(4, '192.168.1.4', DATE('now'), DATE('now', '+1 hour')),
(3, '192.168.1.5', DATE('now'), DATE('now', '+1 hour')),
(4, '192.168.1.6', DATE('now'), DATE('now', '+1 hour'));


INSERT OR IGNORE INTO warehouse (name, location)
VALUES
('North Warehouse', 'Leeds'),
('South Warehouse', 'Manchester');


INSERT OR IGNORE INTO products (category_id, name, location, description, barcode, price, volume_per_unit, created_at)
VALUES
(1,'Bananas','Aisle 1','Fresh bananas','123456789012',1.20,1.00,DATE('now')),
(1,'Apples','Aisle 1','Red apples','123456789013',1.50,1.00,DATE('now')),
(1,'Oranges','Aisle 1','Sweet oranges','123456789014',1.60,1.00,DATE('now')),
(1,'Strawberries','Aisle 1','Fresh strawberries','123456789015',2.50,0.50,DATE('now')),
(2,'Carrots','Aisle 2','Organic carrots','123456789016',0.90,1.00,DATE('now')),
(2,'Broccoli','Aisle 2','Fresh broccoli','123456789017',1.10,0.50,DATE('now')),
(2,'Potatoes','Aisle 2','White potatoes','123456789018',2.00,2.00,DATE('now')),
(2,'Onions','Aisle 2','Brown onions','123456789019',1.30,1.00,DATE('now')),
(3,'Milk','Aisle 3','Semi skimmed milk','123456789020',1.50,1.00,DATE('now')),
(3,'Butter','Aisle 3','Salted butter','123456789021',2.00,0.25,DATE('now')),
(3,'Cheddar Cheese','Aisle 3','Mature cheddar','123456789022',2.80,0.30,DATE('now')),
(3,'Yogurt','Aisle 3','Greek yogurt','123456789023',1.20,0.20,DATE('now')),
(4,'White Bread','Aisle 5','White loaf','123456789024',1.00,0.50,DATE('now')),
(4,'Brown Bread','Aisle 5','Wholemeal loaf','123456789025',1.20,0.50,DATE('now')),
(4,'Croissants','Aisle 5','Butter croissants','123456789026',2.50,0.40,DATE('now')),
(4,'Bagels','Aisle 5','Pack of bagels','123456789027',1.80,0.45,DATE('now')),
(5,'Chicken Breast','Aisle 6','Fresh chicken breast','123456789028',4.50,0.50,DATE('now')),
(5,'Beef Mince','Aisle 6','Lean beef mince','123456789029',5.00,0.50,DATE('now')),
(5,'Pork Chops','Aisle 6','Fresh pork chops','123456789030',4.20,0.60,DATE('now')),
(6,'Frozen Pizza','Aisle 7','Cheese pizza','123456789031',3.00,0.70,DATE('now')),
(6,'Frozen Peas','Aisle 7','Garden peas','123456789032',1.50,0.50,DATE('now')),
(6,'Ice Cream','Aisle 7','Vanilla ice cream','123456789033',2.80,1.00,DATE('now')),
(7,'Orange Juice','Aisle 8','Fresh orange juice','123456789034',2.00,1.00,DATE('now')),
(7,'Apple Juice','Aisle 8','Pure apple juice','123456789035',2.00,1.00,DATE('now')),
(7,'Cola','Aisle 8','Soft drink cola','123456789036',1.50,1.50,DATE('now')),
(7,'Mineral Water','Aisle 8','Still mineral water','123456789037',0.90,1.50,DATE('now')),
(8,'Pasta','Aisle 9','Dry pasta','123456789038',1.20,0.50,DATE('now')),
(8,'Rice','Aisle 9','Long grain rice','123456789039',1.80,1.00,DATE('now')),
(8,'Tomato Sauce','Aisle 9','Pasta sauce','123456789040',1.50,0.50,DATE('now')),
(8,'Olive Oil','Aisle 9','Extra virgin olive oil','123456789041',5.00,0.75,DATE('now'));


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


INSERT OR IGNORE INTO orders (user_id, address_id, list_id, total_price, delivery_slot, order_status, created_at)
VALUES
(3,NULL,3,8.70,DATETIME('now'),'pending',DATETIME('now')),
(4,NULL,4,5.20,DATETIME('now'),'processing',DATETIME('now'));


INSERT OR IGNORE INTO order_items (order_id, product_id, quantity_ordered, price)
VALUES
(1,1,3,1.20),
(1,9,2,1.50),
(2,3,2,1.60);