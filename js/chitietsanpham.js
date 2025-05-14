document.addEventListener('DOMContentLoaded', () => {

    // Dữ liệu sản phẩm - Giữ nguyên như bạn đã cung cấp
    const productData = {
        "order": {
            "product_id": "XYZ123",
            "product_name": "Hộp Đựng Tai Nghe Chụp Tai Full Size, màng trợ giảng Kháng Nước cho tai nghe chụp tai cỡ vừa Chuptai-1",
            "price_range": {
                "min_price": 50000,
                "max_price": 115000
            },
            "variations": [
                {
                    "type": "Phân loại",
                    "option": "Đen Tiêu Chuẩn",
                    "price": 50000,
                    "stock": 150,
                    "image": "../assets/img/den.webp"
                },
                {
                    "type": "Phân loại",
                    "option": "Xám Tiêu Chuẩn",
                    "price": 115000,
                    "stock": 59,
                    "image": "../assets/img/xam.webp"
                },
                {
                    "type": "Phân loại",
                    "option": "Trắng Tiêu Chuẩn",
                    "price": 100000,
                    "stock": 0
                }
            ],
             "images": [
                 "../assets/img/mainheadphone.webp",
                 "../assets/img/den.webp",
                 "../assets/img/xam.webp"
             ],
            "selected_variation": null, // Ban đầu chưa chọn biến thể nào
            "quantity": 1, // Giá trị quantity sẽ được lấy từ input
            "shipping_info": {
                "estimated_delivery": "14 Th05 - 15 Th05",
                "shipping_fee": 0, // Giả định phí vận chuyển
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

    // Lấy tham chiếu đến các phần tử HTML sử dụng querySelector
    const productTitle = document.querySelector('.product-title');
    const ratingStars = document.querySelector('.ratings-sold .stars');
    const reviewsCount = document.querySelector('.ratings-sold .reviews');
    const soldCount = document.querySelector('.ratings-sold .sold');
    const priceElement = document.querySelector('.price-section .price');
    const variationsOptionsContainer = document.querySelector('.variations-options');
    const quantityInput = document.querySelector('.quantity-input');
    const minusBtn = document.querySelector('.quantity-btn.minus');
    const plusBtn = document.querySelector('.quantity-btn.plus');
    const stockAvailableElement = document.querySelector('.stock-available');
    const mainImage = document.querySelector('.main-image');
    const thumbnailsContainer = document.querySelector('.thumbnails');
    const estDeliveryElement = document.querySelector('.shipping-info .est-delivery');
    const shippingFeeElement = document.querySelector('.shipping-info .shipping-fee');
    const voucherTextElement = document.querySelector('.shipping-info .voucher-text');
    const returnPolicyTextElement = document.querySelector('.return-policy .policy-text');
    const addToCartBtn = document.querySelector('.add-to-cart');
    const buyNowBtn = document.querySelector('.buy-now'); // Thêm tham chiếu cho nút "Mua ngay"


    // Hàm định dạng tiền tệ Việt Nam
    function formatCurrency(amount) {
        return `₫${amount.toLocaleString('vi-VN')}`;
    }

    // Hàm cập nhật trạng thái nút +/- số lượng
    function updateQuantityButtons(maxStock) {
        let currentQuantity = parseInt(quantityInput.value);
        quantityInput.max = maxStock;

        minusBtn.disabled = currentQuantity <= 1 || maxStock <= 0;
        plusBtn.disabled = currentQuantity >= maxStock || maxStock <= 0;

        if (maxStock <= 0) {
            quantityInput.value = 0;
            quantityInput.disabled = true;
        } else {
             quantityInput.disabled = false;
        }

        if (currentQuantity > maxStock && maxStock > 0) {
             quantityInput.value = maxStock;
        } else if (maxStock <= 0) {
             quantityInput.value = 0;
        }
         currentQuantity = parseInt(quantityInput.value);
         minusBtn.disabled = currentQuantity <= 1 || maxStock <= 0;
         plusBtn.disabled = currentQuantity >= maxStock || maxStock <= 0;
    }


    // Hàm xử lý khi chọn biến thể
    function handleVariationSelect(event) {
        const selectedButton = event.target;
        const product = productData.order;

        variationsOptionsContainer.querySelectorAll('.variation-option').forEach(button => {
            button.classList.remove('selected');
        });

        selectedButton.classList.add('selected');

        const selectedOptionValue = selectedButton.dataset.value;
        product.selected_variation = product.variations.find(v => v.option === selectedOptionValue);

        if (product.selected_variation) {
            priceElement.textContent = formatCurrency(product.selected_variation.price);
            stockAvailableElement.textContent = `${product.selected_variation.stock} sản phẩm có sẵn`;

            if (product.selected_variation.image) {
                mainImage.src = product.selected_variation.image;
                 updateActiveThumbnail(product.selected_variation.image);
            } else {
                 if (product.images && product.images.length > 0) {
                     mainImage.src = product.images[0];
                     updateActiveThumbnail(product.images[0]);
                 }
            }

             quantityInput.value = 1;
             updateQuantityButtons(product.selected_variation.stock);

             // Bật nút thêm vào giỏ hàng và Mua ngay nếu biến thể có stock > 0
             if (addToCartBtn) {
                  addToCartBtn.disabled = product.selected_variation.stock <= 0;
             }
             if (buyNowBtn) { // Bật nút Mua ngay
                 buyNowBtn.disabled = product.selected_variation.stock <= 0;
             }


        } else {
             const minPrice = product.price_range.min_price;
             const maxPrice = product.price_range.max_price;
             if (minPrice === maxPrice) {
                  priceElement.textContent = formatCurrency(minPrice);
             } else {
                  priceElement.textContent = `${formatCurrency(minPrice)} - ${formatCurrency(maxPrice)}`;
             }
             stockAvailableElement.textContent = `0 sản phẩm có sẵn`;
             quantityInput.value = 0;
             updateQuantityButtons(0);

             // Vô hiệu hóa nút thêm vào giỏ hàng và Mua ngay
             if (addToCartBtn) { addToCartBtn.disabled = true; }
             if (buyNowBtn) { buyNowBtn.disabled = true; }
        }
    }

     // Hàm cập nhật class 'active' cho thumbnail
    function updateActiveThumbnail(imageUrl) {
         thumbnailsContainer.querySelectorAll('.thumbnail').forEach(thumb => {
              const thumbFileName = thumb.src.split('/').pop();
              const imageUrlFileName = imageUrl.split('/').pop();
             if (thumbFileName === imageUrlFileName) {
                  thumb.classList.add('active');
             } else {
                  thumb.classList.remove('active');
             }
         });
    }

    // Hàm render toàn bộ trang sản phẩm lần đầu
    function renderProductPage(data) {
        const product = data.order;

        productTitle.textContent = product.product_name;
        reviewsCount.textContent = `${product.ratings.reviews_count} Đánh Giá`;
        soldCount.textContent = `${product.ratings.sold_count} Đã Bán`;

        const minPrice = product.price_range.min_price;
        const maxPrice = product.price_range.max_price;
        if (minPrice === maxPrice) {
             priceElement.textContent = formatCurrency(minPrice);
        } else {
             priceElement.textContent = `${formatCurrency(minPrice)} - ${formatCurrency(maxPrice)}`;
        }

        estDeliveryElement.textContent = product.shipping_info.estimated_delivery;
        shippingFeeElement.textContent = product.shipping_info.shipping_fee === 0 ? 'Miễn Phí' : formatCurrency(product.shipping_info.shipping_fee);
        voucherTextElement.textContent = product.shipping_info.voucher_applied ? `Voucher ₫${product.shipping_info.voucher_amount.toLocaleString('vi-VN')} nếu giao sau thời gian trên.` : 'Không có voucher vận chuyển';
        returnPolicyTextElement.textContent = product.return_policy_text;

        // Render danh sách ảnh thumbnail
        if (product.images && product.images.length > 0) {
            thumbnailsContainer.innerHTML = '';
            product.images.forEach((image, index) => {
                const img = document.createElement('img');
                img.src = image;
                img.alt = `Thumbnail ${index + 1}`;
                img.classList.add('thumbnail');

                if (index === 0) {
                    img.classList.add('active');
                    mainImage.src = image;
                }

                img.addEventListener('click', () => {
                    mainImage.src = img.src;
                    updateActiveThumbnail(img.src);
                });

                thumbnailsContainer.appendChild(img);
            });
        } else {
             mainImage.src = 'placeholder.jpg';
             thumbnailsContainer.innerHTML = '<p>Không có ảnh</p>';
        }

        // Render các tùy chọn biến thể
        if (product.variations && product.variations.length > 0) {
            variationsOptionsContainer.innerHTML = '';
             const variationTypeLabel = document.createElement('span');
             variationTypeLabel.textContent = product.variations[0].type + ': ';
             variationTypeLabel.style.fontWeight = 'bold';
             variationsOptionsContainer.appendChild(variationTypeLabel);

            product.variations.forEach(variation => {
                const button = document.createElement('button');
                button.classList.add('variation-option');
                button.textContent = variation.option;
                button.dataset.value = variation.option;
                button.dataset.price = variation.price;
                button.dataset.stock = variation.stock;

                if (variation.stock === 0) {
                    button.disabled = true;
                     button.style.textDecoration = 'line-through';
                     button.style.opacity = '0.7';
                     button.style.cursor = 'not-allowed';
                } else {
                    button.addEventListener('click', handleVariationSelect);
                }
                variationsOptionsContainer.appendChild(button);
            });

             const totalStock = product.variations.reduce((sum, v) => sum + (v.stock > 0 ? v.stock : 0), 0);
             stockAvailableElement.textContent = `${totalStock} sản phẩm có sẵn`;
             updateQuantityButtons(totalStock);


        } else {
             variationsOptionsContainer.innerHTML = '<p>Không có tùy chọn phân loại.</p>';
             const orderStock = product.stock || 0;
             stockAvailableElement.textContent = `${orderStock} sản phẩm có sẵn`;
             updateQuantityButtons(orderStock);

             if (addToCartBtn) { addToCartBtn.disabled = orderStock <= 0; }
             if (buyNowBtn) { buyNowBtn.disabled = orderStock <= 0; }
        }
    }

    // Xử lý nút tăng số lượng
    plusBtn.addEventListener('click', () => {
        let currentQuantity = parseInt(quantityInput.value);
        let maxStock = parseInt(quantityInput.max);
        if (currentQuantity < maxStock) {
            quantityInput.value = currentQuantity + 1;
            updateQuantityButtons(maxStock);
        }
    });

    // Xử lý nút giảm số lượng
    minusBtn.addEventListener('click', () => {
        let currentQuantity = parseInt(quantityInput.value);
        let maxStock = parseInt(quantityInput.max);
        if (currentQuantity > 1) {
            quantityInput.value = currentQuantity - 1;
            updateQuantityButtons(maxStock);
        }
    });

    // Xử lý nhập số lượng thủ công
    quantityInput.addEventListener('input', () => {
        let value = parseInt(quantityInput.value);
        let maxStock = parseInt(quantityInput.max);

        if (isNaN(value) || value < 1) {
             value = 1;
        } else if (value > maxStock) {
             value = maxStock;
        }
         quantityInput.value = value;
        updateQuantityButtons(maxStock);
    });

    // --- Logic THÊM VÀO GIỎ HÀNG ---
    if (addToCartBtn) {
        addToCartBtn.addEventListener('click', () => {
            const product = productData.order;
            const selectedVariation = product.selected_variation;
            const quantity = parseInt(quantityInput.value);

            if (!selectedVariation) {
                alert("Vui lòng chọn phân loại sản phẩm!");
                return;
            }
             if (quantity <= 0 || quantity > selectedVariation.stock) {
                 alert("Số lượng không hợp lệ!");
                 return;
             }

            const cartItemsString = localStorage.getItem('cartItems');
            let cartItems = [];

            if (cartItemsString) {
                try {
                    cartItems = JSON.parse(cartItemsString);
                } catch (error) {
                    console.error("Lỗi đọc dữ liệu giỏ hàng từ localStorage:", error);
                    localStorage.removeItem('cartItems');
                    cartItems = [];
                }
            }

            const newItem = {
                productId: product.product_id,
                productName: product.product_name,
                variation: {
                    option: selectedVariation.option,
                    price: selectedVariation.price,
                    // image: selectedVariation.image
                },
                quantity: quantity
            };

            const existingItemIndex = cartItems.findIndex(item =>
                item.productId === newItem.productId &&
                item.variation.option === newItem.variation.option
            );

            if (existingItemIndex > -1) {
                cartItems[existingItemIndex].quantity += newItem.quantity;
            } else {
                cartItems.push(newItem);
            }

            localStorage.setItem('cartItems', JSON.stringify(cartItems));
            console.log("Giỏ hàng trong localStorage:", cartItems);
            window.location.href = 'giohang.html'; // Điều chỉnh đường dẫn nếu cấu trúc khác

        });
    }

    // --- Logic MUA NGAY (Chuyển hướng sang trang đơn hàng ngay lập tức) ---
    if (buyNowBtn) {
        buyNowBtn.addEventListener('click', () => {
            const product = productData.order;
            const selectedVariation = product.selected_variation;
            const quantity = parseInt(quantityInput.value);

             // Kiểm tra giống thêm vào giỏ hàng
            if (!selectedVariation) {
                alert("Vui lòng chọn phân loại sản phẩm!");
                return;
            }
             if (quantity <= 0 || quantity > selectedVariation.stock) {
                 alert("Số lượng không hợp lệ!");
                 return;
             }

            // Chuẩn bị dữ liệu cho đơn hàng (chỉ 1 sản phẩm này)
            const orderDataForSession = {
                // Tạo ID đơn hàng tạm thời (trong thực tế cần backend tạo)
                orderId: 'TEMP_' + Date.now(),
                // Ngày đặt hàng hiện tại
                orderDate: new Date().toISOString().split('T')[0], // Format YYYY-MM-DD
                // Giả định phương thức thanh toán
                paymentMethod: 'Thanh toán khi nhận hàng',
                // Giả định phí vận chuyển (lấy từ productData)
                shippingFee: product.shipping_info.shipping_fee,
                // Danh sách các items trong đơn hàng (chỉ có 1 item)
                items: [
                    {
                         productId: product.product_id,
                         productName: product.product_name,
                         variation: {
                             option: selectedVariation.option,
                             price: selectedVariation.price,
                         },
                         quantity: quantity
                    }
                ],
                // Tính tổng tiền hàng cho đơn hàng này
                itemsTotalAmount: selectedVariation.price * quantity
            };

            // Lưu dữ liệu đơn hàng vào sessionStorage
            sessionStorage.setItem('currentOrderDetails', JSON.stringify(orderDataForSession));
             console.log("Lưu chi tiết đơn hàng vào sessionStorage:", orderDataForSession);

            // Chuyển hướng sang trang chi tiết đơn hàng
            window.location.href = 'xacnhanmuahang.html'; // Điều chỉnh đường dẫn nếu cấu trúc khác
        });
    }


    // --- Khởi tạo trang khi load ---
    renderProductPage(productData);

     // Vô hiệu hóa nút Mua ngay và Thêm giỏ hàng ban đầu nếu chưa chọn biến thể hoặc hết hàng
     if (addToCartBtn) addToCartBtn.disabled = true;
     if (buyNowBtn) buyNowBtn.disabled = true;
      // Logic renderProductPage và handleVariationSelect sẽ bật lại nút khi có biến thể hợp lệ được chọn
});