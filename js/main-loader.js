// js/main-loader.js

console.log("main-loader.js: File đã tải, sẵn sàng để tải header và footer.");

/**
 * Tải nội dung HTML từ một tệp và chèn vào một phần tử trên trang.
 * @param {string} id - ID của phần tử đích.
 * @param {string} filePath - Đường dẫn tới tệp HTML.
 * @param {function} [callback] - Một hàm tùy chọn để thực thi sau khi HTML được chèn thành công.
 */
async function loadHTML(id, filePath, callback) {
    const targetElement = document.getElementById(id);
    if (!targetElement) {
        // Không tìm thấy phần tử, không cần báo lỗi vì có thể trang không cần component này
        return;
    }

    try {
        const res = await fetch(filePath);
        if (!res.ok) {
            console.error(`Lỗi fetch file '${filePath}': ${res.status}`);
            targetElement.innerHTML = `<p style="color:red; text-align:center;">Lỗi tải component: ${id}</p>`;
            return;
        }
        const htmlText = await res.text();
        targetElement.innerHTML = htmlText;

        // Nếu có callback và callback là một hàm, thì gọi nó
        if (callback && typeof callback === 'function') {
            // Gọi callback để đảm bảo các script của component được khởi tạo
            callback();
        }
    } catch (error) {
        console.error(`Lỗi trong khi loadHTML cho id='${id}':`, error);
    }
}

// Hàm này sẽ tự động chạy khi DOM của bất kỳ trang nào đã sẵn sàng
window.addEventListener("DOMContentLoaded", () => {
    console.log("main-loader.js: DOMContentLoaded - Bắt đầu tải header và footer.");

    // Tải header và sau đó gọi callback để khởi tạo các chức năng của nó
    loadHTML("header", "../components/header.html", function() {
        // Callback này sẽ được gọi sau khi header.html được chèn vào DOM.
        // Nó sẽ tìm và gọi hàm initializeHeader() từ file header.js
        if (typeof initializeHeader === 'function') {
            initializeHeader(); // Hàm này sẽ gọi cả initHeaderUI() và initSearch()
        } else {
            console.error("Lỗi! Hàm initializeHeader() không tồn tại. Hãy chắc chắn file header.js đã được tải trước file này.");
        }
    });

    // Tải footer (không cần callback)
    loadHTML("footer", "../components/footer.html");
});