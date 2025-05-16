-- Tim pass_hash, id của username khi đăng nhập
SELECT user_id, password_hash
FROM Users
WHERE username = 'duyen123';

-- Truy vấn các thuộc tính của username khi đã có id
SELECT username, email, full_name, gender, date_of_birth, avatar_url, phone, user_role
FROM Users
WHERE user_id = 1;

-- Lấy shop_id khi có user_id
SELECT shop_id
FROM Shops
WHERE user_id = 1;

-- Lấy thông tin shop
SELECT user_id, shop_name, shop_description, shop_avatar_url, shop_addr, shop_revenue
FROM Shops
WHERE shop_id = 1;

-- Lấy id_cate khi biết tên cate
SELECT category_id
FROM Categories
WHERE category_name = 'laptop';

-- Lấy id và name của cate con của cate cha 
SELECT category_id, category_name
FROM Categories
WHERE parent_id = 1;

-- Lấy các id của 1 cate
SELECT product_id
FROM Products
WHERE category_id = 1;

-- Lấy các id của 1 shop
SELECT product_id
FROM Products
WHERE shop_id = 1;

-- Lấy toàn bộ thông tin khi biết id sản phẩm (lấy min max để so sánh với thằng mới sau đó cập nhật)
SELECT *
FROM Products
WHERE product_id = 2;

-- Lấy danh sách ảnh của 1 sản phẩm
SELECT product_url
FROM ProductsImage
WHERE product_id = 1;

-- Lấy full màu của 1 sản phẩm
SELECT variant_id, color
FROM ProductVariants
WHERE product_id = 1;

-- Lấy ảnh của sản phẩm X có màu Y
SELECT image_url
FROM ProductVariants
WHERE product_id = 1 AND color = 'Đen';

-- Lấy tất cả kích thước của sp
SELECT DISTINCT ps.size
FROM ProductSizes ps
JOIN ProductVariants pv ON ps.product_variant_id = pv.variant_id
WHERE pv.product_id = 1;

-- Lấy size của sp X màu Y
SELECT ps.size, ps.size_id
FROM ProductSizes ps
JOIN ProductVariants pv ON ps.product_variant_id = pv.variant_id
WHERE pv.product_id = 1 AND pv.color = 'Đen';

-- Lấy giá của 1 kích thước của sp X màu Y
SELECT ps.price
FROM ProductSizes ps
JOIN ProductVariants pv ON ps.product_variant_id = pv.variant_id
WHERE pv.product_id = 1 AND pv.color = 'Đen' AND ps.size = 'M';

-- Xem các đơn vị vận chuyển của sản phẩm
SELECT sp.provider_id, sp.provider_name, sp.contact_phone
FROM ShippingLinks sl
JOIN ShippingProviders sp ON sl.provider_id = sp.provider_id
WHERE sl.product_id = 1;

-- Lấy giá tiền của đơn vị vận chuyển của 1 sản phẩm
SELECT 
  ss.fast_price,
  ss.default_price
FROM ShippingLinks sl
JOIN ShippingServices ss ON sl.provider_id = ss.provider_id
WHERE sl.product_id = 1 AND sl.provider_id = 1;

-- Lấy giỏ hàng
SELECT cart_id 
FROM Carts 
WHERE user_id = ?;

-- Lấy các sản phầm trong giỏ
SELECT * 
FROM ProductsInCart 
WHERE cart_id = ?;

-- Lấy địa chỉ người dùng
SELECT * 
FROM Addresses
WHERE user_id = ?
ORDER BY is_default DESC;

-- Lấy voucher của 1 sản phẩm
SELECT v.*
FROM Vouchers v
JOIN AppliedVouchers av ON v.voucher_id = av.voucher_id
JOIN Products p ON av.category_id = p.category_id
WHERE p.product_id = 1
  AND CURDATE() BETWEEN v.start_date AND v.end_date
  AND (v.max_usage IS NULL OR v.usage_count < v.max_usage);

-- voucher của 1 loại hàng
SELECT v.*
FROM Vouchers v
JOIN AppliedVouchers av ON v.voucher_id = av.voucher_id
WHERE av.category_id = 1;

-- Lấy đơn hàng
SELECT *
FROM Orders
WHERE user_id = ?;

-- Lấy sản phẩn trong đơn
SELECT *
FROM ProductsInOrder
WHERE order_id = ?;

-- lấy đánh giá của 1 sp
SELECT * 
FROM ProductReviews 
WHERE product_id = ? 
ORDER BY review_date DESC;

-- hình ảnh của đánh giá
SELECT image_url 
FROM ReviewsImage 
WHERE review_id = ?;

-- id đoạn chat
SELECT conversation_id
FROM Conversations
WHERE (buyer_id = ? AND seller_id = ?)
   OR (buyer_id = ? AND seller_id = ?);
   
-- lấy tin nhắn trong chat
SELECT message_id, sender_id, mess_text, message_time, is_read
FROM Messages
WHERE conversation_id = ?
ORDER BY message_time ASC;

-- xem lịch sử đơn hàng
SELECT h.history_id, h.user_id, h.order_id, o.total_amount, o.address_id
FROM Historys h
JOIN Orders o ON h.order_id = o.order_id
WHERE h.user_id = ?;






