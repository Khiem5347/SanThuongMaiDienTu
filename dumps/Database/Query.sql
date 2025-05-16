-- Thêm người dùng
INSERT INTO Users (username, password_hash, email, full_name, gender, date_of_birth, avatar_url, phone, user_role)
VALUES ('new_username', 'new_password_hash', 'new_email@example.com', 'New Full Name', 'male', '2000-01-01', 'link/new_avatar.jpg', '0123456789', 'buyer');

-- Xóa người dùng
DELETE FROM Users
WHERE user_id = 1;

-- Sửa thông tin người dùng
UPDATE Users
SET email = 'new_email@example.com',
    full_name = 'Nguyễn Văn A',
    gender = 'female',
    date_of_birth = '1996-08-15',
    avatar_url = 'link/new_avatar.png',
    phone = '0333222111'
WHERE user_id = 1;  

-- Thêm shop
INSERT INTO Shops (user_id, shop_name, shop_description, shop_avatar_url, shop_addr, shop_revenue)
VALUES (1, 'Shop A', 'Description of Shop A', 'url_to_avatar_a', 'Address A', 0);

-- Xóa shop
DELETE FROM Shops
WHERE shop_id = 1;

-- Sửa thông tin shop
UPDATE Shops
SET 
  shop_name = 'Tên mới',
  shop_description = 'Mô tả mới',
  shop_avatar_url = 'link_moi.jpg',
  shop_addr = 'Địa chỉ mới'
WHERE shop_id = 1;

-- Tăng doanh thu của shop
UPDATE Shops
SET shop_revenue = shop_revenue + 500000
WHERE shop_id = 1;

-- Thêm danh mục
INSERT INTO Categories (category_name, parent_id)
VALUES ('Electronics', NULL);

-- Xóa danh mục 
DELETE FROM Categories
WHERE category_id = 2;

-- Sửa tên danh mục
UPDATE Categories
SET category_name = 'Notebooks'
WHERE category_id = 2;

-- Thêm sản phẩm cho shop
INSERT INTO Products (seller_id, category_id, shop_id, product_name, product_description, product_main_image_url, product_min_price, product_max_price)
VALUES (1, 101, 201, 'Tên sản phẩm mới', 'Mô tả sản phẩm mới', 'url_anh_chinh.jpg', 100000, 200000);

-- Xóa sản phẩm
DELETE FROM Products
WHERE product_id = 123;

-- Sửa thông tin sản phẩm
UPDATE Products
SET category_id = 102,
    product_name = 'Tên sản phẩm đã sửa',
    product_description = 'Mô tả sản phẩm đã sửa',
    product_main_image_url = 'url_anh_chinh_moi.jpg'
WHERE product_id = 123;

-- Khi có người thêm đánh giá, like, hoặc có thêm sản phẩm đã mua
UPDATE Products
SET product_review = product_review + 1,
    number_of_likes = number_of_likes + 1,
    product_sold = product_sold + 1
WHERE product_id = 123; 

-- Khi có thêm người đánh giá sao
UPDATE Products
SET product_star = ( (product_star * num_of_product_star) + new_rating ) / (num_of_product_star + 1),
    num_of_product_star = num_of_product_star + 1
WHERE product_id = 123;  

-- Thêm hình ảnh cho một sản phẩm
INSERT INTO ProductsImage (product_url, product_id)
VALUES ('url_to_image.jpg', 123);

-- Xóa hình ảnh của một sản phẩm
DELETE FROM ProductsImage
WHERE product_id = 123 AND product_url = 'url_to_image.jpg';

-- THêm biến thể
INSERT INTO ProductVariants (product_id, color, image_url)
VALUES (1, 'Red', 'red_image.jpg');

INSERT INTO ProductSizes (size, product_variant_id, price, quantity)
VALUES ('S', LAST_INSERT_ID(), 200000, 10);

-- Xóa biến thể
DELETE FROM ProductSizes
WHERE product_variant_id = 1;

DELETE FROM ProductVariants
WHERE variant_id = 1;

-- Sửa thông tin trong biến thể
UPDATE ProductVariants
SET color = 'Blue', image_url = 'blue_image.jpg'
WHERE variant_id = 1;

