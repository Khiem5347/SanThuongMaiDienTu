// [Thư mục gốc]/js/flashsale.js

document.addEventListener('DOMContentLoaded', () => {
    // --- PHẦN LẤY DỮ LIỆU SẢN PHẨM (GIỮ NGUYÊN) ---
    const swiperWrapper = document.querySelector('.flash-sale-products-swiper .swiper-wrapper');
    const apiUrl = 'http://localhost:8080/api/products'; 

    if (!swiperWrapper) {
        console.error("Lỗi flashsale.js: Không tìm thấy phần tử '.flash-sale-products-swiper .swiper-wrapper' trong DOM.");
        return;
    }

    swiperWrapper.innerHTML = '<div style="text-align:center; padding: 20px; width:100%;">Đang tải sản phẩm Flash Sale...</div>';

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status} - ${response.statusText}`);
            }
            return response.json();
        })
        .then(apiResponse => {
            const productList = apiResponse.content;
            swiperWrapper.innerHTML = '';

            if (Array.isArray(productList) && productList.length > 0) {
                productList.forEach(product => {
                    const discountedPrice = product.productMinPrice;
                    const originalPrice = product.productMaxPrice;
                    let discountPercent = 0;
                    if (originalPrice > discountedPrice) {
                        discountPercent = Math.round(((originalPrice - discountedPrice) / originalPrice) * 100);
                    }
                    const soldCount = product.productSold || 0;
                    const maxSalesForBar = 10000;
                    const soldPercentage = Math.min(100, (soldCount / maxSalesForBar) * 100);
                    const originalImageUrl = product.productMainImageUrl;
                    const correctedImageUrl = `/dumps/${originalImageUrl}`;

                    const slideHtml = `
                        <div class="swiper-slide flash-sale-product-item">
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
                    swiperWrapper.insertAdjacentHTML('beforeend', slideHtml);
                });

                const swiperElement = document.querySelector('.flash-sale-products-swiper');
                if (swiperElement) {
                    try {
                        const flashSaleSwiper = new Swiper(swiperElement, {
                            slidesPerView: 'auto',
                            spaceBetween: 15,
                            navigation: {
                                nextEl: '.swiper-button-next',
                                prevEl: '.swiper-button-prev',
                            },
                            breakpoints: {
                                640: { slidesPerView: 2, spaceBetween: 15 },
                                768: { slidesPerView: 3, spaceBetween: 20 },
                                1024: { slidesPerView: 5, spaceBetween: 20 }
                            }
                        });
                        console.log("flashsale.js: Swiper for Flash Sale initialized.");
                    } catch (e) {
                        console.error("flashsale.js: Lỗi khi khởi tạo Swiper cho Flash Sale:", e);
                    }
                } else {
                    console.error("flashsale.js: Không tìm thấy phần tử '.flash-sale-products-swiper' để khởi tạo Swiper.");
                }

            } else {
                swiperWrapper.innerHTML = '<div style="text-align:center; padding: 20px; width:100%;">Không có sản phẩm Flash Sale nào.</div>';
                console.log("flashsale.js: Không có sản phẩm nào trong danh sách Flash Sale từ API.");
            }
        })
        .catch(error => {
            console.error('flashsale.js: Có lỗi khi lấy dữ liệu Flash Sale từ API:', error);
            if (swiperWrapper) {
                swiperWrapper.innerHTML = `<div style="text-align:center; padding: 20px; color: red; width:100%;">Lỗi tải sản phẩm Flash Sale. (${error.message})</div>`;
            }
        });
    /**
     * Khởi tạo và chạy đồng hồ đếm ngược cho Flash Sale.
     */
    function initializeFlashSaleTimer() {
        const timerContainer = document.querySelector('.flash-sale-timer');
        const hoursBox = document.querySelector('.countdown-box.hours');
        const minutesBox = document.querySelector('.countdown-box.minutes');
        const secondsBox = document.querySelector('.countdown-box.seconds');

        if (!timerContainer || !hoursBox || !minutesBox || !secondsBox) {
            console.warn("flashsale.js: Không tìm thấy các phần tử của đồng hồ đếm ngược.");
            return;
        }

        // --- BẠN CÓ THỂ THAY ĐỔI THỜI GIAN KẾT THÚC Ở ĐÂY ---
        // Ví dụ: kết thúc vào nửa đêm ngày mai.
        const tomorrow = new Date();
        tomorrow.setDate(tomorrow.getDate() + 1);
        tomorrow.setHours(0, 0, 0, 0);
        const flashSaleEndDate = tomorrow;
        // Hoặc một ngày cụ thể: const flashSaleEndDate = new Date('2025-06-10T00:00:00+07:00');

        console.log(`Flash Sale sẽ kết thúc vào: ${flashSaleEndDate}`);

        const updateTimer = () => {
            const now = new Date();
            const remainingTime = flashSaleEndDate - now;

            if (remainingTime <= 0) {
                // Khi hết giờ
                clearInterval(timerInterval); // Dừng đồng hồ
                timerContainer.innerHTML = '<span class="timer-label" style="color: red; font-weight: bold;">Flash Sale đã kết thúc!</span>';
                return;
            }

            // 1 giây = 1000ms. 1 phút = 60s. 1 giờ = 60p. 1 ngày = 24h.
            const totalSeconds = Math.floor(remainingTime / 1000);
            const totalMinutes = Math.floor(totalSeconds / 60);
            const totalHours = Math.floor(totalMinutes / 60);

            const displaySeconds = totalSeconds % 60;
            const displayMinutes = totalMinutes % 60;
            const displayHours = totalHours; // Hiển thị tổng số giờ còn lại

            // Hàm thêm số 0 đằng trước nếu số nhỏ hơn 10 (vd: 9 -> "09")
            const padZero = (num) => String(num).padStart(2, '0');

            hoursBox.textContent = padZero(displayHours);
            minutesBox.textContent = padZero(displayMinutes);
            secondsBox.textContent = padZero(displaySeconds);
        };

        // Gọi hàm updateTimer mỗi giây
        const timerInterval = setInterval(updateTimer, 1000);
        // Gọi ngay lần đầu tiên để hiển thị mà không phải chờ 1 giây
        updateTimer(); 
    }

    // Chạy hàm khởi tạo đồng hồ
    initializeFlashSaleTimer();
});