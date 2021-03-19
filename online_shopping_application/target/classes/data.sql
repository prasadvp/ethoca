DROP TABLE IF EXISTS USER;
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS CART;
DROP TABLE IF EXISTS ORDER;
DROP TABLE IF EXISTS ORDERDETAILS;

CREATE TABLE USER (
  userId INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  email VARCHAR(250) NOT NULL
);
INSERT INTO USER (userId, name, email) VALUES
  (307011, 'John', 'john@test.com'),
  (307012, 'Sam', 'sam@test.com'),
  (307013, 'Julie', 'julie@test.com');

CREATE TABLE PRODUCT (
  prodId INT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  availableQty INT NOT NULL,
  price DECIMAL(3,2)
);
 INSERT INTO PRODUCT (prodId, name, description,availableQty,price) VALUES
  (1234567890', 'Laptop', 'Windows Laptop', 10, 599.99),
  (1234567891, 'Cellphone', 'Samsung Galaxy M2', 10, 299.99),
  (1234567892, 'Watch', 'Kids watch', 10, 10.00);

CREATE TABLE CART (
  cartId INT PRIMARY KEY,
  userId INT NOT NULL,
  prodId INT,
  quantity INT ,
  active BOOLEAN.
  foreign key (userId) references USER(userId),
  foreign key (prodId) references PRODUCT(prodId)
);

CREATE TABLE ORDER (
  orderId INT PRIMARY KEY,
  userID INT NOT NULL,
  orderDate TIMESTAMP
);

CREATE TABLE ORDERDETAILS (
  orderId INT PRIMARY KEY,
  prodID INT,
  quantity INT
  shippmentDate TIMESTAMP 
);
