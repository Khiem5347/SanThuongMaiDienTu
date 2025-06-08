// js/danhmuc.js
document.addEventListener('DOMContentLoaded', () => {
    const API_BASE_URL = 'http://localhost:8080/api';

    // DOM Elements
    const categoryTitleElement = document.getElementById('category-title');
    const productsGridContainer = document.getElementById('category-products-grid');
    const paginationContainer = document.getElementById('pagination-container');

    let currentCategoryIds = []; // Sẽ là một mảng các ID

    // --- HÀM KHỞI TẠO CHÍNH ---
    async function initializeCategoryPage() {
        const urlParams = new URLSearchParams(window.location.search);
        
        // Đọc tham số 'ids' mới thay vì 'id'
        const idsString = urlParams.get('ids'); 

        if (!idsString) {
            displayError("Không có danh mục nào được chọn.");
            if (categoryTitleElement) categoryTitleElement.textContent = "Lỗi";
            return;
        }

        // Tách chuỗi thành mảng các ID
        currentCategoryIds = idsString.split(',').map(id => parseInt(id.trim()));
        
        if (currentCategoryIds.length === 0 || currentCategoryIds.some(isNaN)) {
             displayError("ID danh mục không hợp lệ.");
             return;
        }

        // Cập nhật tiêu đề (có thể lấy tên từ danh mục đầu tiên hoặc đặt tên chung)
        // Cách tốt hơn là API mới nên trả về cả tên chung cho nhóm danh mục này
        if (categoryTitleElement) {
            categoryTitleElement.textContent = "Sản phẩm theo danh mục đã chọn";
        }
        
        // Tải sản phẩm cho trang đầu tiên bằng API mới
        await fetchAndRenderProducts(currentCategoryIds, 0);
    }

    // --- HÀM TẢI DỮ LIỆU ---

    // Tải sản phẩm theo MẢNG danh mục và trang
    async function fetchAndRenderProducts(categoryIds, page = 0, size = 10) {
        if (!productsGridContainer) return;
        productsGridContainer.innerHTML = '<p class="loading-text">Đang tải sản phẩm...</p>';

        // Chuyển mảng ID thành chuỗi để đưa vào URL API
        const idsQueryParam = categoryIds.join(',');

        try {
            // Gọi đến API MỚI mà bạn đã tạo ở backend
            const response = await fetch(`${API_BASE_URL}/products/by-categories?ids=${idsQueryParam}&page=${page}&size=${size}`);
            
            if (!response.ok) {
                throw new Error('Không thể tải sản phẩm từ server.');
            }
            const data = await response.json();
            
            renderProducts(data.content);
            renderPagination(data.totalPages, data.number, categoryIds);

        } catch (error) {
            console.error('Lỗi khi tải sản phẩm theo danh mục:', error);
            displayError(error.message);
        }
    }

    // --- HÀM RENDER --- (Hàm renderProducts và formatCurrency giữ nguyên)

    function renderProducts(products) {
        if (!productsGridContainer) return;
        productsGridContainer.innerHTML = '';
        if (!products || products.length === 0) {
            productsGridContainer.innerHTML = '<p class="empty-text">Không có sản phẩm nào trong các danh mục này.</p>';
            return;
        }
        products.forEach(product => {
            const productCard = document.createElement('div');
            productCard.className = 'product-card';
            const priceText = product.productMinPrice === product.productMaxPrice
                ? formatCurrency(product.productMinPrice)
                : `${formatCurrency(product.productMinPrice)} - ${formatCurrency(product.productMaxPrice)}`;
            const imageUrl = product.productMainImageUrl 
                ? `/dumps/${product.productMainImageUrl}` 
                : '../assets/placeholder-main.jpg';
            productCard.innerHTML = `
                <a href="../pages/chitietsanpham.html?id=${product.productId}">
                    <img src="${imageUrl}" alt="${product.productName}">
                    <div class="product-info">
                        <h3 class="product-name">${product.productName}</h3>
                        <div class="product-price">${priceText}</div>
                        <div class="product-sold">Đã bán: ${product.productSold || 0}</div>
                    </div>
                </a>
            `;
            productsGridContainer.appendChild(productCard);
        });
    }
    
    // Cập nhật hàm renderPagination để truyền mảng categoryIds
    function renderPagination(totalPages, currentPage, categoryIds) {
        if (!paginationContainer) return;
        paginationContainer.innerHTML = '';
        if (totalPages <= 1) return;

        const prevButton = document.createElement('button');
        prevButton.className = 'pagination-btn';
        prevButton.textContent = 'Trước';
        prevButton.disabled = currentPage === 0;
        prevButton.addEventListener('click', () => {
            fetchAndRenderProducts(categoryIds, currentPage - 1);
        });
        paginationContainer.appendChild(prevButton);

        for (let i = 0; i < totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.className = 'pagination-btn';
            pageButton.textContent = i + 1;
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            pageButton.addEventListener('click', () => {
                fetchAndRenderProducts(categoryIds, i);
            });
            paginationContainer.appendChild(pageButton);
        }

        const nextButton = document.createElement('button');
        nextButton.className = 'pagination-btn';
        nextButton.textContent = 'Sau';
        nextButton.disabled = currentPage >= totalPages - 1;
        nextButton.addEventListener('click', () => {
            fetchAndRenderProducts(categoryIds, currentPage + 1);
        });
        paginationContainer.appendChild(nextButton);
    }
    
    function displayError(message) {
        if (productsGridContainer) {
            productsGridContainer.innerHTML = `<p class="error-text">${message}</p>`;
        }
    }
    
    const formatCurrency = (amount) => {
        if (typeof amount !== 'number') amount = parseFloat(amount);
        if (isNaN(amount)) return 'N/A';
        return amount.toLocaleString('vi-VN', { style: 'currency', currency: 'VND' });
    };

    // --- BẮT ĐẦU CHẠY ---
    initializeCategoryPage();
});