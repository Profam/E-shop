/*
DDL sql initialization script
Database: eshop
Tables: categories, products, product_images, orders, users, orders_products
*/
/*
Delete schema ESHOP, then create if not exists
*/
DROP SCHEMA IF EXISTS eshop;
CREATE SCHEMA IF NOT EXISTS eshop;
/*
Delete table CATEGORIES, then create if not exists
*/
DROP TABLE IF EXISTS ESHOP.CATEGORIES;
CREATE TABLE IF NOT EXISTS ESHOP.CATEGORIES (
    ID INT NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(40) NOT NULL,
    IMAGE_PATH VARCHAR(200) NOT NULL,
    RATING INT NULL,
    PRIMARY KEY(ID),
    UNIQUE INDEX IDX_CATEGORY_ID_UNIQUE (ID ASC),
    UNIQUE INDEX IDX_CATEGORY_NAME_UNIQUE (NAME ASC));
/*
Delete table USERS, then create if not exists
*/
DROP TABLE IF EXISTS ESHOP.USERS;
CREATE TABLE IF NOT EXISTS ESHOP.USERS (
    ID INT NOT NULL AUTO_INCREMENT,
    NAME VARCHAR(40) NOT NULL,
    SURNAME VARCHAR(40) NOT NULL,
    EMAIL VARCHAR(100) NOT NULL,
    PASSWORD VARCHAR(255) NOT NULL,
    BIRTHDAY DATE NOT NULL,
    BALANCE INT NOT NULL,
    PRIMARY KEY(ID),
    UNIQUE INDEX IDX_USER_ID_UNIQUE (ID ASC));
/*
Delete table PRODUCTS, then create if not exists
*/
DROP TABLE IF EXISTS ESHOP.PRODUCTS;
CREATE TABLE IF NOT EXISTS ESHOP.PRODUCTS (
    ID INT NOT NULL AUTO_INCREMENT,
    CATEGORY_ID INT NOT NULL,
    NAME VARCHAR(40) NOT NULL,
    DESCRIPTION VARCHAR(200) NOT NULL,
    PRICE INT NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORIES(ID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE INDEX IDX_PRODUCT_ID_UNIQUE (ID ASC));
/*
Delete table ORDERS, then create if not exists
*/
DROP TABLE IF EXISTS ESHOP.ORDERS;
CREATE TABLE IF NOT EXISTS ESHOP.ORDERS (
    ID INT NOT NULL AUTO_INCREMENT,
    USER_ID INT NOT NULL,
    DATE DATE NOT NULL,
    PRICE INT NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (USER_ID) REFERENCES USERS(ID)
    ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE INDEX IDX_ORDER_ID_UNIQUE (ID ASC));
/*
Delete table PRODUCT_IMAGES, then create if not exists
*/
DROP TABLE IF EXISTS ESHOP.PRODUCT_IMAGES;
CREATE TABLE IF NOT EXISTS ESHOP.PRODUCT_IMAGES (
    ID INT NOT NULL AUTO_INCREMENT,
    PRODUCT_ID INT NOT NULL,
    PATH VARCHAR(200) NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS(ID)
        ON DELETE CASCADE ON UPDATE CASCADE,
    UNIQUE INDEX IDX_ORDER_ID_UNIQUE (ID ASC));
/*
Delete table ORDERS_PRODUCTS, then create if not exists
*/
DROP TABLE IF EXISTS ESHOP.ORDERS_PRODUCTS;
CREATE TABLE IF NOT EXISTS ESHOP.ORDERS_PRODUCTS (
    ORDER_ID INT NOT NULL,
    PRODUCT_ID INT NOT NULL,
    PRIMARY KEY(ORDER_ID, PRODUCT_ID),
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID),
    FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS(ID)
    ON DELETE CASCADE ON UPDATE CASCADE);

INSERT INTO eshop.categories(name, image_path, rating)
VALUES('Appliances','/images/categories/appliances.jpg', 10);
INSERT INTO eshop.categories(name, image_path, rating)
VALUES('Computers','/images/categories/computers.jpg', 10);
INSERT INTO eshop.categories(name, image_path, rating)
VALUES('Electronics','/images/categories/construction.jpg', 8);
INSERT INTO eshop.categories(name, image_path, rating)
VALUES('Construction','/images/categories/electronics.jpg', 9);
INSERT INTO eshop.categories(name, image_path, rating)
VALUES('Sport','/images/categories/sport.jpg', 7);

INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'washing machine 1','washing machine samsung', 400);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'washing machine 2','washing machine lg', 500);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'washing machine 3','washing machine horizont', 300);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'washing machine 4','washing machine candy', 250);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'washing machine 5','washing machine electrolux', 350);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'washing machine 6','washing machine saturn', 450);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'washing machine 7','washing machine luch', 300);

INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'microwave 1','microwave samsung', 300);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'microwave 2','microwave lg', 400);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'microwave 3','microwave horizont', 200);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'microwave 4','microwave candy', 250);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'microwave 5','microwave electrolux', 350);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'microwave 6','microwave saturn', 450);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'microwave 7','microwave luch', 300);

INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'refrigerator 1','refrigerator samsung', 400);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'refrigerator 2','refrigerator lg', 500);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'refrigerator 3','refrigerator horizont', 300);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'refrigerator 4','refrigerator candy', 350);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'refrigerator 5','refrigerator electrolux', 450);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'refrigerator 6','refrigerator saturn', 550);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(1, 'refrigerator 7','refrigerator luch', 650);

INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 1','notebook samsung', 1400);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 2','notebook lg', 1500);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 3','notebook horizont', 1300);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 4','notebook candy', 1350);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 5','notebook electrolux', 1450);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 6','notebook saturn', 1550);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 7','notebook luch', 1650);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 8','notebook samsung', 1400);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 9','notebook lg', 1500);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 10','notebook horizont', 1300);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 11','notebook candy', 1350);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 12','notebook electrolux', 1450);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 13','notebook saturn', 1550);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'notebook 14','notebook luch', 1650);

INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 1','tablet samsung', 700);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 2','tablet lg', 750);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 3','tablet horizont', 650);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 4','tablet candy', 745);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 5','tablet electrolux', 725);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 6','tablet saturn', 765);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 7','tablet luch', 825);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 8','tablet samsung', 700);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 9','tablet lg', 750);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 10','tablet horizont', 650);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 11','tablet candy', 675);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 12','tablet electrolux', 725);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 13','tablet saturn', 775);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'tablet 14','tablet luch', 825);

INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 1','monoblock samsung', 700);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 2','monoblock lg', 750);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 3','monoblock horizont', 650);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 4','monoblock candy', 745);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 5','monoblock electrolux', 725);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 6','monoblock saturn', 765);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 7','monoblock luch', 825);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 8','monoblock samsung', 700);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 9','monoblock lg', 750);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 10','monoblock horizont', 650);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 11','monoblock candy', 675);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 12','monoblock electrolux', 725);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 13','monoblock saturn', 775);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monoblock 14','monoblock luch', 825);

INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 1','monitor samsung', 700);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 2','monitor lg', 750);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 3','monitor horizont', 650);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 4','monitor candy', 745);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 5','monitor electrolux', 725);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 6','monitor saturn', 765);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 7','monitor luch', 825);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 8','monitor samsung', 700);
INSERT INTO eshop.products(category_id, name, description, price)
VALUES(2, 'monitor 9','monitor lg', 750);

INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(1, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(2, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(3, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(4, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(5, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(6, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(7, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(8, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(9, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(10, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(11, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(12, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(13, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(14, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(15, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(16, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(17, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(18, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(19, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(20, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(21, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(22, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(23, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(24, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(25, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(26, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(27, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(28, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(29, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(30, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(31, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(32, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(33, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(34, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(35, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(36, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(37, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(38, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(39, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(40, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(41, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(42, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(43, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(44, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(45, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(46, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(47, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(48, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(49, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(50, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(51, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(52, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(53, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(54, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(55, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(56, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(57, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(58, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(59, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(60, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(61, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(62, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(63, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(64, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(65, '/images/products/lg.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(66, '/images/products/horizont.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(67, '/images/products/candy.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(68, '/images/products/electrolux.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(69, '/images/products/saturn.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(70, '/images/products/luch.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(71, '/images/products/samsung.jpg');
INSERT INTO eshop.product_images(PRODUCT_ID, PATH)
VALUES(72, '/images/products/lg.jpg');

INSERT INTO eshop.users(ID, NAME, SURNAME, EMAIL, PASSWORD, BIRTHDAY, BALANCE)
VALUES(1, 'Paul', 'Rabtsevich', 'goldar2@tut.by', '$2a$10$vV3SbBKNRYk6zv0Db.1z2O7nh/C0d2S5jVjo9czZLAmqT3zgd1t/K', '1994-05-11', 10000);