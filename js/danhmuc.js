// js/danhmuc.js
document.addEventListener('DOMContentLoaded', () => {
    const API_BASE_URL = 'http://localhost:8080/api';

    // DOM Elements
    const categoryTitleElement = document.getElementById('category-title');
    const productsGridContainer = document.getElementById('category-products-grid');
    const paginationContainer = document.getElementById('pagination-container');

    let currentCategoryIds = [];

    // --- HÀM KHỞI TẠO CHÍNH ---
    async function initializeCategoryPage() {
        const urlParams = new URLSearchParams(window.location.search);
        
        // SỬA Ở ĐÂY: Đọc tham số 'categoryIds' thay vì 'ids'
        const idsString = urlParams.get('categoryIds'); 

        if (!idsString) {
            displayError("Không có danh mục nào được chọn.");
            if (categoryTitleElement) categoryTitleElement.textContent = "Lỗi";
            return;
        }

        currentCategoryIds = idsString.split(',').map(id => parseInt(id.trim()));
        
        if (currentCategoryIds.length === 0 || currentCategoryIds.some(isNaN)) {
             displayError("ID danh mục không hợp lệ.");
             return;
        }

        // Cập nhật tiêu đề (lấy tên từ danh mục đầu tiên trong danh sách)
        await fetchAndSetCategoryTitle(currentCategoryIds[0]);
        
        // Tải sản phẩm cho trang đầu tiên
        await fetchAndRenderProducts(currentCategoryIds, 0);
    }

    // --- HÀM TẢI DỮ LIỆU ---

    // Tải chi tiết danh mục để lấy tên hiển thị
    async function fetchAndSetCategoryTitle(firstCategoryId) {
        // Bạn có thể tùy chỉnh logic này để hiển thị tên phù hợp hơn khi có nhiều ID
        try {
            const category = await (await fetch(`${API_BASE_URL}/categories/${firstCategoryId}`)).json();
            if (categoryTitleElement) {
                // Nếu chỉ có 1 ID thì hiển thị tên, nếu nhiều thì hiển thị tên chung
                categoryTitleElement.textContent = currentCategoryIds.length > 1 
                    ? `Danh sách sản phẩm`
                    : category.categoryName;
            }
        } catch (error) {
            console.error('Lỗi khi tải tên danh mục:', error);
            if (categoryTitleElement) {
                categoryTitleElement.textContent = 'Sản phẩm';
            }
        }
    }

   // Tải sản phẩm theo MẢNG danh mục và trang
async function fetchAndRenderProducts(categoryIds, page = 0) {
    // Đặt một giá trị size rất lớn để lấy "tất cả"
    const size = 1000; // Ví dụ: lấy tối đa 1000 sản phẩm

    if (!productsGridContainer) return;
    productsGridContainer.innerHTML = '<p class="loading-text">Đang tải sản phẩm...</p>';

    const idsQueryParam = categoryIds.join(',');

    try {
        const response = await fetch(`${API_BASE_URL}/products/category/by-categoryId?categoryIds=${idsQueryParam}&page=${page}&size=${size}`);
        
        if (!response.ok) {
            throw new Error('Không thể tải sản phẩm từ server.');
        }
        const data = await response.json();
        
        renderProducts(data.content);

        // Khi đã tải tất cả, chúng ta không cần hiển thị các nút phân trang nữa
        if (paginationContainer) {
            paginationContainer.innerHTML = '';
        }

    } catch (error) {
        console.error('Lỗi khi tải sản phẩm theo danh mục:', error);
        displayError(error.message);
    }
}

    // --- HÀM RENDER ---

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
    
    // Hàm renderPagination giữ nguyên
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