UPDATE ProductSizes
SET price = 250000, quantity = 15
WHERE product_variant_id = 1;

-- Thêm liên kết sản phẩm - dvvc
INSERT INTO ShippingLinks (provider_id, product_id)
VALUES (1, 101);

-- xóa liên kết sp - dvvc
DELETE FROM ShippingLinks
WHERE provider_id = 1 AND product_id = 101;

-- Tạo giỏ hàng
INSERT INTO Carts (user_id)
VALUES (1);  

-- Thêm sản phẩm vào giỏ
INSERT INTO ProductsInCart (cart_id, shop_id, product_name, color, size, product_image_url, product_price, product_quantity)
VALUES (1, 101, 'Áo thun', 'Đỏ', 'M', 'url_anh_ao_do.jpg', 200000, 2);

-- Xóa sp khỏi giỏ
DELETE FROM ProductsInCart
WHERE cart_id = 1 AND product_name = 'Áo thun';

-- Sửa số lượng sp trong giỏ
UPDATE ProductsInCart
SET product_quantity = 3
WHERE cart_id = 1 AND product_name = 'Áo thun';

-- Thêm địa chỉ giao hàng
INSERT INTO Addresses (user_id, recipient_name, phone, detail_address)
VALUES (1, 'Nguyen Van A', '0901234567', '123 Đường ABC, Quận XYZ, TP.HCM');

-- Xóa địa chỉ 
DELETE FROM Addresses
WHERE address_id = 5;

-- Thêm voucher
INSERT INTO Vouchers (voucher_code, voucher_description, discount_type, discount_value, min_price, start_date, end_date, max_usage, target_user_role)
VALUES ('SALE10', 'Giảm 10% cho đơn hàng đầu tiên', 'percentage', 10.00, 50000, '2024-01-01', '2024-01-31', 100, 'buyer');

-- xóa voucher
DELETE FROM Vouchers
WHERE voucher_id = 1;

-- sửa voucher
UPDATE Vouchers
SET voucher_description = 'Giảm 15% cho thành viên VIP',
    discount_value = 15.00,
    end_date = '2024-02-29'
WHERE voucher_code = 'SALE10';

-- THêm liên kết voucher - category
INSERT INTO AppliedVouchers (category_id, voucher_id)
VALUES (1, 101);

-- Xóa liên kết voucher - category
DELETE FROM AppliedVouchers
WHERE applied_id = 1;

-- Sửa liên kết voucher - category
UPDATE AppliedVouchers
SET category_id = 2,
    voucher_id = 102
WHERE applied_id = 1;

-- thêm đơn hàng
INSERT INTO Orders (user_id, address_id, total_amount)
VALUES (1, 2, 1000000);

-- xóa đơn
DELETE FROM Orders
WHERE order_id = 1;

-- thêm sp vào đơn
INSERT INTO ProductsInOrder (order_id, shop_id, product_name, color, size, product_image_url, product_price, product_quantity, voucher_id, provider_id)
VALUES (1, 101, 'Áo thun', 'Đỏ', 'M', 'url_anh_ao_do.jpg', 200000, 2, 201, 301);

-- sửa voucher,ship của sp trong đơn
UPDATE ProductsInOrder
SET voucher_id = 202,  
    provider_id = 302  
WHERE order_id = 1 AND product_name = 'Áo thun';  

-- thêm đánh giá
INSERT INTO ProductReviews (product_id, user_id, rating, review_text)
VALUES (101, 1, 5, 'Sản phẩm tuyệt vời!');

-- xóa đánh giá
DELETE FROM ProductReviews
WHERE review_id = 1;

-- thêm ảnh đánh giá
INSERT INTO ReviewsImage (review_id, image_url)
VALUES (1, 'url_to_image1.jpg');

-- xóa ảnh đánh giá
DELETE FROM ReviewsImage
WHERE image_id = 1;

-- thêm lịch sử mua
INSERT INTO Historys (user_id, order_id)
VALUES (1, 101);

-- thêm đoạn chat
INSERT INTO Conversations (buyer_id, seller_id)
VALUES (1, 2);

-- thêm tin nhắn
INSERT INTO Messages (conversation_id, sender_id, mess_text)
VALUES (1, 3, 'Hello!');
