USE shopdb;

-- Hố sơ người dùng 
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,								
    full_name VARCHAR(100),
    gender ENUM('male', 'female'),
    date_of_birth DATE,
    avatar_url VARCHAR(255),
    phone VARCHAR(20),
    user_role SET('buyer', 'seller', 'admin') DEFAULT 'buyer'
);

-- Thông tin sản phẩm 
CREATE TABLE Products (
	product_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    shop_id INT NOT NULL,
    product_name VARCHAR(200) NOT NULL,
    product_description TEXT,
    product_star INT DEFAULT 0,
    product_review INT DEFAULT 0,
    product_sold INT DEFAULT 0,
    product_main_image_url VARCHAR(255) NOT NULL,
    product_min_price INT,
    product_max_price INT,
    number_of_likes INT DEFAULT 0,
    num_of_product_star INT DEFAULT 0,
    remaining_quantity INT,
    FOREIGN KEY (category_id) REFERENCES Categories(category_id),
    FOREIGN KEY (shop_id) REFERENCES Shops(shop_id)
);

-- Hình ảnh sản phẩm 
CREATE TABLE ProductsImage (
	image_id INT AUTO_INCREMENT PRIMARY KEY,
	product_url VARCHAR(255) NOT NULL, 
    product_id INT,
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

-- Biến thể màu sắc của sản phẩm 	
CREATE TABLE ProductVariants (
    variant_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    color VARCHAR(50),	
    image_url VARCHAR(255),			-- Ảnh sẽ theo color
    
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

-- Biến thể kích thước của sản phẩm 
CREATE TABLE ProductSizes (
    size_id INT AUTO_INCREMENT PRIMARY KEY,
    size VARCHAR(50) NOT NULL,
    product_variant_id INT NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (product_variant_id) REFERENCES ProductVariants(variant_id)
);

-- Danh mục sản phẩm 
CREATE TABLE Categories (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL,
    parent_id INT DEFAULT NULL, 

    FOREIGN KEY (parent_id) REFERENCES Categories(category_id)
);

-- Cửa hàng
CREATE TABLE Shops (
    shop_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,  
    shop_name VARCHAR(100) NOT NULL,
    shop_description TEXT,
    shop_avatar_url VARCHAR(255),
    shop_addr TEXT,
    shop_revenue INT,

    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Đơn vị vận chuyển
CREATE TABLE ShippingProviders (
    provider_id INT AUTO_INCREMENT PRIMARY KEY,
    provider_name VARCHAR(100) NOT NULL,
    contact_phone VARCHAR(20)
);

-- Liên kết đơn vị vận chuyển và sản phẩm
CREATE TABLE ShippingLinks (
    link_id INT AUTO_INCREMENT PRIMARY KEY,
    provider_id INT NOT NULL,
	product_id INT NOT NULL,
    FOREIGN KEY (provider_id) REFERENCES ShippingProviders(provider_id),
    FOREIGN KEY (product_id) REFERENCES Products(product_id)
);

-- Bảng giá của đơn vị vận chuyển
CREATE TABLE ShippingServices (
    service_id INT AUTO_INCREMENT PRIMARY KEY,
    provider_id INT NOT NULL,
	fast_price INT NOT NULL,			-- Giá cho 10km
    default_price INT NOT NULL,			-- Giá cho 10km
    add_distance INT NOT NULL,			-- Cứ quá x x 10 km thì lại thêm 10% phí
    FOREIGN KEY (provider_id) REFERENCES ShippingProviders(provider_id)
);

-- Giỏ hàng
 CREATE TABLE Carts (
	cart_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
 );

-- Sản phẩm trong giỏ hàng
 CREATE TABLE ProductsInCart (
	product_in_cart_id INT AUTO_INCREMENT PRIMARY KEY,
    cart_id INT NOT NULL,
    shop_id INT NOT NULL,
    product_name VARCHAR(200) NOT NULL,
	color VARCHAR(50),
    size VARCHAR(50),
    product_image_url VARCHAR(255) NOT NULL,
    product_price INT,
    product_quantity INT,
    FOREIGN KEY (shop_id) REFERENCES Shops(shop_id),
    FOREIGN KEY (cart_id) REFERENCES Carts(cart_id)
);

-- Địa chỉ giao hàng
CREATE TABLE Addresses (
    address_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    recipient_name VARCHAR(100) NOT NULL,
    phone VARCHAR(20) NOT NULL,
    detail_address VARCHAR(255) NOT NULL,
    is_default BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Voucher
CREATE TABLE Vouchers (
    voucher_id INT AUTO_INCREMENT PRIMARY KEY,
    voucher_code VARCHAR(50) UNIQUE NOT NULL,
    voucher_description TEXT,
    discount_type ENUM('percentage', 'fixed') NOT NULL,
    discount_value DECIMAL(10,2) NOT NULL,
    min_price INT NOT NULL,
    start_date DATE,
    end_date DATE,
    max_usage INT,
    usage_count INT DEFAULT 0,
    target_user_role SET('buyer', 'seller', 'all') DEFAULT 'all'
);

-- Áp dụng voucher với loại hàng
CREATE TABLE AppliedVouchers (
    applied_id INT AUTO_INCREMENT PRIMARY KEY,
    category_id INT NOT NULL,
    voucher_id INT NOT NULL,

    FOREIGN KEY (category_id) REFERENCES Categories(category_id),
    FOREIGN KEY (voucher_id) REFERENCES Vouchers(voucher_id)
);

-- Đơn hàng
CREATE TABLE Orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    address_id INT NOT NULL,
    total_amount DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (address_id) REFERENCES Addresses(address_id)
);

-- Hàng trong đơn hàng
CREATE TABLE ProductsInOrder (
	product_in_order_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    shop_id INT NOT NULL,
    voucher_id INT NOT NULL,
    provider_id INT NOT NULL,
    product_name VARCHAR(200) NOT NULL,
	color VARCHAR(50),
    size VARCHAR(50),
    product_image_url VARCHAR(255) NOT NULL,
    product_price INT,
    product_quantity INT,
    FOREIGN KEY (shop_id) REFERENCES Shops(shop_id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id),
    FOREIGN KEY (voucher_id) REFERENCES Vouchers(voucher_id),
    FOREIGN KEY (provider_id) REFERENCES ShippingProviders(provider_id)
);

-- Thanh toán
CREATE TABLE Payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    order_id INT NOT NULL,
    payment_method ENUM('COD', 'CreditCard', 'BankTransfer') NOT NULL,
    payment_status ENUM('Pending', 'Completed', 'Failed') DEFAULT 'Pending',
    payment_time DATETIME DEFAULT CURRENT_TIMESTAMP,
    amount DECIMAL(10,2) NOT NULL,

    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);

-- Đánh giá sản phẩm
CREATE TABLE ProductReviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    product_id INT NOT NULL,
    user_id INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),     
    review_text TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (product_id) REFERENCES Products(product_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Hình ảnh đánh giá
CREATE TABLE ReviewsImage (
	image_id INT AUTO_INCREMENT PRIMARY KEY,
    review_id INT NOT NULL,
	image_url VARCHAR(255) NOT NULL, 
    FOREIGN KEY (review_id) REFERENCES ProductReviews(review_id)
);

-- Chat
CREATE TABLE Conversations (
    conversation_id INT AUTO_INCREMENT PRIMARY KEY,
    buyer_id INT NOT NULL,
    seller_id INT NOT NULL,
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (buyer_id) REFERENCES Users(user_id),
    FOREIGN KEY (seller_id) REFERENCES Users(user_id)
);

-- Tin nhắn trong chat
CREATE TABLE Messages (
    message_id INT AUTO_INCREMENT PRIMARY KEY,
    conversation_id INT NOT NULL,
    sender_id INT NOT NULL,
    mess_text TEXT,
    message_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    is_read BOOLEAN DEFAULT FALSE,

    FOREIGN KEY (conversation_id) REFERENCES Conversations(conversation_id),
    FOREIGN KEY (sender_id) REFERENCES Users(user_id)
);

-- Lịch sử mua sắm
CREATE TABLE Historys (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    order_id INT NOT NULL,

    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (order_id) REFERENCES Orders(order_id)
);






