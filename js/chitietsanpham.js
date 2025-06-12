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
            // [FIXED LOGIC] Trường hợp không có biến thể màu -> sử dụng dữ liệu gốc.
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

        // Cập nhật ảnh chính
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

        // Render các tùy chọn kích thước nếu có
        const hasSizes = variant.productSizes && variant.productSizes.length > 0;
        if (hasSizes) {
            renderSizeOptionsUI(variant.productSizes);
            // Reset hiển thị cho đến khi size được chọn
            if (stockAvailableElement) stockAvailableElement.textContent = 'Vui lòng chọn kích thước';
            updateDisplayedPriceRange(); // Hiển thị khoảng giá của màu này
            updateQuantityControls(0); // Vô hiệu hóa nút số lượng
        } else {
            // [FIXED LOGIC] Sản phẩm "chỉ có màu", không có size.
            // Cập nhật giá và tồn kho ngay lập tức dựa trên thông tin của màu và sản phẩm gốc.
            console.log("[DEBUG] handleColorSelection: Biến thể màu này không có size. Cập nhật dựa trên sản phẩm chính.");
            sizeOptionsContainer.innerHTML = '<p>Màu này không có tùy chọn kích thước.</p>';
            updatePriceAndStock(variant); // Gửi object variant để có thể lấy giá (nếu cần)
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
            // Tự động chọn size đầu tiên
            if(sizeOptionsContainer.children[0]) {
                sizeOptionsContainer.children[0].click();
            }
        } else {
            // Trường hợp này đã được xử lý ở handleColorSelection, nhưng để phòng hờ.
            sizeOptionsContainer.innerHTML = '<p>Màu này không có tùy chọn kích thước hoặc đã hết hàng.</p>';
            updatePriceAndStock(selectedColorVariant); // Cập nhật dựa trên màu đã chọn
        }
    }
    
    function handleSizeSelection(sizeData) {
        selectedProductSize = sizeData;
        console.log('[DEBUG] handleSizeSelection: Đã chọn size:', selectedProductSize);
        updatePriceAndStock(selectedProductSize);
    }

    // [FIXED LOGIC] TÁI CẤU TRÚC LOGIC LẤY TỒN KHO
    function updatePriceAndStock(selection) {
        console.log("[DEBUG] updatePriceAndStock: Bắt đầu cập nhật với lựa chọn:", selection);
        let displayPrice = 0;
        let availableStock = 0;
        let stockText = 'Sản phẩm tạm thời hết hàng';

        if (selection && selection.sizeId) {
            // TRƯỜNG HỢP 1: Đã chọn size cụ thể (có cả màu và size).
            // Lấy tồn kho từ chính `productSize` đó.
            console.log("[DEBUG] updatePriceAndStock: Xử lý theo 'Kích thước cụ thể'");
            displayPrice = selection.price;
            availableStock = selection.quantity || 0;
        } else {
            // TRƯỜNG HỢP 2 & 3:
            // - Chỉ chọn màu (sản phẩm có màu, không có size). `selection` là object `variant`.
            // - Không có biến thể nào. `selection` là `null`.
            // -> Trong cả hai trường hợp này, tồn kho sẽ được lấy từ sản phẩm chính.
            console.log("[DEBUG] updatePriceAndStock: Xử lý theo 'Sản phẩm chính (Fallback)'");
            if (mainProductData) {
                // Sử dụng trường `remainingQuantity` từ API product chính.
                availableStock = mainProductData.remainingQuantity || 0;
            }

            // Nếu là trường hợp chỉ có màu, giá có thể được định nghĩa trong `variant`.
            if (selection && selection.variantId && selection.price) {
                displayPrice = selection.price;
            } else if (mainProductData) {
                // Nếu không, hiển thị khoảng giá chung.
                updateDisplayedPriceRange();
            } else {
                priceElement.textContent = formatCurrency(0);
            }
        }

        // Cập nhật giá (chỉ khi có giá cụ thể)
        if (displayPrice > 0) {
             if (priceElement) priceElement.textContent = formatCurrency(displayPrice);
        }

        // Cập nhật tồn kho
        if (stockAvailableElement) {
            stockText = `${availableStock} sản phẩm có sẵn`;
            stockAvailableElement.textContent = stockText;
        }
        
        // Cập nhật ô nhập số lượng và các nút +/-
        currentQuantity = (availableStock > 0) ? 1 : 0;
        if (quantityInputElement) quantityInputElement.value = currentQuantity;
        updateQuantityControls(availableStock);
    }
    
    function updateDisplayedPriceRange() {
        if (!priceElement) return;

        console.log("[DEBUG] updateDisplayedPriceRange: Cập nhật khoảng giá hiển thị.");
        if (selectedColorVariant && selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0) {
            // Hiển thị khoảng giá của các size trong màu đã chọn
            const pricesInVariant = selectedColorVariant.productSizes.map(s => s.price);
            const min = Math.min(...pricesInVariant);
            const max = Math.max(...pricesInVariant);
            priceElement.textContent = (min === max) ? formatCurrency(min) : `${formatCurrency(min)} - ${formatCurrency(max)}`;
        } else if (mainProductData) {
            // Fallback về giá chung của sản phẩm
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
        
        maxStock = maxStock || 0; // Đảm bảo maxStock là số
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
    
    // [FIXED LOGIC] Hàm xác định stock tối đa để kiểm tra
    function getAvailableStock() {
        if (selectedProductSize) {
            // Trường hợp 1: Đã chọn size, dùng stock của size đó.
            return selectedProductSize.quantity || 0;
        }
        // Trường hợp 2 & 3: Chỉ chọn màu hoặc không có biến thể, dùng stock của sản phẩm chính.
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

    // [LOGIC KHÔNG THAY ĐỔI] Logic này đã linh hoạt nên không cần sửa
    const handleAddToCartOrBuyNow = (isBuyNow = false) => {
        console.log(`[DEBUG] handleAddToCartOrBuyNow (isBuyNow: ${isBuyNow}):`, { selectedColorVariant, selectedProductSize, currentQuantity });

        // --- BƯỚC 1: XÁC ĐỊNH LỰA CHỌN CUỐI CÙNG VÀ TỒN KHO ---
        let finalSelection = null;
        let stockToCheck = 0;
        let selectionText = 'Mặc định';
        let finalPrice = mainProductData?.productMinPrice || 0;
        let finalImageUrl = mainImageElement ? mainImageElement.src : '';

        if (colorVariants.length > 0) {
            // Sản phẩm có biến thể
            if (!selectedColorVariant) {
                alert('Vui lòng chọn màu sắc.');
                return;
            }
            
            const hasSizes = selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0;
            if (hasSizes) { // TH1: Có màu và size
                if (!selectedProductSize) {
                    alert('Vui lòng chọn kích thước.');
                    return;
                }
                finalSelection = selectedProductSize;
                stockToCheck = finalSelection.quantity || 0;
                selectionText = `${selectedColorVariant.color} - ${finalSelection.size}`;
                finalPrice = finalSelection.price;
            } else { // TH2: Chỉ có màu
                finalSelection = selectedColorVariant;
                stockToCheck = mainProductData?.remainingQuantity || 0; // Sửa ở đây
                selectionText = finalSelection.color;
                finalPrice = finalSelection.price || mainProductData.productMinPrice;
            }
            if (selectedColorVariant.imageUrl) {
                 finalImageUrl = `/dumps/${selectedColorVariant.imageUrl}`;
            }
        } else {
            // TH3 & TH4: Không có biến thể
            finalSelection = mainProductData;
            stockToCheck = finalSelection?.remainingQuantity || 0; // Sửa ở đây
        }

        // --- BƯỚC 2: KIỂM TRA SỐ LƯỢNG ---
        if (currentQuantity <= 0) {
            alert('Vui lòng chọn số lượng lớn hơn 0. Sản phẩm có thể đã hết hàng.');
            return;
        }
        if (currentQuantity > stockToCheck) {
            alert(`Số lượng tồn kho không đủ. Chỉ còn ${stockToCheck} sản phẩm.`);
            return;
        }

        // --- BƯỚC 3: TẠO ĐỐI TƯỢNG SẢN PHẨM ---
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

        // --- BƯỚC 4: XỬ LÝ LOGIC (THÊM GIỎ HÀNG / MUA NGAY) ---
        if (isBuyNow) {
            const orderDataForSession = {
                orderId: 'BUYNOW_' + Date.now(),
                orderDate: new Date().toISOString().split('T')[0],
                paymentMethod: 'Thanh toán khi nhận hàng',
                shippingFee: 30000, // Giả định
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

    // --- CÁC HÀM CHO SHOP VÀ ĐÁNH GIÁ (KHÔNG THAY ĐỔI) ---

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

    function loadReviewsFromLocalStorage(productId) {
        if (!productId) return [];
        const reviewsKey = `reviews_for_product_${productId}`;
        const reviewsString = localStorage.getItem(reviewsKey);
        try {
            return reviewsString ? JSON.parse(reviewsString) : [];
        } catch (e) {
            console.error("Lỗi đọc reviews từ localStorage:", e);
            return [];
        }
    }

    function saveReviewsToLocalStorage(productId, reviews) {
        if (!productId || !reviews) return;
        const reviewsKey = `reviews_for_product_${productId}`;
        localStorage.setItem(reviewsKey, JSON.stringify(reviews));
    }

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
            reviewItem.innerHTML = `
                <img src="../assets/default-avatar.png" alt="avatar" class="review-author-avatar">
                <div class="review-content">
                    <p class="review-author-name">${review.username || 'Anonymous'}</p>
                    <div class="review-stars">${stars}</div>
                    <p class="review-timestamp">${new Date(review.createdAt).toLocaleString('vi-VN')}</p>
                    <p class="review-text">${review.comment}</p>
                </div>`;
            reviewsListContainer.appendChild(reviewItem);
        });
    }
    
    function setupReviewForm() {
        if (!reviewForm || !reviewLoginPrompt) return;
        const isLoggedIn = localStorage.getItem('isLoggedIn');

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
            reviewForm.style.display = 'none';
            reviewLoginPrompt.style.display = 'block';
        }
    }
    
    function handleReviewSubmit(e) {
        e.preventDefault();
        
        const isLoggedIn = localStorage.getItem('isLoggedIn');
        if (isLoggedIn !== 'true') {
            alert('Bạn cần đăng nhập để đánh giá.');
            return;
        }
        
        const rating = parseInt(starRatingInputContainer.dataset.rating, 10);
        const comment = document.getElementById('review-text').value.trim();
        
        if (!rating || rating === 0) {
            alert('Vui lòng chọn số sao đánh giá.');
            return;
        }
        if (!comment) {
            alert('Vui lòng nhập nội dung đánh giá.');
            return;
        }

        const username = localStorage.getItem('loggedInUser') || 'Người dùng ẩn danh';

        const newReview = {
            username: username,
            rating: rating,
            comment: comment,
            createdAt: new Date().toISOString()
        };

        const existingReviews = loadReviewsFromLocalStorage(currentProductId);
        existingReviews.push(newReview);
        saveReviewsToLocalStorage(currentProductId, existingReviews);

        alert('Cảm ơn bạn đã đánh giá sản phẩm!');
        reviewForm.reset();
        starRatingInputContainer.dataset.rating = 0;
        starRatingInputContainer.querySelectorAll('.star').forEach(s => s.classList.remove('selected'));

        renderProductReviews(existingReviews);
    }
    
    // --- KHỞI TẠO TRANG ---
    async function initializePage() {
        currentProductId = getProductIdFromUrl();
        if (!currentProductId || isNaN(currentProductId)) {
            if (productContainer) productContainer.innerHTML = '<p style="color:red; text-align:center; padding:50px;">ID sản phẩm không hợp lệ.</p>';
            return;
        }

        // Hiển thị loading
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
        
        // Ẩn loading
        const currentLoadingMessage = productContainer?.querySelector('.initial-loading-message');
        if (currentLoadingMessage) {
            currentLoadingMessage.style.display = 'none';
        }

        if (productDetailsData) {
            // Hiển thị lại content
            if(imageGalleryElement) imageGalleryElement.style.display = '';
            if(productDetailsElement) productDetailsElement.style.display = '';
            
            renderProductDetails(productDetailsData);
            
            const [imagesData, variantsData, reviewsData] = await Promise.all([
                fetchData(`http://localhost:8080/api/product-images/product/${currentProductId}`, 'hình ảnh sản phẩm'),
                fetchData(`http://localhost:8080/api/product-variants/product/${currentProductId}`, 'biến thể sản phẩm'),
                fetchData(`http://localhost:8080/api/product-reviews/product/${currentProductId}`, 'đánh giá sản phẩm')
            ]);

            renderGeneralProductImages(imagesData);
            renderColorVariantsUI(variantsData); // Hàm này sẽ tự xử lý các trường hợp khác nhau
            
            // Render các phần phụ
            displayShopInfo(productDetailsData);
            renderProductReviews(reviewsData);   
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