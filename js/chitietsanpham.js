document.addEventListener('DOMContentLoaded', () => {

    // --- Lấy ID sản phẩm từ URL ---
    const urlParams = new URLSearchParams(window.location.search);
    const productId = urlParams.get('id');

    // URL của API chi tiết sản phẩm
    const apiUrl = `http://localhost:3000/api/products/${productId}`;

    // Lấy tham chiếu đến các phần tử HTML cần cập nhật (CHỈ LẤY MỘT LẦN)
    const productContainer = document.querySelector('.product-container');
    // Lấy thêm tham chiếu đến element loading ban đầu nếu có
    const loadingMessageElement = productContainer ? productContainer.querySelector('p[style*="text-align: center"]') : null;


    const productTitle = document.querySelector('.product-title');
    const ratingSpan = document.querySelector('.ratings-sold .rating'); // Thẻ span chứa rating number
    const starsSpan = document.querySelector('.ratings-sold .stars'); // Thẻ span chứa sao
    const reviewsCountElement = document.querySelector('.ratings-sold .reviews');
    const soldCountElement = document.querySelector('.ratings-sold .sold');
    const priceElement = document.querySelector('.price-section .price');
    const variationsSection = document.querySelector('.variations-section');
    const variationsOptionsContainer = document.querySelector('.variations-options');
    const quantityInput = document.querySelector('.quantity-input');
    const minusBtn = document.querySelector('.quantity-btn.minus');
    const plusBtn = document.querySelector('.quantity-btn.plus');
    const stockAvailableElement = document.querySelector('.stock-available');
    const mainImage = document.querySelector('.main-image');
    const thumbnailsContainer = document.querySelector('.thumbnails');
    const shippingInfoSection = document.querySelector('.shipping-info');
    const estDeliveryElement = document.querySelector('.shipping-info .est-delivery');
    const shippingFeeElement = document.querySelector('.shipping-info .shipping-fee');
    const voucherInfoDiv = document.querySelector('.shipping-info .voucher-info');
    const voucherTextElement = document.querySelector('.shipping-info .voucher-text');
    const returnPolicySection = document.querySelector('.return-policy');
    const returnPolicyTextElement = document.querySelector('.return-policy .policy-text');
    const returnDetailsLink = document.querySelector('.return-policy .return-details');
    const actionButtonsSection = document.querySelector('.action-buttons');
    const addToCartBtn = document.querySelector('.add-to-cart');
    const buyNowBtn = document.querySelector('.buy-now');
    const quantitySection = document.querySelector('.quantity-section');


    // Kiểm tra các phần tử HTML chính đã tồn tại chưa ngay từ đầu
     if (!productContainer || !productTitle || !priceElement || !mainImage || !thumbnailsContainer || !variationsSection || !shippingInfoSection || !returnPolicySection || !quantitySection || !actionButtonsSection || !variationsOptionsContainer || !quantityInput || !minusBtn || !plusBtn || !stockAvailableElement || !addToCartBtn || !buyNowBtn) {
        console.error("Lỗi: Thiếu một hoặc nhiều phần tử HTML cần thiết trên trang. Vui lòng kiểm tra file chitietsanpham.html và class/id.");
        // Hiển thị thông báo lỗi thân thiện
        if (productContainer) {
             productContainer.innerHTML = '<p style="text-align: center; color: red;">Không thể hiển thị chi tiết sản phẩm. Cấu trúc trang bị lỗi.</p>';
             productContainer.style.display = 'block';
        } else {
             document.body.innerHTML = '<p style="text-align: center; color: red;">Lỗi: Container sản phẩm chính không được tìm thấy.</p>';
        }
        return;
    }

    // Ban đầu hiển thị loading message và ẩn các phần khác (nếu có) trong container
    // HTML gốc chỉ có loading message, nên chỉ cần đảm bảo container hiển thị
    productContainer.style.display = 'block'; // Hoặc 'flex' tùy style ban đầu


    // --- Biến để lưu dữ liệu sản phẩm fetch được và stock tối đa hiện tại ---
    let currentProductData = null; // Lưu toàn bộ dữ liệu sản phẩm
    let currentMaxStock = 0; // Lưu stock tối đa có thể chọn cho biến thể/sản phẩm hiện tại


    // --- Hàm định dạng tiền tệ Việt Nam ---
    function formatCurrency(amount) {
        if (amount == null || typeof amount !== 'number') return 'N/A';
        return `₫${amount.toLocaleString('vi-VN')}`;
    }

    // --- Hàm tạo chuỗi sao dựa trên điểm trung bình ---
    function createStarRating(averageRating) {
        if (averageRating == null || typeof averageRating !== 'number') return '☆☆☆☆☆';
        const roundedRating = Math.round(averageRating);
        const fullStars = '★'.repeat(Math.max(0, Math.min(5, roundedRating)));
        const emptyStars = '☆'.repeat(Math.max(0, 5 - roundedRating));
        return fullStars + emptyStars;
    }

     // --- Hàm cập nhật class 'active' cho thumbnail ---
     function updateActiveThumbnail(imageUrl) {
          if (!thumbnailsContainer) return;

         thumbnailsContainer.querySelectorAll('.thumbnail').forEach(thumb => {
             thumb.classList.remove('active');
         });

         const imageUrlFileName = imageUrl ? imageUrl.split('/').pop() : null;
         if (!imageUrlFileName) return;

         const targetThumb = Array.from(thumbnailsContainer.querySelectorAll('.thumbnail'))
            .find(thumb => thumb.src && thumb.src.split('/').pop() === imageUrlFileName);

         if (targetThumb) {
             targetThumb.classList.add('active');
         }
     }


    // --- Hàm cập nhật trạng thái nút +/- số lượng ---
    function updateQuantityButtons() {
         if (!quantityInput || !minusBtn || !plusBtn || !stockAvailableElement) {
             console.error("Thiếu các phần tử điều khiển số lượng.");
             if (addToCartBtn) addToCartBtn.disabled = true;
             if (buyNowBtn) buyNowBtn.disabled = true;
             return;
         }

        let currentQuantity = parseInt(quantityInput.value);

        if (isNaN(currentQuantity) || currentQuantity < 0) {
            currentQuantity = 0;
        }
         if (currentQuantity > currentMaxStock && currentMaxStock > 0) {
             currentQuantity = currentMaxStock;
         } else if (currentMaxStock < 0) {
              currentMaxStock = 0;
         }

         quantityInput.value = currentQuantity;
         quantityInput.max = currentMaxStock;


        minusBtn.disabled = currentQuantity <= 0;
        plusBtn.disabled = currentQuantity >= currentMaxStock || currentMaxStock <= 0;
        quantityInput.disabled = currentMaxStock <= 0;


         const isQuantityValid = currentQuantity > 0 && currentQuantity <= currentMaxStock;
         const requiresVariation = variationsSection && variationsSection.style.display !== 'none';
         const variationSelected = currentProductData && currentProductData.selected_variation;


         if (addToCartBtn) {
             addToCartBtn.disabled = !isQuantityValid || (requiresVariation && !variationSelected);
         }
         if (buyNowBtn) {
             buyNowBtn.disabled = !isQuantityValid || (requiresVariation && !variationSelected);
         }

         stockAvailableElement.textContent = `${currentMaxStock.toLocaleString('vi-VN')} sản phẩm có sẵn`;
         if (currentMaxStock <= 0) {
              stockAvailableElement.textContent = `Hết hàng`;
         }
    }


    // --- Hàm xử lý khi chọn biến thể ---
    function handleVariationSelect(event) {
         if (!variationsSection || !variationsOptionsContainer || !currentProductData || !currentProductData.variations) {
             console.error("Thiếu các phần tử hoặc dữ liệu biến thể.");
             return;
         }

        const selectedButton = event.target;

        variationsOptionsContainer.querySelectorAll('.variation-option').forEach(button => {
            button.classList.remove('selected');
        });

        selectedButton.classList.add('selected');

        const selectedOptionValue = selectedButton.dataset.value;
        const selectedVariation = currentProductData.variations.find(v => v.option === selectedOptionValue);

        if (selectedVariation) {
             if (priceElement) priceElement.textContent = formatCurrency(selectedVariation.price);
             if (stockAvailableElement) stockAvailableElement.textContent = `${selectedVariation.stock} sản phẩm có sẵn`;
            currentMaxStock = selectedVariation.stock;
             if (quantityInput) quantityInput.value = 1;

            if (selectedVariation.image) {
                 if (mainImage) mainImage.src = selectedVariation.image;
                 updateActiveThumbnail(selectedVariation.image);
            } else {
                 if (currentProductData.product_main_image_url) {
                     if (mainImage) mainImage.src = currentProductData.product_main_image_url;
                     updateActiveThumbnail(currentProductData.product_main_image_url);
                 } else {
                     if (mainImage) mainImage.src = 'placeholder.jpg';
                     if (thumbnailsContainer) {
                          thumbnailsContainer.querySelectorAll('.thumbnail').forEach(thumb => thumb.classList.remove('active'));
                     }
                 }
            }
            updateQuantityButtons();

        } else {
             console.error("Không tìm thấy biến thể đã chọn trong dữ liệu sản phẩm.");
             if (priceElement) {
                 const minPrice = currentProductData.product_min_price || 0;
                 const maxPrice = currentProductData.product_max_price || 0;
                  if (minPrice === maxPrice) {
                      priceElement.textContent = formatCurrency(minPrice);
                  } else {
                      priceElement.textContent = `${formatCurrency(minPrice)} - ${formatCurrency(maxPrice)}`;
                  }
             }
             if (stockAvailableElement) stockAvailableElement.textContent = `Thông tin stock không xác định`;
             currentMaxStock = 0;
             if (quantityInput) quantityInput.value = 0;
             updateQuantityButtons();
        }
         currentProductData.selected_variation = selectedVariation;
    }


    // --- Hàm render toàn bộ trang sản phẩm dựa trên dữ liệu từ API ---
    function renderProductPage(product) {
         currentProductData = product;

         // Xóa loading message ban đầu (element p nằm trong productContainer)
         if (loadingMessageElement) {
              loadingMessageElement.remove();
         }
         // Đảm bảo container chính hiển thị nội dung (nếu ban đầu bị ẩn)
          if (productContainer) {
               productContainer.style.display = 'flex'; // Hoặc 'block' tùy style
          }


         // Cập nhật các element bằng dữ liệu từ API (Sử dụng các biến đã được lấy ở đầu file)
         if (productTitle) productTitle.textContent = product.product_name || 'Tên Sản Phẩm';

         // Cập nhật Rating, Reviews, Sold
         if (ratingSpan) {
              const ratingNumberSpan = ratingSpan.querySelector('.rating-number');
              if (ratingNumberSpan) ratingNumberSpan.textContent = product.product_star != null ? product.product_star.toFixed(1) : '0.0';
              if (starsSpan) starsSpan.textContent = createStarRating(product.product_star);
         } else { console.warn("Rating span element (.ratings-sold .rating) not found."); }
         if (reviewsCountElement) reviewsCountElement.textContent = `${product.product_review != null ? product.product_review.toLocaleString('vi-VN') : '0'} Đánh Giá`; else { console.warn("Reviews count element (.ratings-sold .reviews) not found."); }
         if (soldCountElement) soldCountElement.textContent = `${product.product_sold != null ? product.product_sold.toLocaleString('vi-VN') : '0'} Đã Bán`; else { console.warn("Sold count element (.ratings-sold .sold) not found."); }


        // Cập nhật giá
        const minPrice = product.product_min_price;
        const maxPrice = product.product_max_price;
        if (priceElement) {
            if (minPrice != null && maxPrice != null && minPrice === maxPrice) {
                priceElement.textContent = formatCurrency(minPrice);
            } else if (minPrice != null || maxPrice != null) {
                 const minPriceFormatted = minPrice != null ? formatCurrency(minPrice) : '...';
                 const maxPriceFormatted = maxPrice != null ? formatCurrency(maxPrice) : '...';
                 if (minPrice != null && maxPrice != null) {
                      priceElement.textContent = `${minPriceFormatted} - ${maxPriceFormatted}`;
                 } else if (minPrice != null) {
                      priceElement.textContent = minPriceFormatted;
                 } else {
                      priceElement.textContent = maxPriceFormatted;
                 }
            }
            else {
                 priceElement.textContent = 'Đang cập nhật giá';
             }
        } else { console.error("Price element (.price-section .price) not found."); }


        // Cập nhật ảnh và thumbnails
        if (mainImage && thumbnailsContainer) {
             // Xóa thumbnails mẫu trong HTML gốc
             thumbnailsContainer.innerHTML = '';

             if (product.images && product.images.length > 0) { // Nếu API trả về mảng images
                 product.images.forEach((image, index) => {
                     const img = document.createElement('img');
                     img.src = image;
                     img.alt = `${product.product_name || 'Product Image'} Thumbnail ${index + 1}`;
                     img.classList.add('thumbnail');

                     if (index === 0) {
                         img.classList.add('active');
                         mainImage.src = image;
                         mainImage.alt = product.product_name || 'Main Product Image';
                     }

                     img.addEventListener('click', () => {
                         if (mainImage) mainImage.src = img.src;
                         updateActiveThumbnail(img.src);
                     });
                     thumbnailsContainer.appendChild(img);
                 });
             } else if (product.product_main_image_url) {
                 const mainImageUrl = product.product_main_image_url;
                 mainImage.src = mainImageUrl;
                 mainImage.alt = product.product_name || 'Main Product Image';

                 const thumb = document.createElement('img');
                 thumb.src = mainImageUrl;
                 thumb.alt = `${product.product_name || 'Product Image'} Thumbnail`;
                 thumb.classList.add('thumbnail', 'active');

                 thumb.addEventListener('click', () => {
                      if (mainImage) mainImage.src = thumb.src;
                      updateActiveThumbnail(thumb.src);
                 });
                 thumbnailsContainer.appendChild(thumb);

             } else {
                 mainImage.src = 'placeholder.jpg';
                 mainImage.alt = 'No Image Available';
                 thumbnailsContainer.innerHTML = '<p style="font-size: 0.8em; color: #777;">Không có ảnh phụ</p>';
             }
        } else { console.error("Thiếu các phần tử ảnh chính hoặc container thumbnails trong HTML."); }


        // Render các tùy chọn biến thể
        if (variationsSection && variationsOptionsContainer) {
             if (product.variations && product.variations.length > 0) {
                 variationsSection.style.display = 'block';
                 variationsOptionsContainer.innerHTML = '';
                 const variationTypeLabel = document.createElement('span');
                 variationTypeLabel.textContent = (product.variations[0].type || 'Phân loại') + ': ';
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
                 currentMaxStock = totalStock;

             } else {
                 variationsSection.style.display = 'none';
                 const productSold = product.product_sold || 0;
                 currentMaxStock = product.stock != null ? product.stock : (productSold > 0 ? 10000 : 0);
             }
        } else { console.warn("Thiếu các phần tử cho Variations Section trong HTML. Ẩn phần này."); variationsSection.style.display = 'none'; } // Đảm bảo ẩn nếu thiếu element

        // Cập nhật thông tin vận chuyển
        if (shippingInfoSection) {
             if (estDeliveryElement) estDeliveryElement.textContent = product.shipping_info ? (product.shipping_info.estimated_delivery || '...') : 'Đang cập nhật...';
             if (shippingFeeElement) shippingFeeElement.textContent = product.shipping_info ? (product.shipping_info.shipping_fee === 0 ? 'Miễn Phí' : formatCurrency(product.shipping_info.shipping_fee || 0)) : 'Đang cập nhật...';
             if (voucherTextElement) {
                 voucherTextElement.textContent = (product.shipping_info && product.shipping_info.voucher_applied) ?
                                                `Voucher ${formatCurrency(product.shipping_info.voucher_amount || 0)} áp dụng.` :
                                                'Không có ưu đãi vận chuyển';
             }
             if (voucherInfoDiv) voucherInfoDiv.style.display = (product.shipping_info && product.shipping_info.voucher_applied) ? 'flex' : 'none'; // Hiện/ẩn voucher div con
        } else { console.warn("Shipping Info section not found in HTML."); }


        // Cập nhật thông tin đổi trả
        if (returnPolicySection && returnPolicyTextElement) {
             if (product.return_policy_text) {
                 returnPolicyTextElement.textContent = product.return_policy_text;
                 if (returnDetailsLink) returnDetailsLink.style.display = 'inline';
             } else {
                 returnPolicyTextElement.textContent = 'Chính sách đổi trả áp dụng.';
                 if (returnDetailsLink) returnDetailsLink.style.display = 'none';
             }
        } else { console.warn("Return Policy section or text element not found in HTML."); }


        // Cập nhật trạng thái ban đầu cho Quantity và Action Buttons
        if (quantitySection && actionButtonsSection && quantityInput && minusBtn && plusBtn && stockAvailableElement && addToCartBtn && buyNowBtn) {
             updateQuantityButtons();

             // Gán event listeners chỉ MỘT LẦN ở đây sau khi kiểm tra element tồn tại
             plusBtn.addEventListener('click', () => { if (quantityInput) quantityInput.value = parseInt(quantityInput.value) + 1; updateQuantityButtons(); });
             minusBtn.addEventListener('click', () => { if (quantityInput && parseInt(quantityInput.value) > 1) quantityInput.value = parseInt(quantityInput.value) - 1; updateQuantityButtons(); });
             quantityInput.addEventListener('input', () => {
                 setTimeout(() => {
                     let value = parseInt(quantityInput.value);
                     let maxStock = currentMaxStock;
                     if (isNaN(value) || value < 1) value = 1;
                     if (value > maxStock && maxStock > 0) value = maxStock;
                     quantityInput.value = value;
                     updateQuantityButtons();
                 }, 300);
             });
              quantityInput.addEventListener('blur', () => {
                   if (!quantityInput) return;
                   let value = parseInt(quantityInput.value);
                   let maxStock = currentMaxStock;
                   if (isNaN(value) || value < 1) value = 1;
                   if (value > maxStock && maxStock > 0) value = maxStock;
                    else if (maxStock <= 0) value = 0;
                   quantityInput.value = value;
                   updateQuantityButtons();
              });

              // Event listeners cho Add to Cart / Buy Now đã được gán ở cuối DOMContentLoaded

         } else { console.error("Thiếu các phần tử cho Quantity Section hoặc Action Buttons trong HTML."); }


         // Trạng thái disabled ban đầu cho Add to Cart / Buy Now được set trong updateQuantityButtons,
         // hàm này được gọi ở đây và cuối DOMContentLoaded.


    } // End of renderProductPage function


    // --- Logic cho nút Thêm vào giỏ hàng và Mua ngay ---
    // Gán event listeners chỉ MỘT LẦN ở cuối DOMContentLoaded
    if (addToCartBtn) {
        addToCartBtn.addEventListener('click', () => {
             if (!currentProductData || !quantityInput || !variationsSection || !actionButtonsSection) { // Thêm check actionButtonsSection
                  console.error("Missing data or elements for Add to Cart click handler.");
                  alert("Không thể thêm vào giỏ hàng. Dữ liệu sản phẩm hoặc cấu trúc trang bị lỗi.");
                  return;
             }
            const product = currentProductData;
            const selectedVariation = product.selected_variation;
            const quantity = parseInt(quantityInput.value);
            if (variationsSection.style.display !== 'none' && !selectedVariation) {
                alert("Vui lòng chọn phân loại sản phẩm!");
                return;
            }
             if (quantity <= 0 || quantity > currentMaxStock) {
                  alert(`Số lượng không hợp lệ! Vui lòng chọn số lượng từ 1 đến ${currentMaxStock}.`);
                  return;
             }
            const itemToAdd = {
                productId: product.product_id,
                productName: product.product_name,
                variation: selectedVariation ? {
                    option: selectedVariation.option,
                    price: selectedVariation.price,
                    image: selectedVariation.image
                 } : null,
                quantity: quantity,
                mainImage: product.product_main_image_url || (selectedVariation ? selectedVariation.image : null) || 'placeholder.jpg',
                 itemPrice: selectedVariation ? selectedVariation.price : (product.product_min_price != null ? product.product_min_price : 0)
            };
            const cartItemsString = localStorage.getItem('cartItems');
            let cartItems = [];
            if (cartItemsString) { try { cartItems = JSON.parse(cartItemsString); } catch (error) { console.error("Lỗi đọc dữ liệu giỏ hàng từ localStorage:", error); localStorage.removeItem('cartItems'); cartItems = []; } }
            const existingItemIndex = cartItems.findIndex(item => {
                const isSameProduct = item.productId === itemToAdd.productId;
                const isSameVariation = (item.variation === null && itemToAdd.variation === null) || (item.variation && itemToAdd.variation && item.variation.option === itemToAdd.variation.option);
                return isSameProduct && isSameVariation; });
            if (existingItemIndex > -1) {
                 const updatedQuantity = cartItems[existingItemIndex].quantity + itemToAdd.quantity;
                 const itemStock = selectedVariation ? selectedVariation.stock : currentMaxStock;
                 if (updatedQuantity > itemStock) { alert(`Tổng số lượng cho "${itemToAdd.productName} ${itemToAdd.variation ? '- ' + itemToAdd.variation.option : ''}" trong giỏ hàng và thêm mới (${updatedQuantity}) vượt quá số lượng có sẵn (${itemStock}).`); return; }
                cartItems[existingItemIndex].quantity = updatedQuantity;
                alert(`Đã cập nhật số lượng sản phẩm "${itemToAdd.productName} ${itemToAdd.variation ? '- ' + itemToAdd.variation.option : ''}" trong giỏ hàng.`);
            } else {
                 const itemStock = selectedVariation ? selectedVariation.stock : currentMaxStock;
                 if (itemToAdd.quantity > itemStock) { alert(`Số lượng thêm mới (${itemToAdd.quantity}) vượt quá số lượng có sẵn (${itemStock}).`); return; }
                cartItems.push(itemToAdd);
                alert("Đã thêm sản phẩm vào giỏ hàng!");
            }
            localStorage.setItem('cartItems', JSON.stringify(cartItems));
            console.log("Giỏ hàng trong localStorage:", cartItems);
        });
    }
     if (buyNowBtn) {
         buyNowBtn.addEventListener('click', () => {
              if (!currentProductData || !quantityInput || !variationsSection || !actionButtonsSection) { // Thêm check actionButtonsSection
                  console.error("Missing data or elements for Buy Now click handler.");
                   alert("Không thể mua ngay. Dữ liệu sản phẩm hoặc cấu trúc trang bị lỗi.");
                   return;
              }
             const product = currentProductData;
             const selectedVariation = product.selected_variation;
             const quantity = parseInt(quantityInput.value);
             if (variationsSection.style.display !== 'none' && !selectedVariation) { alert("Vui lòng chọn phân loại sản phẩm!"); return; }
             if (quantity <= 0 || quantity > currentMaxStock) { alert(`Số lượng không hợp lệ! Vui lòng chọn số lượng từ 1 đến ${currentMaxStock}.`); return; }
             const orderDataForSession = {
                 orderId: 'TEMP_' + Date.now(),
                 orderDate: new Date().toISOString().split('T')[0],
                 paymentMethod: 'Thanh toán khi nhận hàng',
                 shippingFee: product.shipping_info ? (product.shipping_info.shipping_fee != null ? product.shipping_info.shipping_fee : 0) : 0,
                 items: [{
                          productId: product.product_id,
                          productName: product.product_name,
                          variation: selectedVariation ? { option: selectedVariation.option, price: selectedVariation.price, image: selectedVariation.image } : null,
                          quantity: quantity,
                          mainImage: product.product_main_image_url || (selectedVariation ? selectedVariation.image : null) || 'placeholder.jpg',
                          itemPrice: selectedVariation ? selectedVariation.price : (product.product_min_price != null ? product.product_min_price : 0)
                     }],
                 itemsTotalAmount: (selectedVariation ? selectedVariation.price : (product.product_min_price != null ? product.product_min_price : 0)) * quantity
             };
             sessionStorage.setItem('currentOrderDetails', JSON.stringify(orderDataForSession));
             console.log("Lưu chi tiết đơn hàng vào sessionStorage:", orderDataForSession);
             window.location.href = 'xacnhanmuahang.html';
         });
     }


    // --- Logic chính: Fetch dữ liệu sản phẩm khi tải trang ---
    if (!productId) {
         if (productContainer) { productContainer.innerHTML = '<p style="text-align: center; color: red;">Lỗi: Không có ID sản phẩm được cung cấp trong URL.</p>'; productContainer.style.display = 'block'; }
         console.error("Không có ID sản phẩm trong URL."); return;
    }

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                if (response.status === 404) { throw new Error('Sản phẩm không tồn tại hoặc đã hết hàng.'); }
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(product => {
            if (product && product.product_id) {
                 renderProductPage(product); // <-- Gọi hàm render nếu data hợp lệ
                 console.log('Đã tải và hiển thị chi tiết sản phẩm:', product);
            } else {
                 const errorMsg = 'Không tìm thấy dữ liệu chi tiết cho sản phẩm này.';
                 if (productContainer) { productContainer.innerHTML = `<p style="text-align: center; color: red;">${errorMsg}</p>`; productContainer.style.display = 'block'; } else { document.body.innerHTML = `<p style="text-align: center; color: red;">${errorMsg}</p>`; }
                 console.error(errorMsg, product);
            }
        })
        .catch(error => {
            console.error('Có lỗi khi lấy chi tiết sản phẩm:', error);
             if (productContainer) { productContainer.innerHTML = `<p style="text-align: center; color: red;">Lỗi khi tải chi tiết sản phẩm: ${error.message}</p>`; productContainer.style.display = 'block'; } else { document.body.innerHTML = `<p style="text-align: center; color: red;">Lỗi khi tải chi tiết sản phẩm: ${error.message}</p>`; }
        });

    // Initial setup cho Quantity buttons và Action buttons
    // Kiểm tra sự tồn tại của các element số lượng và nút trước khi gán listeners và gọi update
    if (quantityInput && minusBtn && plusBtn && stockAvailableElement && addToCartBtn && buyNowBtn) {
         quantityInput.value = 1; // Set giá trị mặc định ban đầu
         currentMaxStock = 0; // Stock ban đầu là 0 cho đến khi data load
         updateQuantityButtons(); // Cập nhật trạng thái nút dựa trên stock ban đầu

    } else {
        console.error("Thiếu các phần tử HTML cho Quantity Section hoặc Action Buttons.");
        // Các nút Thêm/Mua sẽ bị disabled ở đầu updateQuantityButtons nếu thiếu element số lượng.
    }

});