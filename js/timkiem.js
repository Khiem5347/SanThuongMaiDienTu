document.addEventListener('DOMContentLoaded', () => {
    const API_BASE_URL = 'http://localhost:8080';

    const searchTitle = document.getElementById('search-title');
    const productResultsContainer = document.getElementById('product-results');
    const relatedProductsContainer = document.getElementById('related-products');
    const noResultsMessage = '<p>Không tìm thấy sản phẩm nào.</p>';
    const noRelatedMessage = '<p>Không có sản phẩm liên quan.</p>';

    const urlParams = new URLSearchParams(window.location.search);
    const query = urlParams.get('q');

    const createProductCard = (product) => {
        const imageUrl = product.productMainImageUrl 
            ? `/dumps/${product.productMainImageUrl}` 
            : 'https://placehold.co/600x400/eee/ccc?text=No+Image';

        const productName = product.productName;
        const displayPrice = typeof product.productMinPrice === 'number' 
            ? `${product.productMinPrice.toLocaleString('vi-VN')} ₫` 
            : 'Liên hệ';
        const productId = product.productId;

        return `
            <div class="product-card">
                <a href="/pages/chitietsanpham.html?id=${productId}">
                    <img src="${imageUrl}" alt="${productName}" class="product-image" onerror="this.onerror=null;this.src='https://placehold.co/600x400/eee/ccc?text=Error';">
                    <div class="product-info">
                        <h3 class="product-name">${productName}</h3>
                        <p class="product-price">${displayPrice}</p>
                    </div>
                </a>
            </div>
        `;
    };

    const displayProducts = (container, products, message = noResultsMessage) => {
        if (products && products.length > 0) {
            container.innerHTML = products.map(createProductCard).join('');
        } else {
            container.innerHTML = message;
        }
    };

    /**
     * HÀM MỚI: Lấy các sản phẩm liên quan dựa trên cùng một CATEGORY.
     * @param {number} categoryId - ID của danh mục cần tìm.
     * @param {number[]} excludeProductIds - Mảng các ID sản phẩm cần loại bỏ (để không trùng lặp).
     */
    const fetchRelatedCategoryProducts = async (categoryId, excludeProductIds) => {
        try {
            // Gọi API để lấy các sản phẩm trong cùng danh mục
            const response = await fetch(`${API_BASE_URL}/api/products/category/${categoryId}`);
            if (!response.ok) throw new Error('Failed to fetch category products');
            
            const data = await response.json();
            
            // API có thể trả về mảng trực tiếp hoặc đối tượng có thuộc tính 'content'
            const allCategoryProducts = data.content || data;

            // Lọc ra những sản phẩm không có trong kết quả tìm kiếm chính
            const relatedProducts = allCategoryProducts.filter(p => !excludeProductIds.includes(p.productId));
            
            displayProducts(relatedProductsContainer, relatedProducts, noRelatedMessage);

        } catch (error) {
            console.error('Error fetching related category products:', error);
            relatedProductsContainer.innerHTML = '<p>Lỗi khi tải sản phẩm liên quan.</p>';
        }
    };

    /**
     * Hàm chính để tìm kiếm và khởi chạy các hàm liên quan.
     */
    const fetchSearchResults = async (searchQuery) => {
        if (!searchQuery) {
            searchTitle.textContent = 'Vui lòng nhập từ khóa để tìm kiếm';
            productResultsContainer.innerHTML = '';
            relatedProductsContainer.innerHTML = '';
            return;
        }

        searchTitle.textContent = `Kết quả tìm kiếm cho: "${searchQuery}"`;

        try {
            const response = await fetch(`${API_BASE_URL}/api/products/search?name=${encodeURIComponent(searchQuery)}`);
            if (!response.ok) throw new Error('Network response was not ok');
            
            const data = await response.json();
            const products = data.content;
            
            // 1. Hiển thị kết quả tìm kiếm chính
            displayProducts(productResultsContainer, products);

            // 2. Nếu có kết quả, khởi chạy việc tìm sản phẩm liên quan theo danh mục
            if (products && products.length > 0) {
                // Lấy category ID từ sản phẩm đầu tiên
                const categoryId = products[0].categoryId;
                // Lấy danh sách ID của các sản phẩm vừa tìm được để loại trừ
                const productIdsToExclude = products.map(p => p.productId);

                if (categoryId) {
                    fetchRelatedCategoryProducts(categoryId, productIdsToExclude);
                } else {
                    // Nếu sản phẩm không có category, không hiển thị mục liên quan
                    relatedProductsContainer.innerHTML = noRelatedMessage;
                }
            } else {
                // Nếu không có kết quả tìm kiếm, cũng không hiển thị mục liên quan
                relatedProductsContainer.innerHTML = '';
            }

        } catch (error) {
            console.error('Error fetching search results:', error);
            productResultsContainer.innerHTML = '<p>Đã xảy ra lỗi khi tìm kiếm sản phẩm.</p>';
            relatedProductsContainer.innerHTML = ''; // Dọn dẹp mục liên quan nếu tìm kiếm chính bị lỗi
        }
    };

    // Chạy hàm tìm kiếm chính
    fetchSearchResults(query);
});