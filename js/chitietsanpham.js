document.addEventListener('DOMContentLoaded', () => {
    // Dữ liệu JSON tạm thời mô phỏng dữ liệu từ API hoặc file
    const productData = {
        "order": {
            "product_id": "XYZ123",
            "product_name": "Hộp Đựng Tai Nghe Chụp Tai Full Size, màng trợ giảng Kháng Nước cho tai nghe chụp tai cỡ vừa Chuptai-1",
            "price_range": {
              "min_price": 50000,
              "max_price": 115000
            },
            "variations": [ // Thêm trường variations để JS có thể đọc
                {
                    "type": "Phân loại",
                    "option": "Tiêu chuẩn-Đen /ICT-1",
                    "price": 50000,
                    "stock": 150,
                    "image": "placeholder-var1.jpg" // Ảnh riêng cho biến thể (tùy chọn)
                },
                {
                    "type": "Phân loại",
                    "option": "Nâng cấp-Xám /ICT-1M",
                    "price": 115000,
                    "stock": 59,
                     "image": "placeholder-var2.jpg"
                },
                 {
                    "type": "Phân loại",
                    "option": "Nâng cấp-Đen", // Ví dụ biến thể khác
                    "price": 100000,
                    "stock": 0 // Hết hàng
                }
            ],
             "images": [ // Thêm trường images cho gallery
                 "placeholder-main.jpg",
                 "placeholder-thumb1.jpg",
                 "placeholder-thumb2.jpg",
                 "placeholder-thumb3.jpg" // Thêm các ảnh khác
            ],
            "selected_variation": null, // Ban đầu chưa chọn biến thể nào
            "quantity": 1,
            "shipping_info": {
              "estimated_delivery": "14 Th05 - 15 Th05",
              "shipping_fee": 0, // 0 means Free
              "voucher_applied": true,
              "voucher_amount": 15000
            },
            "return_policy_text": "Trả hàng miễn phí 15 ngày",
            "ratings": {
                 "average": 4.9,
                 "reviews_count": "2k",
                 "sold_count": "7.8k"
            },
            "seller_info": {
              "seller_name": "Tên Người Bán",
              "seller_id": "ID_NGUOI_BAN"
            }
          }
    };

    // Lấy tham chiếu đến các phần tử HTML
    const productTitle = document.querySelector('.product-title');
    const ratingStars = document.querySelector('.ratings-sold .stars');
    const reviewsCount = document.querySelector('.ratings-sold .reviews');
    const soldCount = document.querySelector('.ratings-sold .sold');
    const priceElement = document.querySelector('.price-section .price');
    const estDeliveryElement = document.querySelector('.shipping-info .est-delivery');
    const shippingFeeElement = document.querySelector('.shipping-info .shipping-fee');
    const voucherTextElement = document.querySelector('.shipping-info .voucher-text');
    const returnPolicyTextElement = document.querySelector('.return-policy .policy-text');
    const variationsOptionsContainer = document.querySelector('.variations-options');
    const quantityInput = document.querySelector('.quantity-input');
    const minusBtn = document.querySelector('.quantity-btn.minus');
    const plusBtn = document.querySelector('.quantity-btn.plus');
    const stockAvailableElement = document.querySelector('.stock-available');
    const mainImage = document.querySelector('.main-image');
    const thumbnailsContainer = document.querySelector('.thumbnails');


    // Hàm định dạng tiền tệ Việt Nam
    function formatCurrency(amount) {
        return `₫${amount.toLocaleString('vi-VN')}`;
    }

    // Hàm cập nhật giao diện dựa trên dữ liệu
    function renderProductPage(data) {
        const product = data.order;

        // Cập nhật thông tin cơ bản
        productTitle.textContent = product.product_name;
        // ratingStars.textContent = '★★★★★'; // Có thể tính số sao dựa trên average nếu cần
        reviewsCount.textContent = `${product.ratings.reviews_count} Đánh Giá`;
        soldCount.textContent = `${product.ratings.sold_count} Đã Bán`;

        estDeliveryElement.textContent = product.shipping_info.estimated_delivery;
        shippingFeeElement.textContent = product.shipping_info.shipping_fee === 0 ? 'Miễn Phí' : formatCurrency(product.shipping_info.shipping_fee);
        voucherTextElement.textContent = product.shipping_info.voucher_applied ? `Voucher ₫${product.shipping_info.voucher_amount.toLocaleString('vi-VN')} nếu giao sau thời gian trên.` : 'Không có voucher vận chuyển';
        returnPolicyTextElement.textContent = product.return_policy_text;

        // Hiển thị khoảng giá ban đầu nếu chưa có biến thể nào được chọn hoặc không có biến thể
        if (!product.variations || product.variations.length === 0 || product.selected_variation === null) {
             const minPrice = product.price_range.min_price;
             const maxPrice = product.price_range.max_price;
             if (minPrice === maxPrice) {
                 priceElement.textContent = formatCurrency(minPrice);
             } else {
                 priceElement.textContent = `${formatCurrency(minPrice)} - ${formatCurrency(maxPrice)}`;
             }
              // Cập nhật stock ban đầu (có thể là tổng hoặc một giá trị mặc định)
             const totalStock = product.variations ? product.variations.reduce((sum, v) => sum + v.stock, 0) : 0;
             stockAvailableElement.textContent = `${totalStock} sản phẩm có sẵn`;
        }


        // Render danh sách ảnh thumbnail
        if (product.images && product.images.length > 0) {
            thumbnailsContainer.innerHTML = ''; // Xóa ảnh cũ
            product.images.forEach((image, index) => {
                const img = document.createElement('img');
                img.src = image;
                img.alt = `Thumbnail ${index + 1}`;
                img.classList.add('thumbnail');
                if (index === 0) {
                    img.classList.add('active'); // Ảnh đầu tiên active
                    mainImage.src = image; // Set ảnh chính ban đầu
                }
                img.addEventListener('click', () => {
                    // Cập nhật ảnh chính khi click thumbnail
                    mainImage.src = img.src;
                    // Loại bỏ active khỏi tất cả thumbnails
                    thumbnailsContainer.querySelectorAll('.thumbnail').forEach(thumb => thumb.classList.remove('active'));
                    // Thêm active cho thumbnail được click
                    img.classList.add('active');
                });
                thumbnailsContainer.appendChild(img);
            });
        }


        // Render các tùy chọn biến thể
        if (product.variations && product.variations.length > 0) {
            variationsOptionsContainer.innerHTML = ''; // Xóa tùy chọn cũ
            product.variations.forEach(variation => {
                const button = document.createElement('button');
                button.classList.add('variation-option');
                button.textContent = variation.option;
                button.dataset.value = variation.option; // Lưu giá trị biến thể
                button.dataset.price = variation.price; // Lưu giá của biến thể
                button.dataset.stock = variation.stock; // Lưu stock của biến thể

                 if (variation.stock === 0) {
                    button.disabled = true; // Vô hiệu hóa nếu hết hàng
                     button.style.textDecoration = 'line-through'; // Gạch ngang
                     button.style.opacity = '0.6';
                     button.style.cursor = 'not-allowed';
                 } else {
                     button.addEventListener('click', handleVariationSelect);
                 }


                variationsOptionsContainer.appendChild(button);
            });
        }
    }

    // Hàm xử lý khi chọn biến thể
    function handleVariationSelect(event) {
        const selectedButton = event.target;
        const product = productData.order;

        // Loại bỏ class 'selected' khỏi tất cả các tùy chọn biến thể
        variationsOptionsContainer.querySelectorAll('.variation-option').forEach(button => {
            button.classList.remove('selected');
        });

        // Thêm class 'selected' vào tùy chọn được click
        selectedButton.classList.add('selected');

        // Cập nhật dữ liệu biến thể được chọn trong object productData
        const selectedOptionValue = selectedButton.dataset.value;
        product.selected_variation = product.variations.find(v => v.option === selectedOptionValue);

        // Cập nhật giá và stock dựa trên biến thể được chọn
        if (product.selected_variation) {
            priceElement.textContent = formatCurrency(product.selected_variation.price);
            stockAvailableElement.textContent = `${product.selected_variation.stock} sản phẩm có sẵn`;
             // Đặt lại số lượng về 1 khi chọn biến thể mới (hoặc giữ giá trị cũ nếu hợp lệ)
            quantityInput.value = 1;
            quantityInput.max = product.selected_variation.stock; // Cập nhật max quantity
             updateQuantityButtons(); // Cập nhật trạng thái nút +/-

            // (Tùy chọn) Cập nhật ảnh chính nếu biến thể có ảnh riêng
            if (product.selected_variation.image) {
                mainImage.src = product.selected_variation.image;
                 // Cập nhật active thumbnail tương ứng (nếu ảnh biến thể trùng với thumbnail nào đó)
                 thumbnailsContainer.querySelectorAll('.thumbnail').forEach(thumb => {
                     if (thumb.src.includes(product.selected_variation.image)) {
                         thumb.classList.add('active');
                     } else {
                         thumb.classList.remove('active');
                     }
                 });
            }


        } else {
             // Trường hợp không tìm thấy biến thể (lỗi dữ liệu?) hoặc hết hàng được chọn
             // Có thể reset về khoảng giá hoặc hiển thị thông báo
              const minPrice = product.price_range.min_price;
              const maxPrice = product.price_range.max_price;
              if (minPrice === maxPrice) {
                  priceElement.textContent = formatCurrency(minPrice);
              } else {
                  priceElement.textContent = `${formatCurrency(minPrice)} - ${formatCurrency(maxPrice)}`;
              }
             stockAvailableElement.textContent = `... sản phẩm có sẵn`; // Hoặc hiển thị thông báo hết hàng
             quantityInput.value = 0;
             quantityInput.max = 0;
              updateQuantityButtons();
        }
    }

    // Hàm cập nhật trạng thái nút +/- số lượng
    function updateQuantityButtons() {
         let currentQuantity = parseInt(quantityInput.value);
         let maxStock = parseInt(quantityInput.max);

         minusBtn.disabled = currentQuantity <= 1;
         plusBtn.disabled = currentQuantity >= maxStock;
         if (maxStock <= 0) { // Vô hiệu hóa cả 2 nếu hết hàng
             minusBtn.disabled = true;
             plusBtn.disabled = true;
         }
    }

    // Xử lý nút tăng số lượng
    plusBtn.addEventListener('click', () => {
        let currentQuantity = parseInt(quantityInput.value);
        let maxStock = parseInt(quantityInput.max);
        if (currentQuantity < maxStock) {
            quantityInput.value = currentQuantity + 1;
            updateQuantityButtons();
        }
    });

    // Xử lý nút giảm số lượng
    minusBtn.addEventListener('click', () => {
        let currentQuantity = parseInt(quantityInput.value);
        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
            updateQuantityButtons();
        }
    });

    // Xử lý nhập số lượng thủ công
    quantityInput.addEventListener('input', () => {
        let value = parseInt(quantityInput.value);
        let maxStock = parseInt(quantityInput.max);

        if (isNaN(value) || value < 1) {
            quantityInput.value = 1;
        } else if (value > maxStock) {
            quantityInput.value = maxStock;
        }
         updateQuantityButtons(); // Cập nhật nút sau khi nhập
    });

     // Khởi tạo trang khi load
     renderProductPage(productData);
     updateQuantityButtons(); // Cập nhật trạng thái nút số lượng ban đầu
});