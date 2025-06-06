// [Thư mục gốc]/js/flashsale.js

document.addEventListener('DOMContentLoaded', () => {
    const swiperWrapper = document.querySelector('.flash-sale-products-swiper .swiper-wrapper');
    const apiUrl = 'http://localhost:8080/api/products'; // Đảm bảo API này trả về dữ liệu phù hợp cho Flash Sale

    if (!swiperWrapper) {
        console.error("Lỗi flashsale.js: Không tìm thấy phần tử '.flash-sale-products-swiper .swiper-wrapper' trong DOM.");
        return; // Thoát sớm nếu không có container cho slide
    }

    // Hiển thị thông báo "Đang tải..." ban đầu
    swiperWrapper.innerHTML = '<div style="text-align:center; padding: 20px; width:100%;">Đang tải sản phẩm Flash Sale...</div>';

    fetch(apiUrl)
        .then(response => {
            if (!response.ok) {
                // Nếu server trả về lỗi, ném ra một Error để được bắt ở khối .catch()
                throw new Error(`HTTP error! Status: ${response.status} - ${response.statusText}`);
            }
            return response.json(); // Chuyển đổi phản hồi sang JSON
        })
        .then(apiResponse => { // apiResponse là toàn bộ đối tượng JSON từ server
            const productList = apiResponse.content; // Lấy mảng sản phẩm từ thuộc tính 'content'

            // Xóa nội dung "Đang tải..." hoặc "Không có sản phẩm" cũ trước khi thêm slide mới
            swiperWrapper.innerHTML = '';

            if (Array.isArray(productList) && productList.length > 0) {
                productList.forEach(product => {
                    // Lấy giá và tính toán khuyến mãi (nếu có)
                    // Đảm bảo tên thuộc tính khớp với dữ liệu JSON (camelCase)
                    const discountedPrice = product.productMinPrice;
                    const originalPrice = product.productMaxPrice;
                    let discountPercent = 0;
                    if (originalPrice > discountedPrice) {
                        discountPercent = Math.round(((originalPrice - discountedPrice) / originalPrice) * 100);
                    }

                    // Lấy số lượng đã bán
                    const soldCount = product.productSold || 0;
                    const maxSalesForBar = 10000; // Đây là giá trị giả định, bạn có thể cần điều chỉnh
                    const soldPercentage = Math.min(100, (soldCount / maxSalesForBar) * 100);

                    // Xử lý đường dẫn hình ảnh: thêm tiền tố "../dumps/"
                    const originalImageUrl = product.productMainImageUrl;
                    const correctedImageUrl = `/dumps/${originalImageUrl}`; // Thêm tiền tố

                    // Tạo HTML cho mỗi slide sản phẩm
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

                // Khởi tạo Swiper SAU KHI tất cả các slide đã được thêm vào DOM
                const swiperElement = document.querySelector('.flash-sale-products-swiper');
                if (swiperElement) {
                    try {
                        const flashSaleSwiper = new Swiper(swiperElement, {
                            slidesPerView: 'auto', // Hiển thị số lượng slide tự động dựa trên kích thước item và container
                            spaceBetween: 15,      // Khoảng cách giữa các slide (điều chỉnh nếu cần)
                            // scrollbar: { // Bỏ scrollbar nếu không có phần tử HTML tương ứng hoặc không muốn dùng
                            //     el: '.swiper-scrollbar',
                            //     hide: false,
                            // },
                            navigation: {
                                nextEl: '.swiper-button-next', // Selector của nút Next
                                prevEl: '.swiper-button-prev', // Selector của nút Previous
                            },
                            breakpoints: { // Cấu hình responsive
                                640: {
                                    slidesPerView: 2,
                                    spaceBetween: 15,
                                },
                                768: {
                                    slidesPerView: 3,
                                    spaceBetween: 20,
                                },
                                1024: {
                                    slidesPerView: 5, // Hoặc 4, 6 tùy theo thiết kế của bạn
                                    spaceBetween: 20,
                                }
                            }
                        });
                        console.log("flashsale.js: Swiper for Flash Sale initialized.");
                    } catch (e) {
                        console.error("flashsale.js: Lỗi khi khởi tạo Swiper cho Flash Sale:", e);
                        // Có thể thư viện Swiper chưa được tải đúng cách
                        // Hoặc cấu hình Swiper có vấn đề
                    }
                } else {
                    console.error("flashsale.js: Không tìm thấy phần tử '.flash-sale-products-swiper' để khởi tạo Swiper.");
                }

            } else {
                // Nếu không có sản phẩm nào trong productList
                swiperWrapper.innerHTML = '<div style="text-align:center; padding: 20px; width:100%;">Không có sản phẩm Flash Sale nào.</div>';
                console.log("flashsale.js: Không có sản phẩm nào trong danh sách Flash Sale từ API.");
            }
        })
        .catch(error => {
            // Xử lý lỗi nếu fetch API thất bại
            console.error('flashsale.js: Có lỗi khi lấy dữ liệu Flash Sale từ API:', error);
            if (swiperWrapper) { // Kiểm tra lại swiperWrapper trước khi gán innerHTML
                swiperWrapper.innerHTML = `<div style="text-align:center; padding: 20px; color: red; width:100%;">Lỗi tải sản phẩm Flash Sale. (${error.message})</div>`;
            }
        });

    // Code xử lý Timer cho Flash Sale (nếu có, bạn sẽ thêm vào đây)
    // Ví dụ:
    // function updateFlashSaleTimer() { ... }
    // setInterval(updateFlashSaleTimer, 1000);
    // updateFlashSaleTimer(); // Gọi lần đầu
});