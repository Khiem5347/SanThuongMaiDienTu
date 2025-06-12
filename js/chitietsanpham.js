// [Thư mục gốc]/js/chitietsanpham.js
const API_BASE_URL = 'http://localhost:8080/api';
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
    console.log("[DEBUG] Các phần tử DOM ban đầu:", {
        productContainer, imageGalleryElement, productDetailsElement, mainImageElement, thumbnailsContainer,
        productTitleElement, ratingSpan, ratingValueSpan, reviewsSpan, soldSpan, priceElement,
        colorOptionsContainer, sizeOptionsContainer, quantityInputElement, stockAvailableElement,
        minusQtyButton, plusQtyButton, addToCartButton, buyNowButton
    });

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
        else console.warn("[DEBUG] renderProductDetails: productTitleElement không tìm thấy.");
        
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
        } else console.warn("[DEBUG] renderProductDetails: ratingValueSpan hoặc ratingSpan không tìm thấy.");

        if (reviewsSpan) reviewsSpan.textContent = `${data.productReview || 0} Đánh Giá`;
        else console.warn("[DEBUG] renderProductDetails: reviewsSpan không tìm thấy.");

        if (soldSpan) soldSpan.textContent = `${data.productSold || 0} Đã Bán`;
        else console.warn("[DEBUG] renderProductDetails: soldSpan không tìm thấy.");

        updateDisplayedPriceRange();
        console.log("[DEBUG] renderProductDetails: Hoàn tất render chi tiết sản phẩm chính.");
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
            console.log("[DEBUG] renderGeneralProductImages: Đặt ảnh chính ban đầu:", initialMainImageUrl);

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
                    console.log("[DEBUG] renderGeneralProductImages: Thumbnail clicked, đổi ảnh chính thành:", thumbUrl);
                });
                thumbnailsContainer.appendChild(thumb);
            });
        } else {
            mainImageElement.src = '../assets/default-avatar.png';
            mainImageElement.alt = 'Không có ảnh sản phẩm';
            console.log("[DEBUG] renderGeneralProductImages: Không có ảnh, hiển thị ảnh mặc định.");
        }
        console.log("[DEBUG] renderGeneralProductImages: Hoàn tất render ảnh.");
    }
    
    function renderColorVariantsUI(variants) {
         console.log("[DEBUG] renderColorVariantsUI: Bắt đầu render biến thể màu:", variants);
        colorVariants = variants || [];
        if (!colorOptionsContainer) {
            console.warn("[DEBUG] renderColorVariantsUI: Thiếu colorOptionsContainer.");
            if (sizeOptionsContainer) sizeOptionsContainer.innerHTML = '<p class="select-color-first-text">Lỗi: Không thể tải tùy chọn màu.</p>';
            updatePriceAndStock(null);
            return;
        }
        colorOptionsContainer.innerHTML = ''; // Xóa placeholder "Đang tải tùy chọn màu..."

        if (colorVariants.length > 0) {
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
            
            console.log("[DEBUG] renderColorVariantsUI: Tự động chọn màu đầu tiên.");
            if(colorOptionsContainer.children[0]) {
                colorOptionsContainer.children[0].click();
            } else { // Không có màu nào được render, xử lý trạng thái size
                 if (sizeOptionsContainer) sizeOptionsContainer.innerHTML = '<p class="select-color-first-text">Sản phẩm không có tùy chọn màu.</p>';
            }
            
        } else {
            colorOptionsContainer.innerHTML = '<p>Sản phẩm này không có tùy chọn màu.</p>';
            if (sizeOptionsContainer) sizeOptionsContainer.innerHTML = '<p>Không có tùy chọn kích thước.</p>';
            updatePriceAndStock(null);
            console.log("[DEBUG] renderColorVariantsUI: Không có biến thể màu.");
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
            console.log("[DEBUG] handleColorSelection: Cập nhật ảnh chính theo màu:", variantImageUrl);
            
            document.querySelectorAll('.thumbnails .thumbnail').forEach(t => t.classList.remove('active'));
            if(thumbnailsContainer) {
                const activeThumb = Array.from(thumbnailsContainer.children).find(thumb => thumb.src.includes(variant.imageUrl));
                if (activeThumb) activeThumb.classList.add('active');
            }
        } else if (mainImageElement && generalProductImages.length > 0 && generalProductImages[0].productUrl) {
             mainImageElement.src = `/dumps/${generalProductImages[0].productUrl}`;
             console.log("[DEBUG] handleColorSelection: Màu không có ảnh riêng, dùng ảnh chung đầu tiên.");
        } else if (mainImageElement && mainProductData && mainProductData.productMainImageUrl) {
             mainImageElement.src = `/dumps/${mainProductData.productMainImageUrl}`;
             console.log("[DEBUG] handleColorSelection: Màu không có ảnh riêng, dùng ảnh chính của sản phẩm.");
        }

        renderSizeOptionsUI(variant.productSizes || []);
        updateDisplayedPriceRange();
        if (stockAvailableElement) stockAvailableElement.textContent = 'Vui lòng chọn kích thước';
        updateQuantityControls(0); // Disable quantity until size is picked
    }

    function renderSizeOptionsUI(sizes) {
          console.log("[DEBUG] renderSizeOptionsUI: Bắt đầu render kích thước cho màu đã chọn:", sizes);
        if (!sizeOptionsContainer) {
             console.warn("[DEBUG] renderSizeOptionsUI: Thiếu sizeOptionsContainer.");
             return;
        }
        sizeOptionsContainer.innerHTML = ''; // Xóa placeholder "Vui lòng chọn màu sắc..."

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
            console.log("[DEBUG] renderSizeOptionsUI: Tự động chọn size đầu tiên.");
            if(sizeOptionsContainer.children[0]) {
                sizeOptionsContainer.children[0].click();
            }
        } else {
            sizeOptionsContainer.innerHTML = '<p>Màu này không có tùy chọn kích thước hoặc đã hết hàng.</p>';
            updatePriceAndStock(null); // Không có size, giá và tồn kho sẽ dựa vào màu hoặc sản phẩm chính
            console.log("[DEBUG] renderSizeOptionsUI: Không có kích thước cho màu này.");
        }
        console.log("[DEBUG] renderSizeOptionsUI: Hoàn tất render kích thước.");
    }
    
    function handleSizeSelection(sizeData) {
        selectedProductSize = sizeData;
        console.log('[DEBUG] handleSizeSelection: Đã chọn size:', selectedProductSize);
        updatePriceAndStock(selectedProductSize);
    }

    function updatePriceAndStock(sizeData) {
          console.log("[DEBUG] updatePriceAndStock: Cập nhật giá và tồn kho cho sizeData:", sizeData);
        if (sizeData) { // Nếu một size cụ thể được chọn
            if (priceElement) priceElement.textContent = formatCurrency(sizeData.price);
            else console.warn("[DEBUG] updatePriceAndStock: priceElement không tìm thấy.");
            
            if (stockAvailableElement) stockAvailableElement.textContent = `${sizeData.quantity || 0} sản phẩm có sẵn`;
            else console.warn("[DEBUG] updatePriceAndStock: stockAvailableElement không tìm thấy.");
            
            if(quantityInputElement) quantityInputElement.value = (sizeData.quantity > 0) ? 1 : 0;
            currentQuantity = (sizeData.quantity > 0) ? 1 : 0;
            updateQuantityControls(sizeData.quantity || 0);
        } else { // Nếu không có size cụ thể (ví dụ: chỉ mới chọn màu, hoặc không có biến thể nào)
            updateDisplayedPriceRange(); // Hiển thị khoảng giá của màu (nếu đã chọn màu) hoặc sản phẩm chính
            
            let stockText = 'Vui lòng chọn phân loại';
            let availableStock = 0;
            if (selectedColorVariant && selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0) {
                stockText = 'Vui lòng chọn kích thước'; // Đã chọn màu, chờ chọn size
            } else if (mainProductData && mainProductData.productStock !== undefined) { // Sử dụng productStock từ sản phẩm chính nếu không có biến thể
                stockText = `${mainProductData.productStock || 0} sản phẩm có sẵn`;
                availableStock = mainProductData.productStock || 0;
            }

            if (stockAvailableElement) stockAvailableElement.textContent = stockText;
            else console.warn("[DEBUG] updatePriceAndStock: stockAvailableElement không tìm thấy khi sizeData null.");

            if(quantityInputElement) quantityInputElement.value = 0;
            currentQuantity = 0;
            updateQuantityControls(availableStock);
        }
    }

    function updateDisplayedPriceRange() {
         if (!priceElement) {
            console.warn("[DEBUG] updateDisplayedPriceRange: priceElement không tìm thấy.");
            return;
        }
        console.log("[DEBUG] updateDisplayedPriceRange: Cập nhật khoảng giá hiển thị.");
        if (selectedColorVariant && selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0) {
            const pricesInVariant = selectedColorVariant.productSizes.map(s => s.price);
            const min = Math.min(...pricesInVariant);
            const max = Math.max(...pricesInVariant);
            priceElement.textContent = (min === max) ? formatCurrency(min) : `${formatCurrency(min)} - ${formatCurrency(max)}`;
        } else if (mainProductData) { // Fallback về giá chung của sản phẩm
            priceElement.textContent = (mainProductData.productMinPrice === mainProductData.productMaxPrice || (!mainProductData.productMaxPrice && mainProductData.productMinPrice)) ?
                formatCurrency(mainProductData.productMinPrice || 0) :
                `${formatCurrency(mainProductData.productMinPrice || 0)} - ${formatCurrency(mainProductData.productMaxPrice || 0)}`;
        } else {
            priceElement.textContent = formatCurrency(0);
        }
    }

    function updateQuantityControls(maxStock) {
         if (!quantityInputElement || !minusQtyButton || !plusQtyButton) {
            return;
        }
        const currentVal = parseInt(quantityInputElement.value) || 0; // Mặc định là 0 nếu không parse được
        minusQtyButton.disabled = currentVal <= 1 || maxStock === 0;
        plusQtyButton.disabled = currentVal >= maxStock || maxStock === 0;
        if (maxStock === 0 && quantityInputElement) { // Nếu hết hàng, đặt số lượng về 0
             quantityInputElement.value = 0;
             currentQuantity = 0; // Cập nhật biến currentQuantity
        } else if (currentVal === 0 && maxStock > 0 && quantityInputElement) { // Nếu đang là 0 mà có hàng, có thể đặt là 1
            // quantityInputElement.value = 1; // Hoặc để người dùng tự tăng
            // currentQuantity = 1;
        }
    }

    if (minusQtyButton && quantityInputElement) {
        minusQtyButton.addEventListener('click', () => {
            let val = parseInt(quantityInputElement.value);
            if (val > 1) {
                val--;
                quantityInputElement.value = val;
                currentQuantity = val;
                updateQuantityControls(selectedProductSize ? selectedProductSize.quantity : (mainProductData?.productStock || 0));
            }
        });
    }

    if (plusQtyButton && quantityInputElement) {
        plusQtyButton.addEventListener('click', () => {
            let val = parseInt(quantityInputElement.value);
            const stock = selectedProductSize ? selectedProductSize.quantity : (mainProductData?.productStock || 0);
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
            const stock = selectedProductSize ? selectedProductSize.quantity : (mainProductData?.productStock || 0);
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

    if (addToCartButton) {
        addToCartButton.addEventListener('click', () => {
            console.log("[DEBUG] addToCartButton: Clicked. selectedColorVariant:", selectedColorVariant, "selectedProductSize:", selectedProductSize, "currentQuantity:", currentQuantity);
            if (colorVariants.length > 0 && !selectedColorVariant) { alert('Vui lòng chọn màu sắc.'); return; }
            if (selectedColorVariant && selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0 && !selectedProductSize) { alert('Vui lòng chọn kích thước.'); return; }
            
            const stockToCheck = selectedProductSize ? selectedProductSize.quantity : (mainProductData?.productStock || 0);
            if (currentQuantity <= 0) { alert('Vui lòng chọn số lượng lớn hơn 0.'); return; }
            if (currentQuantity > stockToCheck) { alert(`Số lượng tồn kho không đủ. Chỉ còn ${stockToCheck} sản phẩm.`); return; }

            const itemToAdd = {
                productId: currentProductId,
                productName: mainProductData?.productName || 'N/A',
                variantId: selectedColorVariant?.variantId, // Có thể null nếu không có biến thể màu
                sizeId: selectedProductSize?.sizeId,       // Có thể null nếu không có biến thể size
                variation: {
                    option: selectedProductSize ? `${selectedColorVariant?.color || ''} - ${selectedProductSize.size}` : (selectedColorVariant ? selectedColorVariant.color : 'Mặc định'),
                    price: selectedProductSize ? selectedProductSize.price : mainProductData?.productMinPrice,
                    imageUrl: selectedColorVariant?.imageUrl ? `/dumps/${selectedColorVariant.imageUrl}` : (mainImageElement ? mainImageElement.src : '')
                },
                quantity: currentQuantity
            };
            
            console.log('Thêm vào giỏ hàng:', itemToAdd);
            let cartItems = JSON.parse(localStorage.getItem('cartItems')) || [];
            const existingItemIndex = cartItems.findIndex(item =>
                item.productId === itemToAdd.productId &&
                item.variantId === itemToAdd.variantId && // So sánh cả variantId (màu)
                item.sizeId === itemToAdd.sizeId       // Và sizeId (kích thước)
            );

            if (existingItemIndex > -1) {
                cartItems[existingItemIndex].quantity += itemToAdd.quantity;
                if (cartItems[existingItemIndex].quantity > stockToCheck) {
                    cartItems[existingItemIndex].quantity = stockToCheck;
                    alert(`Đã cập nhật số lượng trong giỏ. Số lượng tối đa cho sản phẩm này là ${stockToCheck}.`);
                }
            } else {
                cartItems.push(itemToAdd);
            }
            localStorage.setItem('cartItems', JSON.stringify(cartItems));
            alert(`Đã thêm "${itemToAdd.productName} (${itemToAdd.variation.option})" vào giỏ hàng!`);
            window.location.href = 'giohang.html';
        });
    }

    if (buyNowButton) {
        buyNowButton.addEventListener('click', () => {
            console.log("[DEBUG] buyNowButton: Clicked. selectedColorVariant:", selectedColorVariant, "selectedProductSize:", selectedProductSize, "currentQuantity:", currentQuantity);
            if (colorVariants.length > 0 && !selectedColorVariant) { alert('Vui lòng chọn màu sắc.'); return; }
            if (selectedColorVariant && selectedColorVariant.productSizes && selectedColorVariant.productSizes.length > 0 && !selectedProductSize) { alert('Vui lòng chọn kích thước.'); return; }

            const stockToCheck = selectedProductSize ? selectedProductSize.quantity : (mainProductData?.productStock || 0);
            if (currentQuantity <= 0) { alert('Vui lòng chọn số lượng lớn hơn 0.'); return; }
            if (currentQuantity > stockToCheck) { alert(`Số lượng tồn kho không đủ. Chỉ còn ${stockToCheck} sản phẩm.`); return; }

            const itemToBuy = {
                productId: currentProductId,
                productName: mainProductData?.productName || 'N/A',
                variantId: selectedColorVariant?.variantId,
                sizeId: selectedProductSize?.sizeId,
                variation: {
                    option: selectedProductSize ? `${selectedColorVariant?.color || ''} - ${selectedProductSize.size}` : (selectedColorVariant ? selectedColorVariant.color : 'Mặc định'),
                    price: selectedProductSize ? selectedProductSize.price : mainProductData?.productMinPrice,
                    imageUrl: selectedColorVariant?.imageUrl ? `/dumps/${selectedColorVariant.imageUrl}` : (mainImageElement ? mainImageElement.src : '')
                },
                quantity: currentQuantity
            };
            const orderDataForSession = {
                orderId: 'BUYNOW_' + Date.now(),
                orderDate: new Date().toISOString().split('T')[0],
                paymentMethod: 'Thanh toán khi nhận hàng',
                shippingFee: 30000, // Giả định
                items: [itemToBuy],
                itemsTotalAmount: itemToBuy.variation.price * itemToBuy.quantity
            };
            sessionStorage.setItem('currentOrderDetails', JSON.stringify(orderDataForSession));
            console.log("Lưu chi tiết đơn hàng (Mua Ngay) vào sessionStorage:", orderDataForSession);
            window.location.href = 'xacnhanmuahang.html';
        });
    }


    // --- PHẦN MỚI: CÁC HÀM CHO SHOP VÀ ĐÁNH GIÁ ---

    /**
     * Hiển thị thông tin cửa hàng dựa trên dữ liệu sản phẩm.
     * @param {object} product - Dữ liệu chi tiết của sản phẩm.
     */
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
     * Render danh sách các đánh giá sản phẩm.
     * @param {Array} reviews - Mảng các đối tượng đánh giá.
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
            reviewItem.innerHTML = `
                <img src="../assets/default-avatar.png" alt="avatar" class="review-author-avatar">
                <div class="review-content">
                    <p class="review-author-name">${review.username || 'Anonymous'}</p>
                    <div class="review-stars">${stars}</div>
                    <p class="review-timestamp">${new Date(review.createdAt || Date.now()).toLocaleDateString('vi-VN')}</p>
                    <p class="review-text">${review.reviewText || ''}</p>
                </div>`;
            reviewsListContainer.appendChild(reviewItem);
        });
    }

    /**
     * Cài đặt form gửi đánh giá: hiển thị/ẩn và xử lý sự kiện.
     */
    function setupReviewForm() {
         if (!reviewForm || !reviewLoginPrompt) return;
        const token = localStorage.getItem('token');

        if (token) {
            reviewForm.style.display = 'block';
            reviewLoginPrompt.style.display = 'none';

            const stars = starRatingInputContainer?.querySelectorAll('.star');
            if (!stars) return;
            stars.forEach(star => {
                star.addEventListener('click', () => {
                    const ratingValue = star.dataset.value;
                    starRatingInputContainer.dataset.rating = ratingValue;
                    stars.forEach(s => s.classList.toggle('selected', s.dataset.value <= ratingValue));
                });
            });

            reviewForm.addEventListener('submit', handleReviewSubmit);
        } else {
            reviewForm.style.display = 'none';
            reviewLoginPrompt.style.display = 'block';
        }
    }

    /**
     * Xử lý sự kiện khi người dùng gửi form đánh giá.
     */
    async function handleReviewSubmit(e) {
         e.preventDefault();
        const token = localStorage.getItem('token');
        const userString = localStorage.getItem('user');
        if (!token || !userString) {
            alert('Bạn cần đăng nhập để đánh giá.');
            return;
        }

        submitReviewBtn.disabled = true;
        submitReviewBtn.textContent = 'Đang gửi...';

        const rating = parseInt(starRatingInputContainer.dataset.rating, 10);
        const comment = document.getElementById('review-text').value;
        const user = JSON.parse(userString);

        if (rating === 0) {
            alert('Vui lòng chọn số sao đánh giá.');
            submitReviewBtn.disabled = false;
            submitReviewBtn.textContent = 'Gửi Đánh Giá';
            return;
        }

        try {
            const response = await fetch(`${API_BASE_URL}/product-reviews`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${token}` },
                body: JSON.stringify({
                    productId: currentProductId,
                    userId: user.userId,
                    rating: rating,
                    reviewText: comment
                })
            });
            if (!response.ok) {
                const errData = await response.json().catch(() => ({ message: 'Không thể gửi đánh giá.' }));
                throw new Error(errData.message);
            }
            alert('Cảm ơn bạn đã đánh giá sản phẩm!');
            reviewForm.reset();
            starRatingInputContainer.dataset.rating = 0;
            starRatingInputContainer.querySelectorAll('.star').forEach(s => s.classList.remove('selected'));
            
            // Tải lại danh sách đánh giá
            const newReviewsData = await fetchData(`${API_BASE_URL}/product-reviews/product/${currentProductId}`);
            renderProductReviews(newReviewsData);

        } catch (error) {
            alert(`Đã xảy ra lỗi: ${error.message}`);
        } finally {
            submitReviewBtn.disabled = false;
            submitReviewBtn.textContent = 'Gửi Đánh Giá';
        }
    }
    // --- KẾT THÚC PHẦN MỚI: CÁC HÀM CHO SHOP VÀ ĐÁNH GIÁ ---

    async function initializePage() {
        currentProductId = getProductIdFromUrl();
        if (!currentProductId || isNaN(currentProductId)) {
            if (productContainer) productContainer.innerHTML = '<p style="color:red; text-align:center; padding:50px;">ID sản phẩm không hợp lệ.</p>';
            console.error("[DEBUG] initializePage: ID sản phẩm không hợp lệ:", currentProductId);
            return;
        }
        console.log("[DEBUG] initializePage: Product ID hợp lệ:", currentProductId);

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
        console.log("[DEBUG] initializePage: Hiển thị 'Đang tải chi tiết sản phẩm...'");

        const productDetailsData = await fetchData(`http://localhost:8080/api/products/${currentProductId}`, 'chi tiết sản phẩm chính');
        console.log("[DEBUG] initializePage: Kết quả fetch productDetailsData:", productDetailsData);
        
        const currentLoadingMessage = productContainer?.querySelector('.initial-loading-message');
        if (currentLoadingMessage) {
            currentLoadingMessage.style.display = 'none';
            console.log("[DEBUG] initializePage: Đã ẩn 'Đang tải...'");
        }

        if (productDetailsData) {
            console.log("[DEBUG] initializePage: productDetailsData có dữ liệu. Hiển thị lại content và render...");
            if(imageGalleryElement) imageGalleryElement.style.display = '';
            if(productDetailsElement) productDetailsElement.style.display = '';
            renderProductDetails(productDetailsData);
             // Tải song song các dữ liệu phụ
            const [imagesData, variantsData, reviewsData] = await Promise.all([
                fetchData(`http://localhost:8080/api/product-images/product/${currentProductId}`, 'hình ảnh sản phẩm'),
                fetchData(`http://localhost:8080/api/product-variants/product/${currentProductId}`, 'biến thể sản phẩm'),
                fetchData(`http://localhost:8080/api/product-reviews/product/${currentProductId}`, 'đánh giá sản phẩm') // Thêm fetch reviews
            ]);
            console.log("[DEBUG] initializePage: Kết quả fetchProductImages:", imagesData);
            console.log("[DEBUG] initializePage: Kết quả fetchProductVariants:", variantsData);

            renderGeneralProductImages(imagesData);
            renderColorVariantsUI(variantsData); // Hàm này sẽ gọi renderSizeOptionsUI và chọn biến thể/size đầu tiên
             // **GỌI CÁC HÀM MỚI Ở ĐÂY**
            displayShopInfo(productDetailsData); // Dùng data từ sản phẩm chính
            renderProductReviews(reviewsData);   // Dùng data từ API reviews
            setupReviewForm();                   // Cài đặt form review
            console.log("[DEBUG] initializePage: Hoàn tất render.");
        } else {
            console.error("[DEBUG] initializePage: productDetailsData là null. Dừng khởi tạo.");
            if (productContainer && !productContainer.querySelector('.error-message-main')) {
                 // Nếu fetchData đã hiển thị lỗi rồi thì thôi, nếu chưa thì hiển thị ở đây
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