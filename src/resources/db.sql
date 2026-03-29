create database restaurant_db;
use restaurant_db;

-- 1. Bảng người dùng và phân quyền
CREATE TABLE users
(
    user_id    INT PRIMARY KEY AUTO_INCREMENT,
    username   VARCHAR(50)                          NOT NULL UNIQUE,
    password   VARCHAR(255)                         NOT NULL,
    full_name  VARCHAR(100)                         NOT NULL,
    email      VARCHAR(100) UNIQUE,
    phone      VARCHAR(20),
    role       ENUM ('CUSTOMER', 'CHEF', 'MANAGER') NOT NULL,
    is_active  BOOLEAN  DEFAULT TRUE,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
    -- updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 2. Bảng bàn ăn
CREATE TABLE restaurant_tables
(
    table_id     INT PRIMARY KEY AUTO_INCREMENT,
    table_number INT NOT NULL UNIQUE, -- Số bàn
    capacity     INT NOT NULL,        -- Sức chứa
    status       ENUM ('AVAILABLE', 'OCCUPIED', 'RESERVED') DEFAULT 'AVAILABLE'

    -- created_at       DATETIME DEFAULT CURRENT_TIMESTAMP
    --  updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 3. Bảng thực đơn (Menu)
CREATE TABLE menu_items
(
    item_id      INT PRIMARY KEY AUTO_INCREMENT,
    name         VARCHAR(100)           NOT NULL,
    category     ENUM ('FOOD', 'DRINK') NOT NULL,
    price        DECIMAL(10, 2)         NOT NULL CHECK (price > 0),
    stock        INT     DEFAULT NULL, -- NULL nếu là đồ ăn, có số nếu là đồ uống/có tồn kho
    is_available BOOLEAN DEFAULT TRUE

    -- created_at       DATETIME DEFAULT CURRENT_TIMESTAMP
    --  updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 4. Bảng Order
CREATE TABLE orders
(
    order_id     INT PRIMARY KEY AUTO_INCREMENT,
    table_id     INT NOT NULL,
    user_id      INT NOT NULL, -- Khách hàng gọi món
    total_amount DECIMAL(10, 2)                                         DEFAULT 0.00,
    status       ENUM ('PENDING', 'APPROVED', 'CANCELLED', 'COMPLETED') DEFAULT 'PENDING',
    created_at   DATETIME                                               DEFAULT CURRENT_TIMESTAMP,
    -- updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (table_id) REFERENCES restaurant_tables (table_id) ON DELETE RESTRICT,
    FOREIGN KEY (user_id) REFERENCES users (user_id) ON DELETE RESTRICT
);

-- 5. Bảng Order Items
CREATE TABLE order_items
(
    order_item_id INT PRIMARY KEY AUTO_INCREMENT,
    order_id      INT NOT NULL,
    item_id       INT NOT NULL,
    quantity      INT NOT NULL CHECK (quantity > 0),
    status        ENUM ('PENDING', 'COOKING', 'READY', 'SERVED', 'CANCELLED') DEFAULT 'PENDING',
    -- created_at       DATETIME DEFAULT CURRENT_TIMESTAMP,
    -- updated_at       DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE CASCADE,
    FOREIGN KEY (item_id) REFERENCES menu_items (item_id) ON DELETE RESTRICT
);

-- 6. Bảng Review (Đánh giá) - Nâng cao
CREATE TABLE reviews
(
    review_id  INT PRIMARY KEY AUTO_INCREMENT,
    user_id    INT     NOT NULL,
    order_id   INT     NULL, -- Đánh giá theo order cụ thể
    item_id    INT     NULL, -- Đánh giá theo món (tùy chọn)
    rating     TINYINT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment    TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (order_id) REFERENCES orders (order_id),
    FOREIGN KEY (item_id) REFERENCES menu_items (item_id)
);

-- 7. Bảng Payment / Hóa đơn (Nâng cao - Thanh toán)
CREATE TABLE payments
(
    payment_id     INT PRIMARY KEY AUTO_INCREMENT,
    order_id       INT                               NOT NULL,
    amount         DECIMAL(10, 2)                    NOT NULL,
    payment_method ENUM ('CASH', 'CARD', 'TRANSFER') NOT NULL,
    payment_time   DATETIME                             DEFAULT CURRENT_TIMESTAMP,
    status         ENUM ('PENDING', 'PAID', 'REFUNDED') DEFAULT 'PENDING',

    FOREIGN KEY (order_id) REFERENCES orders (order_id) ON DELETE RESTRICT
);