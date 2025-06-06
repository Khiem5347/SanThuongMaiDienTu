// js/flashsale_list.js
document.addEventListener('DOMContentLoaded', () => {
    const productGrid = document.getElementById('product-grid');
    const paginationControls = document.getElementById('pagination-controls');
    const loadingText = document.querySelector('.loading-text');

    const API_BASE_URL = 'http://localhost:8080/api/products';
    const PRODUCTS_PER_PAGE = 10; // Số sản phẩm mỗi trang, bạn có thể điều chỉnh
    let currentPage = 0; // Trang hiện tại, bắt đầu từ 0 cho API

    async function fetchProducts(page = 0) {
        if (loadingText) loadingText.style.display = 'block'; // Hiển thị "Đang tải..."
        if (productGrid) productGrid.innerHTML = ''; // Xóa sản phẩm cũ trước khi tải trang mới (trừ khi đang append)
        
        const apiUrlWithPage = `${API_BASE_URL}?page=${page}&size=${PRODUCTS_PER_PAGE}`;
        console.log(`Fetching products from: ${apiUrlWithPage}`);

        try {
            const response = await fetch(apiUrlWithPage);
            if (!response.ok) {
                throw new Error(`Lỗi HTTP! Status: ${response.status}`);
            }
            const apiResponse = await response.json();
            
            if (loadingText) loadingText.style.display = 'none'; // Ẩn "Đang tải..."
            renderProducts(apiResponse.content);
            renderPagination(apiResponse);

        } catch (error) {
            console.error('Lỗi khi tải danh sách sản phẩm Flash Sale:', error);
            if (loadingText) loadingText.style.display = 'none';
            if (productGrid) productGrid.innerHTML = `<p class="error-text">Không thể tải sản phẩm. Vui lòng thử lại sau. (${error.message})</p>`;
        }
    }

    function renderProducts(products) {
        if (!productGrid) {
            console.error("Phần tử product-grid không tìm thấy!");
            return;
        }
        // productGrid.innerHTML = ''; // Đã xóa ở fetchProducts

        if (!products || products.length === 0) {
            productGrid.innerHTML = '<p class="loading-text">Không có sản phẩm nào.</p>';
            return;
        }

        products.forEach(product => {
            const discountedPrice = product.productMinPrice;
            const originalPrice = product.productMaxPrice;
            let discountPercent = 0;
            if (originalPrice > discountedPrice) {
                discountPercent = Math.round(((originalPrice - discountedPrice) / originalPrice) * 100);
            }
            const soldCount = product.productSold || 0;
            const maxSalesForBar = 10000; // Giả định
            const soldPercentage = Math.min(100, (soldCount / maxSalesForBar) * 100);

            // Sửa đường dẫn ảnh
            const originalImageUrl = product.productMainImageUrl;
            const correctedImageUrl = `/dumps/${originalImageUrl}`;

            const productCardHtml = `
                <div class="flash-sale-product-item-list">
                    <a href="../pages/chitietsanpham.html?id=${product.productId}" class="product-link">
                        <div class="product-image-wrapper">
                            <img src="${correctedImageUrl}" alt="${product.productName}" class="product-image">
                        </div>
                        <div class="product-info">
                            ${product.shopName ? `<p class="product-location">${product.shopName}</p>` : ''}
                            <p class="product-name">${product.productName}</p>
                            <div class="product-prices">
                                <span class="discounted-price">${discountedPrice.toLocaleString('vi-VN')}đ</span>
                                ${originalPrice > discountedPrice ? `<span class="original-price">${originalPrice.toLocaleString('vi-VN')}đ</span>` : ''}
                            </div>
                            ${discountPercent > 0 ? `
                            <div class="product-discount-info">
                                <span class="discount-percent">-${discountPercent}%</span>
                            </div>` : ''}
                            <div class="sold-bar-container">
                                <div class="sold-bar-progress" style="width: ${soldPercentage}%;"></div>
                                <span class="sold-text">Đã bán ${soldCount.toLocaleString('vi-VN')}</span>
                            </div>
                        </div>
                    </a>
                </div>
            `;
            productGrid.insertAdjacentHTML('beforeend', productCardHtml);
        });
    }

    function renderPagination(apiResponse) {
        if (!paginationControls) {
            console.error("Phần tử pagination-controls không tìm thấy!");
            return;
        }
        paginationControls.innerHTML = ''; // Xóa các nút cũ

        const totalPages = apiResponse.totalPages;
        currentPage = apiResponse.number; // API trả về page number (0-indexed)

        // Nút "Trang Trước"
        const prevButton = document.createElement('button');
        prevButton.textContent = 'Trước';
        prevButton.disabled = apiResponse.first; // Vô hiệu hóa nếu là trang đầu
        prevButton.addEventListener('click', () => fetchProducts(currentPage - 1));
        paginationControls.appendChild(prevButton);

        // Hiển thị thông tin trang hiện tại / tổng số trang (ví dụ)
        // Logic hiển thị các nút số trang có thể phức tạp hơn nếu có nhiều trang
        // Ví dụ đơn giản: hiển thị vài trang xung quanh trang hiện tại
        const maxPageButtons = 5; // Số nút trang tối đa hiển thị
        let startPage = Math.max(0, currentPage - Math.floor(maxPageButtons / 2));
        let endPage = Math.min(totalPages - 1, startPage + maxPageButtons - 1);
        
        if (totalPages > maxPageButtons && endPage - startPage + 1 < maxPageButtons) {
             startPage = Math.max(0, endPage - maxPageButtons + 1);
        }


        if (startPage > 0) {
            const firstPageButton = document.createElement('button');
            firstPageButton.textContent = '1';
            firstPageButton.addEventListener('click', () => fetchProducts(0));
            paginationControls.appendChild(firstPageButton);
            if (startPage > 1) {
                 const ellipsisStart = document.createElement('span');
                 ellipsisStart.textContent = '...';
                 paginationControls.appendChild(ellipsisStart);
            }
        }
        
        for (let i = startPage; i <= endPage; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i + 1; // Hiển thị số trang (1-indexed)
            if (i === currentPage) {
                pageButton.classList.add('current-page');
                pageButton.disabled = true;
            }
            pageButton.addEventListener('click', () => fetchProducts(i));
            paginationControls.appendChild(pageButton);
        }

        if (endPage < totalPages - 1) {
            if (endPage < totalPages - 2) {
                const ellipsisEnd = document.createElement('span');
                ellipsisEnd.textContent = '...';
                paginationControls.appendChild(ellipsisEnd);
            }
            const lastPageButton = document.createElement('button');
            lastPageButton.textContent = totalPages;
            lastPageButton.addEventListener('click', () => fetchProducts(totalPages - 1));
            paginationControls.appendChild(lastPageButton);
        }


        // Nút "Trang Sau"
        const nextButton = document.createElement('button');
        nextButton.textContent = 'Sau';
        nextButton.disabled = apiResponse.last; // Vô hiệu hóa nếu là trang cuối
        nextButton.addEventListener('click', () => fetchProducts(currentPage + 1));
        paginationControls.appendChild(nextButton);
    }

    // Lấy dữ liệu cho trang đầu tiên khi trang được tải
    fetchProducts(currentPage);
});


