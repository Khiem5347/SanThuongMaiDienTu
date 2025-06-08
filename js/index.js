// Chuyển động banner
// Thêm kiểm tra để đảm bảo phần tử .swiper tồn tại trước khi khởi tạo
if (document.querySelector('.swiper')) {
    new Swiper('.swiper', {
        loop: true,
        autoplay: {
            delay: 3000, // Tự động chạy sau mỗi 3 giây
            disableOnInteraction: false, // Tiếp tục tự chạy sau khi người dùng tương tác
        },
        navigation: {
            nextEl: '.swiper-button-next', // Selector của nút Next
            prevEl: '.swiper-button-prev', // Selector của nút Prev
        },
        pagination: {
            el: '.swiper-pagination',
            clickable: true, // Cho phép click vào dấu chấm để chuyển slide
        },
    });
}


/**
 * Tải nội dung HTML từ một tệp và chèn vào một phần tử trên trang.
 * @param {string} id - ID của phần tử đích.
 * @param {string} filePath - Đường dẫn tới tệp HTML.
 * @param {function} [callback] - Một hàm tùy chọn để thực thi sau khi HTML được chèn thành công.
 */
async function loadHTML(id, filePath, callback) {
    console.log(`js/index.js: Bắt đầu loadHTML cho id='${id}', file='${filePath}'`);
    const targetElement = document.getElementById(id);

    if (!targetElement) {
        console.error(`js/index.js: Lỗi loadHTML - Không tìm thấy phần tử với id="${id}".`);
        return;
    }

    try {
        const res = await fetch(filePath);
        if (!res.ok) {
            console.error(`js/index.js: Lỗi fetch file '${filePath}': ${res.status} ${res.statusText}`);
            return;
        }
        const htmlText = await res.text();
        targetElement.innerHTML = htmlText;
        console.log(`js/index.js: Đã chèn thành công HTML từ '${filePath}' vào id='${id}'.`);

        // Nếu có callback và callback là một hàm, thì gọi nó
        if (callback && typeof callback === 'function') {
            // Gọi callback sau một khoảng trễ nhỏ (0ms) để đảm bảo DOM đã được cập nhật.
            setTimeout(callback, 0);
        }
    } catch (error) {
        console.error(`js/index.js: Lỗi trong khi loadHTML cho id='${id}', file='${filePath}':`, error);
    }
}

// Gọi các hàm khởi tạo khi trang tải xong
window.addEventListener("DOMContentLoaded", () => {
    console.log("js/index.js: DOMContentLoaded - Bắt đầu tải header và footer.");

    // Tải header và sau đó khởi tạo các chức năng của nó
    loadHTML("header", "../components/header.html", function() {
        // Callback này sẽ được gọi sau khi header.html được chèn vào DOM.
        console.log("js/index.js: Callback sau khi tải header.html - Bắt đầu khởi tạo toàn bộ header.");

        // *** ĐÂY LÀ THAY ĐỔI QUAN TRỌNG ***
        // Gọi hàm khởi tạo tổng hợp từ header.js
        if (typeof initializeHeader === 'function') {
            initializeHeader(); // Hàm này sẽ gọi cả initHeaderUI() và initSearch()
        } else {
            console.error("js/index.js: Lỗi! Hàm initializeHeader() không được định nghĩa. Hãy chắc chắn rằng bạn đã cập nhật file js/header.js theo hướng dẫn mới nhất.");
        }
    });

    // Tải footer (không cần callback đặc biệt)
    loadHTML("footer", "../components/footer.html");
});
