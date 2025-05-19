document.addEventListener('DOMContentLoaded', () => {
    const swiperWrapper = document.querySelector('.flash-sale-products-swiper .swiper-wrapper');
    const apiUrl = 'http://localhost:3000/api/products';
    if (swiperWrapper) {
        swiperWrapper.innerHTML = '';
    } else {
        console.error("Element .flash-sale-products-swiper .swiper-wrapper not found.");
        return;
    }
    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            return response.json();
        })
        .then(products => {
            if (Array.isArray(products) && products.length > 0) {
                products.forEach(product => {
                    const discountedPrice = product.product_min_price;
                    const originalPrice = product.product_max_price;
                    let discountPercent = 0;
                    if (originalPrice > discountedPrice) {
                         discountPercent = Math.round(((originalPrice - discountedPrice) / originalPrice) * 100);
                    }
                    const soldCount = product.product_sold || 0;
                    const maxSalesForBar = 10000; // Giả định
                    const soldPercentage = Math.min(100, (soldCount / maxSalesForBar) * 100);
                    const slideHtml = `
                        <div class="swiper-slide flash-sale-product-item">
                            <a href="../pages/chitietsanpham.html?id=${product.product_id}" class="product-link">
                                <div class="product-image-wrapper">
                                    <img src="${product.product_main_image_url}" alt="${product.product_name}" class="product-image">
                                </div>
                                <div class="product-info">
                                    ${product.product_location ? `<p class="product-location">${product.product_location}</p>` : ''}
                                    <p class="product-name">${product.product_name}</p>
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
                    swiperWrapper.insertAdjacentHTML('beforeend', slideHtml);
                });
                const swiperElement = document.querySelector('.flash-sale-products-swiper');
                if (swiperElement) {
                     const flashSaleSwiper = new Swiper(swiperElement, {
                        slidesPerView: 'auto',
                        spaceBetween: 10,
                        scrollbar: {
                           el: '.swiper-scrollbar',
                           hide: false,
                        },
                        // Thêm cấu hình navigation
                        navigation: {
                            nextEl: '.swiper-button-next', // Selector của nút Next
                            prevEl: '.swiper-button-prev', // Selector của nút Previous
                        },
                        // Thêm responsive breakpoints nếu cần
                        breakpoints: {
                            640: {
                                slidesPerView: 2,
                                spaceBetween: 20,
                            },
                            768: {
                                slidesPerView: 3,
                                spaceBetween: 30,
                            },
                            1024: {
                                slidesPerView: 5,
                                spaceBetween: 40,
                            },
                        }
                    });
                } else {
                    console.error("Element .flash-sale-products-swiper not found for Swiper initialization.");
                }
            } else {
                swiperWrapper.innerHTML = '<div style="text-align:center; padding: 20px;">Không có sản phẩm Flash Sale nào.</div>';
            }
        })
        .catch(error => {
            console.error('Có lỗi khi lấy dữ liệu Flash Sale:', error);
             if (swiperWrapper) {
                 swiperWrapper.innerHTML = '<div style="text-align:center; padding: 20px; color: red;">Lỗi tải sản phẩm Flash Sale.</div>';
            }
        });
    // Code xử lý Timer ( tạm chưa có)
});