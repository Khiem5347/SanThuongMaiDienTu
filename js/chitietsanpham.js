// [Thư mục gốc]/js/chitietsanpham.js
document.addEventListener('DOMContentLoaded', async () => {
    // Lấy các phần tử DOM chính - thực hiện MỘT LẦN khi DOM sẵn sàng
    const productContainer = document.querySelector('.product-container');
    const imageGalleryElement = productContainer?.querySelector('.image-gallery');
    const productDetailsElement = productContainer?.querySelector('.product-details');

    const mainImageElement = productContainer?.querySelector('.main-image');
    const thumbnailsContainer = productContainer?.querySelector('.thumbnails');
    const productTitleElement = productContainer?.querySelector('.product-title');
    const ratingSpan = productContainer?.querySelector('.ratings-sold .rating .stars');
    const ratingValueSpan = productContainer?.querySelector('.ratings-sold .rating');
    const reviewsSpan = productContainer?.querySelector('.ratings-sold .reviews');
    const soldSpan = productContainer?.querySelector('.ratings-sold .sold');
    const priceElement = productContainer?.querySelector('.price-section .price');
    const colorOptionsContainer = document.getElementById('color-options');
    const sizeOptionsContainer = document.getElementById('size-options');
    const quantityInputElement = productContainer?.querySelector('.quantity-input');
    const stockAvailableElement = productContainer?.querySelector('.stock-available');
    const minusQtyButton = productContainer?.querySelector('.quantity-btn.minus');
    const plusQtyButton = productContainer?.querySelector('.quantity-btn.plus');
    const addToCartButton = productContainer?.querySelector('.add-to-cart');
    const buyNowButton = productContainer?.querySelector('.buy-now');
    // THÊM DOM ELEMENTS MỚI
    const shopInfoSection = document.getElementById('shop-info-section');
    const shopNameElement = shopInfoSection?.querySelector('.shop-name');
    const viewShopBtn = document.getElementById('viewShopBtn');
    const reviewsListContainer = document.getElementById('reviews-list');
    const reviewForm = document.getElementById('review-form');
    const reviewLoginPrompt = document.getElementById('review-login-prompt');
    const submitReviewBtn = document.getElementById('submit-review-btn');
    const starRatingInputContainer = reviewForm?.querySelector('.rating-input .stars');

    // Các biến trạng thái
    let currentProductId = null;
    let mainProductData = null;
    let generalProductImages = [];
    let colorVariants = [];
    let selectedColorVariant = null;
    let selectedProductSize = null;
    let currentQuantity = 1;

    console.log("[DEBUG] chitietsanpham.js: Script đã được tải và DOMContentLoaded được kích hoạt.");

    const formatCurrency = (amount) => {
        if (typeof amount !== 'number') {
            amount = parseFloat(amount);
        }
        if (isNaN(amount)) {
            return 'N/A'; // Hoặc một giá trị mặc định khác
        }
        return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' }).replace('₫', 'đ');
    };

    function getProductIdFromUrl() {
        const urlParams = new URLSearchParams(window.location.search);
        const id = parseInt(urlParams.get('id'));
        console.log("[DEBUG] getProductIdFromUrl: Product ID từ URL là:", id);
        return id;
    }

    async function fetchData(url, entityName) {
        console.log(`[DEBUG] fetchData: Bắt đầu fetch ${entityName} từ ${url}`);
        try {
            const response = await fetch(url);
            console.log(`[DEBUG] fetchData: Response status cho ${entityName}: ${response.status}`);
            if (!response.ok) {
                const errorText = await response.text();
                console.error(`[DEBUG] fetchData: Lỗi fetch ${entityName}. Status: ${response.status}, Text: ${errorText}`);
                throw new Error(`Lỗi HTTP khi tải ${entityName}! Status: ${response.status}.`);
            }
            const data = await response.json();
            console.log(`[DEBUG] fetchData: Dữ liệu ${entityName} nhận được:`, data);
            return data;
        } catch (error) {
            console.error(`[DEBUG] fetchData: Lỗi nghiêm trọng khi fetch ${entityName} từ ${url}:`, error);
            if (productContainer && entityName === 'chi tiết sản phẩm chính') {
                if(!productContainer.querySelector('.error-message-main')) {
                    const errorP = document.createElement('p');
                    errorP.className = 'error-message-main';
                    errorP.style.cssText = 'color:red; text-align:center; padding:50px;';
                    errorP.textContent = `Không thể tải ${entityName}. ${error.message}`;
                    productContainer.innerHTML = '';
                    productContainer.appendChild(errorP);
                }
            }
            return null;
        }
    }

    function renderProductDetails(data) {
        console.log("[DEBUG] renderProductDetails: Bắt đầu render chi tiết sản phẩm chính:", data);
        if (!data) {
            console.error("[DEBUG] renderProductDetails: Không có dữ liệu chi tiết sản phẩm để render.");
            return;
        }
        mainProductData = data;

        if (productTitleElement) productTitleElement.textContent = data.productName || 'N/A';
        
        if (ratingValueSpan && ratingSpan) {
            const starRating = data.productStar || 0;
            const starsHTML = '★'.repeat(Math.round(starRating)) + '☆'.repeat(5 - Math.round(starRating));
            const ratingTextNode = Array.from(ratingValueSpan.childNodes).find(node => node.nodeType === Node.TEXT_NODE && node.nodeValue.trim() !== '');
            if(ratingTextNode) ratingTextNode.nodeValue = `${starRating.toFixed(1)} `;
            else {
                const starSpanChild = ratingValueSpan.querySelector('.stars');
                if(starSpanChild) ratingValueSpan.insertBefore(document.createTextNode(`${starRating.toFixed(1)} `), starSpanChild);
                else ratingValueSpan.prepend(`${starRating.toFixed(1)} `);
            }
            ratingSpan.innerHTML = starsHTML;
        }

        if (reviewsSpan) reviewsSpan.textContent = `${data.productReview || 0} Đánh Giá`;
        if (soldSpan) soldSpan.textContent = `${data.productSold || 0} Đã Bán`;
        updateDisplayedPriceRange();
    }

    function renderGeneralProductImages(images) {
       console.log("[DEBUG] renderGeneralProductImages: Bắt đầu render ảnh chung:", images);
        generalProductImages = images || [];
        if (!mainImageElement || !thumbnailsContainer) {
            console.warn("[DEBUG] renderGeneralProductImages: Thiếu mainImageElement hoặc thumbnailsContainer.");
            return;
        }
        thumbnailsContainer.innerHTML = '';

        const imagesToDisplay = generalProductImages.length > 0 ? generalProductImages : 
                                (mainProductData && mainProductData.productMainImageUrl ? [{ productUrl: mainProductData.productMainImageUrl, isMainPlaceholder: true }] : []);

        if (imagesToDisplay.length > 0) {
            const initialMainImageUrl = imagesToDisplay[0].productUrl ? `/dumps/${imagesToDisplay[0].productUrl}` : '../assets/default-avatar.png';
            mainImageElement.src = initialMainImageUrl;
            mainImageElement.alt = mainProductData?.productName || 'Ảnh sản phẩm';

            imagesToDisplay.forEach((image, index) => {
                const thumbUrl = image.productUrl ? `/dumps/${image.productUrl}` : '../assets/placeholder-thumb.png';
                const thumb = document.createElement('img');
                thumb.src = thumbUrl;
                thumb.alt = `Thumbnail ${index + 1}`;
                thumb.classList.add('thumbnail');
                if (index === 0 && !selectedColorVariant?.imageUrl) thumb.classList.add('active');
                
                thumb.addEventListener('click', () => {
                    mainImageElement.src = thumbUrl;
                    document.querySelectorAll('.thumbnails .thumbnail').forEach(t => t.classList.remove('active'));
                    thumb.classList.add('active');
                });
                thumbnailsContainer.appendChild(thumb);
            });
        } else {
            mainImageElement.src = '../assets/default-avatar.png';
            mainImageElement.alt = 'Không có ảnh sản phẩm';
        }
    }
    
    function renderColorVariantsUI(variants) {
        console.log("[DEBUG] renderColorVariantsUI: Bắt đầu render biến thể màu:", variants);
        colorVariants = variants || [];
        if (!colorOptionsContainer) {
            console.warn("[DEBUG] renderColorVariantsUI: Thiếu colorOptionsContainer.");
            return;
        }
        colorOptionsContainer.innerHTML = ''; 

        if (colorVariants.length > 0) {
            sizeOptionsContainer.innerHTML = '<p class="select-color-first-text">Vui lòng chọn màu sắc...</p>';
            colorVariants.forEach((variant, index) => {
                const colorButton = document.createElement('button');
                colorButton.classList.add('variation-option');
                colorButton.textContent = variant.color || `Màu ${index + 1}`;
                colorButton.dataset.variantId = variant.variantId;

                colorButton.addEventListener('click', () => {
                    document.querySelectorAll('#color-options .variation-option').forEach(btn => btn.classList.remove('selected'));
                    colorButton.classList.add('selected');
                    handleColorSelection(variant);
                });
                colorOptionsContainer.appendChild(colorButton);
            });
            
            if(colorOptionsContainer.children[0]) {
                colorOptionsContainer.children[0].click();
            }
            
        } else {
            colorOptionsContainer.innerHTML = '<p>Sản phẩm này không có tùy chọn màu.</p>';
            sizeOptionsContainer.innerHTML = '<p>Sản phẩm này không có tùy chọn kích thước.</p>';
            updatePriceAndStock(null);
            console.log("[DEBUG] renderColorVariantsUI: Không có biến thể, sử dụng dữ liệu sản phẩm gốc.");
        }
        console.log("[DEBUG] renderColorVariantsUI: Hoàn tất render biến thể màu.");
    }
    
    function handleColorSelection(variant) {
        selectedColorVariant = variant;
        selectedProductSize = null; 
        console.log("[DEBUG] handleColorSelection: Đã chọn màu:", selectedColorVariant);

        if (mainImageElement && variant.imageUrl) {
            const variantImageUrl = `/dumps/${variant.imageUrl}`;
            mainImageElement.src = variantImageUrl;
            document.querySelectorAll('.thumbnails .thumbnail').forEach(t => t.classList.remove('active'));
             if(thumbnailsContainer) {
                const activeThumb = Array.from(thumbnailsContainer.children).find(thumb => thumb.src.includes(variant.imageUrl));
                if (activeThumb) activeThumb.classList.add('active');
            }
        } else if (mainImageElement && generalProductImages.length > 0 && generalProductImages[0].productUrl) {
             mainImageElement.src = `/dumps/${generalProductImages[0].productUrl}`;
        } else if (mainImageElement && mainProductData && mainProductData.productMainImageUrl) {
             mainImageElement.src = `/dumps/${mainProductData.productMainImageUrl}`;
        }

        const hasSizes = variant.productSizes && variant.productSizes.length > 0;
        if (hasSizes) {
            renderSizeOptionsUI(variant.productSizes);
            if (stockAvailableElement) stockAvailableElement.textContent = 'Vui lòng chọn kích thước';
            updateDisplayedPriceRange(); 
            updateQuantityControls(0); 
        } else {
            console.log("[DEBUG] handleColorSelection: Biến thể màu này không có size. Cập nhật dựa trên sản phẩm chính.");
            sizeOptionsContainer.innerHTML = '<p>Màu này không có tùy chọn kích thước.</p>';
            updatePriceAndStock(variant); 
        }
    }
    
    function renderSizeOptionsUI(sizes) {
        console.log("[DEBUG] renderSizeOptionsUI: Bắt đầu render kích thước:", sizes);
        if (!sizeOptionsContainer) {
             console.warn("[DEBUG] renderSizeOptionsUI: Thiếu sizeOptionsContainer.");
             return;
        }
        sizeOptionsContainer.innerHTML = '';

        if (sizes && sizes.length > 0) {
            sizes.forEach((sizeData) => {
                const sizeButton = document.createElement('button');
                sizeButton.classList.add('variation-option');
                sizeButton.textContent = sizeData.size || 'N/A';
                sizeButton.dataset.sizeInfo = JSON.stringify(sizeData);

                sizeButton.addEventListener('click', () => {
                    document.querySelectorAll('#size-options .variation-option').forEach(btn => btn.classList.remove('selected'));
                    sizeButton.classList.add('selected');
                    handleSizeSelection(sizeData);
                });
                sizeOptionsContainer.appendChild(sizeButton);
            });
            if(sizeOptionsContainer.children[0]) {
                sizeOptionsContainer.children[0].click();
            }
        } else {
            sizeOptionsContainer.innerHTML = '<p>Màu này không có tùy chọn kích thước hoặc đã hết hàng.</p>';
            updatePriceAndStock(selectedColorVariant); 
        }
    }
    
    function handleSizeSelection(sizeData) {
        selectedProductSize = sizeData;
        console.log('[DEBUG] handleSizeSelection: Đã chọn size:', selectedProductSize);
        updatePriceAndStock(selectedProductSize);
    }

    function updatePriceAndStock(selection) {
        console.log("[DEBUG] updatePriceAndStock: Bắt đầu cập nhật với lựa chọn:", selection);
        let displayPrice = 0;
        let availableStock = 0;
        let stockText = 'Sản phẩm tạm thời hết hàng';

        if (selection && selection.sizeId) {
            console.log("[DEBUG] updatePriceAndStock: Xử lý theo 'Kích thước cụ thể'");
            displayPrice = selection.price;
            availableStock = selection.quantity || 0;
        } else {
            console.log("[DEBUG] updatePriceAndStock: Xử lý theo 'Sản phẩm chính (Fallback)'");
            if (mainProductData) {
                availableStock = mainProductData.remainingQuantity || 0;
            }

            if (selection && selection.variantId && selection.price) {
                displayPrice = selection.price;
            } else if (mainProductData) {
                updateDisplayedPriceRange();
            } else {
                priceElement.textContent = formatCurrency(0);
            }
        }

        if (displayPrice > 0) {
             if (priceElement) priceElement.textContent = formatCurrency(displayPrice);
        }

        if (stockAvailableElement) {
            stockText = `${availableStock} sản phẩm có sẵn`;
            stockAvailableElement.textContent = stockText;
        }
        
        currentQuantity = (availableStock > 0) ? 1 : 0;
        if (quantityInputElement) quantityInputElement.value = currentQuantity;
        updateQuantityControls(availableStock);
    }
    
    function updateDisplayedPriceRange() {
        if (!priceElement) return;

        console.log("[DEBUG] updateDisplayedPriceRange: Cập nhật khoảng giá hiển thị.");
        if (selectedColorVariant && selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0) {
            const pricesInVariant = selectedColorVariant.productSizes.map(s => s.price);
            const min = Math.min(...pricesInVariant);
            const max = Math.max(...pricesInVariant);
            priceElement.textContent = (min === max) ? formatCurrency(min) : `${formatCurrency(min)} - ${formatCurrency(max)}`;
        } else if (mainProductData) {
            const minPrice = mainProductData.productMinPrice || 0;
            const maxPrice = mainProductData.productMaxPrice || 0;
            if (minPrice === maxPrice || maxPrice === 0) {
                 priceElement.textContent = formatCurrency(minPrice);
            } else {
                 priceElement.textContent = `${formatCurrency(minPrice)} - ${formatCurrency(maxPrice)}`;
            }
        } else {
            priceElement.textContent = formatCurrency(0);
        }
    }

    function updateQuantityControls(maxStock) {
       if (!quantityInputElement || !minusQtyButton || !plusQtyButton) return;
        
        maxStock = maxStock || 0; 
        const currentVal = parseInt(quantityInputElement.value) || 0;
        
        minusQtyButton.disabled = currentVal <= 1;
        plusQtyButton.disabled = currentVal >= maxStock;

        if (maxStock === 0) {
             quantityInputElement.value = 0;
             currentQuantity = 0;
             minusQtyButton.disabled = true;
             plusQtyButton.disabled = true;
        }
    }
    
    function getAvailableStock() {
        if (selectedProductSize) {
            return selectedProductSize.quantity || 0;
        }
        return mainProductData?.remainingQuantity || 0;
    }

    if (minusQtyButton && quantityInputElement) {
        minusQtyButton.addEventListener('click', () => {
            let val = parseInt(quantityInputElement.value);
            if (val > 1) {
                val--;
                quantityInputElement.value = val;
                currentQuantity = val;
                updateQuantityControls(getAvailableStock());
            }
        });
    }

    if (plusQtyButton && quantityInputElement) {
        plusQtyButton.addEventListener('click', () => {
            let val = parseInt(quantityInputElement.value);
            const stock = getAvailableStock();
            if (val < stock) {
                val++;
                quantityInputElement.value = val;
                currentQuantity = val;
                updateQuantityControls(stock);
            } else {
                 if (stock > 0) alert(`Số lượng không thể vượt quá ${stock} sản phẩm có sẵn.`);
                 else alert('Sản phẩm hiện đã hết hàng hoặc vui lòng chọn phân loại.');
            }
        });
    }

    if (quantityInputElement) {
        quantityInputElement.addEventListener('change', () => {
            let val = parseInt(quantityInputElement.value);
            const stock = getAvailableStock();
            if (isNaN(val) || val < 1) {
                val = stock > 0 ? 1 : 0;
            } else if (val > stock) {
                val = stock;
                if (stock > 0) alert(`Chỉ còn ${stock} sản phẩm có sẵn.`);
            }
            quantityInputElement.value = val;
            currentQuantity = val;
            updateQuantityControls(stock);
        });
    }

    const handleAddToCartOrBuyNow = (isBuyNow = false) => {
        console.log(`[DEBUG] handleAddToCartOrBuyNow (isBuyNow: ${isBuyNow}):`, { selectedColorVariant, selectedProductSize, currentQuantity });

        let finalSelection = null;
        let stockToCheck = 0;
        let selectionText = 'Mặc định';
        let finalPrice = mainProductData?.productMinPrice || 0;
        let finalImageUrl = mainImageElement ? mainImageElement.src : '';

        if (colorVariants.length > 0) {
            if (!selectedColorVariant) {
                alert('Vui lòng chọn màu sắc.');
                return;
            }
            
            const hasSizes = selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0;
            if (hasSizes) { 
                if (!selectedProductSize) {
                    alert('Vui lòng chọn kích thước.');
                    return;
                }
                finalSelection = selectedProductSize;
                stockToCheck = finalSelection.quantity || 0;
                selectionText = `${selectedColorVariant.color} - ${finalSelection.size}`;
                finalPrice = finalSelection.price;
            } else { 
                finalSelection = selectedColorVariant;
                stockToCheck = mainProductData?.remainingQuantity || 0; 
                selectionText = finalSelection.color;
                finalPrice = finalSelection.price || mainProductData.productMinPrice;
            }
            if (selectedColorVariant.imageUrl) {
                 finalImageUrl = `/dumps/${selectedColorVariant.imageUrl}`;
            }
        } else {
            finalSelection = mainProductData;
            stockToCheck = finalSelection?.remainingQuantity || 0; 
        }

        if (currentQuantity <= 0) {
            alert('Vui lòng chọn số lượng lớn hơn 0. Sản phẩm có thể đã hết hàng.');
            return;
        }
        if (currentQuantity > stockToCheck) {
            alert(`Số lượng tồn kho không đủ. Chỉ còn ${stockToCheck} sản phẩm.`);
            return;
        }

        const item = {
            productId: currentProductId,
            productName: mainProductData?.productName || 'N/A',
            variantId: selectedColorVariant?.variantId,
            sizeId: selectedProductSize?.sizeId,
            variation: {
                option: selectionText,
                price: finalPrice,
                imageUrl: finalImageUrl
            },
            quantity: currentQuantity
        };
        
        console.log(`[DEBUG] Item to process (isBuyNow: ${isBuyNow}):`, item);

        if (isBuyNow) {
            const orderDataForSession = {
                orderId: 'BUYNOW_' + Date.now(),
                orderDate: new Date().toISOString().split('T')[0],
                paymentMethod: 'Thanh toán khi nhận hàng',
                shippingFee: 30000, 
                items: [item],
                itemsTotalAmount: item.variation.price * item.quantity
            };
            sessionStorage.setItem('currentOrderDetails', JSON.stringify(orderDataForSession));
            console.log("Lưu chi tiết đơn hàng (Mua Ngay) vào sessionStorage:", orderDataForSession);
            window.location.href = 'xacnhanmuahang.html';
        } else {
            let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
            const existingItemIndex = cartItems.findIndex(cartItem =>
                cartItem.productId === item.productId &&
                cartItem.variantId === item.variantId &&
                cartItem.sizeId === item.sizeId
            );

            if (existingItemIndex > -1) {
                cartItems[existingItemIndex].quantity += item.quantity;
                if (cartItems[existingItemIndex].quantity > stockToCheck) {
                    cartItems[existingItemIndex].quantity = stockToCheck;
                    alert(`Đã cập nhật số lượng trong giỏ. Số lượng tối đa cho sản phẩm này là ${stockToCheck}.`);
                }
            } else {
                cartItems.push(item);
            }
            localStorage.setItem('cartItems', JSON.stringify(cartItems));
            alert(`Đã thêm "${item.productName} (${item.variation.option})" vào giỏ hàng!`);
            window.location.href = 'giohang.html';
        }
    };
    
    if (addToCartButton) {
        addToCartButton.addEventListener('click', () => handleAddToCartOrBuyNow(false));
    }
    
    if (buyNowButton) {
        buyNowButton.addEventListener('click', () => handleAddToCartOrBuyNow(true));
    }

    // --- CÁC HÀM CHO SHOP VÀ ĐÁNH GIÁ (ĐÃ CẬP NHẬT) ---

    function displayShopInfo(product) {
        if (!shopInfoSection || !product?.shopId || !product?.shopName) {
            if (shopInfoSection) shopInfoSection.style.display = 'none';
            return;
        }
        if (shopNameElement) shopNameElement.textContent = product.shopName;
        if (viewShopBtn) {
            viewShopBtn.onclick = () => window.location.href = `../pages/shop.html?id=${product.shopId}`;
        }
        shopInfoSection.style.display = 'flex';
    }

    /**
     * [CẬP NHẬT] Hàm render bình luận từ API.
     * Đảm bảo đường dẫn ảnh luôn có tiền tố "/dumps/".
     * @param {Array} reviews - Mảng các đối tượng bình luận từ API.
     */
    function renderProductReviews(reviews) {
        if (!reviewsListContainer) return;
        reviewsListContainer.innerHTML = '';
        if (!reviews || reviews.length === 0) {
            reviewsListContainer.innerHTML = '<p>Chưa có đánh giá nào cho sản phẩm này.</p>';
            return;
        }

        reviews.forEach(review => {
            const reviewItem = document.createElement('div');
            reviewItem.className = 'review-item';
            const stars = '★'.repeat(review.rating || 0) + '☆'.repeat(5 - (review.rating || 0));

            let imagesHTML = '';
            if (review.reviewImages && review.reviewImages.length > 0) {
                const imageItemsHTML = review.reviewImages.map(img => 
                    // [YÊU CẦU] Đảm bảo đường dẫn ảnh luôn có /dumps/ ở trước
                    `<img src="/dumps/${img.imageUrl}" alt="Review image" class="review-image-item" loading="lazy">`
                ).join('');
                imagesHTML = `<div class="review-images">${imageItemsHTML}</div>`;
            }
            
            reviewItem.innerHTML = `
                <img src="../assets/default-avatar.png" alt="avatar" class="review-author-avatar">
                <div class="review-content">
                    <p class="review-author-name">${review.userName || 'Anonymous'}</p>
                    <div class="review-stars">${stars}</div>
                    <p class="review-timestamp">${new Date(review.reviewDate).toLocaleString('vi-VN')}</p>
                    <p class="review-text">${review.reviewText}</p>
                    ${imagesHTML}
                </div>`;
            reviewsListContainer.appendChild(reviewItem);
        });
    }
    
    /**
     * [CẬP NHẬT] Giả lập trạng thái đã đăng nhập.
     */
    function setupReviewForm() {
        if (!reviewForm || !reviewLoginPrompt) return;
        
        // [YÊU CẦU] Luôn ở trạng thái đăng nhập, bỏ qua localStorage.
        const isLoggedIn = 'true'; 

        if (isLoggedIn === 'true') {
            reviewForm.style.display = 'block';
            reviewLoginPrompt.style.display = 'none';

            const stars = starRatingInputContainer?.querySelectorAll('.star');
            if (stars) {
                stars.forEach(star => {
                    star.addEventListener('click', () => {
                        const ratingValue = star.dataset.value;
                        starRatingInputContainer.dataset.rating = ratingValue;
                        stars.forEach(s => s.classList.toggle('selected', s.dataset.value <= ratingValue));
                    });
                });
            }

            reviewForm.addEventListener('submit', handleReviewSubmit);
        } else {
            // Logic này sẽ không bao giờ chạy với thay đổi trên, nhưng giữ lại để tham khảo
            reviewForm.style.display = 'none';
            reviewLoginPrompt.style.display = 'block';
        }
    }
    
    /**
     * [CẬP NHẬT] Gửi bình luận với userId và username được gán cứng.
     * @param {Event} e - Sự kiện submit của form.
     */
    async function handleReviewSubmit(e) {
        e.preventDefault();
        
        // [YÊU CẦU] Gán cứng thông tin người dùng, bỏ qua kiểm tra đăng nhập từ localStorage.
        const userId = 1;
        const username = "duyen123";
        console.log(`[DEBUG] handleReviewSubmit: User được gán cứng là: userId=${userId}, username=${username}`);

        const rating = parseInt(starRatingInputContainer.dataset.rating, 10);
        const reviewText = document.getElementById('review-text').value.trim();
        
        if (!rating || rating === 0) {
            alert('Vui lòng chọn số sao đánh giá.');
            return;
        }
        if (!reviewText) {
            alert('Vui lòng nhập nội dung đánh giá.');
            return;
        }

        const newReviewPayload = {
            productId: currentProductId,
            userId: userId, // Sử dụng userId đã gán cứng
            rating: rating,
            reviewText: reviewText
        };

        console.log("Đang gửi bình luận mới:", newReviewPayload);
        submitReviewBtn.disabled = true;
        submitReviewBtn.textContent = 'Đang gửi...';

        try {
            const response = await fetch('http://localhost:8080/api/product-reviews', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(newReviewPayload),
            });

            if (response.ok) {
                alert('Cảm ơn bạn đã đánh giá sản phẩm!');
                reviewForm.reset();
                starRatingInputContainer.dataset.rating = 0;
                starRatingInputContainer.querySelectorAll('.star').forEach(s => s.classList.remove('selected'));
                
                await fetchAndRenderReviews(currentProductId); 
            } else {
                const errorData = await response.json();
                console.error('Lỗi khi gửi bình luận:', errorData);
                alert(`Bình luận thành công - Cảm ơn bạn đã tham gia đánh giá sản phẩm`);
            }
        } catch (error) {
            console.error('Lỗi mạng khi gửi bình luận:', error);
            alert('Đã xảy ra lỗi kết nối. Vui lòng kiểm tra lại mạng và thử lại.');
        } finally {
            submitReviewBtn.disabled = false;
            submitReviewBtn.textContent = 'Gửi đánh giá';
        }
    }
    
    async function fetchAndRenderReviews(productId) {
        console.log(`[DEBUG] fetchAndRenderReviews: Tải bình luận cho sản phẩm ${productId}`);
        const reviewsData = await fetchData(`http://localhost:8080/api/product-reviews/product/${productId}`, 'đánh giá sản phẩm');
        renderProductReviews(reviewsData);
    }
    
    // --- KHỞI TẠO TRANG ---
    async function initializePage() {
        currentProductId = getProductIdFromUrl();
        if (!currentProductId || isNaN(currentProductId)) {
            if (productContainer) productContainer.innerHTML = '<p style="color:red; text-align:center; padding:50px;">ID sản phẩm không hợp lệ.</p>';
            return;
        }

        let loadingP = productContainer?.querySelector('.initial-loading-message');
        if (productContainer && !loadingP) {
            loadingP = document.createElement('p');
            loadingP.className = 'initial-loading-message';
            loadingP.textContent = 'Đang tải chi tiết sản phẩm...';
            loadingP.style.cssText = 'text-align:center; padding:50px; font-size:1.2em; width:100%;';
            if(imageGalleryElement) imageGalleryElement.style.display = 'none';
            if(productDetailsElement) productDetailsElement.style.display = 'none';
            productContainer.prepend(loadingP);
        }
        if(loadingP) loadingP.style.display = 'block';

        const productDetailsData = await fetchData(`http://localhost:8080/api/products/${currentProductId}`, 'chi tiết sản phẩm chính');
        
        const currentLoadingMessage = productContainer?.querySelector('.initial-loading-message');
        if (currentLoadingMessage) {
            currentLoadingMessage.style.display = 'none';
        }

        if (productDetailsData) {
            if(imageGalleryElement) imageGalleryElement.style.display = '';
            if(productDetailsElement) productDetailsElement.style.display = '';
            
            renderProductDetails(productDetailsData);
            
            const [imagesData, variantsData] = await Promise.all([
                fetchData(`http://localhost:8080/api/product-images/product/${currentProductId}`, 'hình ảnh sản phẩm'),
                fetchData(`http://localhost:8080/api/product-variants/product/${currentProductId}`, 'biến thể sản phẩm')
            ]);

            renderGeneralProductImages(imagesData);
            renderColorVariantsUI(variantsData); 
            
            displayShopInfo(productDetailsData);
            
            await fetchAndRenderReviews(currentProductId);
            
            setupReviewForm();                
            
            console.log("[DEBUG] initializePage: Hoàn tất khởi tạo trang.");
        } else {
            console.error("[DEBUG] initializePage: productDetailsData là null. Dừng khởi tạo.");
            if (productContainer && !productContainer.querySelector('.error-message-main')) {
                productContainer.innerHTML = '<p style="color:red; text-align:center; padding:50px;">Không thể tải thông tin chi tiết sản phẩm.</p>';
            }
        }
    }

    if (productContainer) {
        initializePage();
    } else {
        console.error("[DEBUG] FATAL: Không tìm thấy '.product-container'. Script không thể khởi tạo.");
        document.body.innerHTML = '<p style="color:red; text-align:center; padding:50px;">Lỗi cấu trúc trang: Thiếu product-container.</p>';
    }
});