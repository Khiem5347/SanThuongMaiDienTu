// Chuyển động banner
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

async function loadHTML(id, filePath, callback) { // Thêm tham số callback
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
            // Gọi callback sau một khoảng trễ nhỏ (0ms) để trình duyệt có thời gian render DOM mới
            setTimeout(callback, 0);
        }
    } catch (error) {
        console.error(`js/index.js: Lỗi trong khi loadHTML cho id='${id}', file='${filePath}':`, error);
    }
}

// Gọi khi trang tải xong
window.addEventListener("DOMContentLoaded", () => {
    console.log("js/index.js: DOMContentLoaded - Bắt đầu tải header và footer.");

    loadHTML("header", "../components/header.html", function() {
        // Callback này sẽ được gọi sau khi header.html được chèn
        console.log("js/index.js: Callback sau khi tải header.html - Kiểm tra và gọi initHeaderUI.");
        if (typeof initHeaderUI === 'function') {
            initHeaderUI(); // Gọi hàm khởi tạo UI của header
        } else {
            console.error("js/index.js: Hàm initHeaderUI không được định nghĩa! Kiểm tra file js/header.js.");
        }
    });

    loadHTML("footer", "../components/footer.html"); // Giả sử footer không cần callback đặc biệt
});



