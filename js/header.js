// js/header.js
console.log("js/header.js: File đã tải. Chứa các hàm khởi tạo, sẵn sàng được gọi.");

/**
 * Khởi tạo giao diện người dùng cho header (đăng nhập, dropdown).
 */
function initHeaderUI() {
    console.log("js/header.js: Bắt đầu chạy hàm initHeaderUI().");

    const loginNavLi = document.getElementById('loginNav')?.parentElement;
    const headerUl = document.querySelector('header ul');

    if (!headerUl) {
        console.error("js/header.js: (Lỗi trong initHeaderUI) Không tìm thấy thẻ <ul> trong <header>.");
        return;
    }

    // --- LOGIC KIỂM TRA HYBRID (CẢ CŨ VÀ MỚI) ---
    // 1. Ưu tiên kiểm tra theo chuẩn mới
    const token = localStorage.getItem('token');
    const userString = localStorage.getItem('user');
    const user = userString ? JSON.parse(userString) : null;

    // 2. Nếu không có, kiểm tra theo chuẩn cũ để tương thích
    const isLoggedIn_old = localStorage.getItem('isLoggedIn');
    const username_old = localStorage.getItem('loggedInUser');

    // Xác định tên người dùng và trạng thái đăng nhập cuối cùng
    const finalUsername = user?.username || username_old;
    const finalIsLoggedIn = !!token || (isLoggedIn_old === 'true');

    // Xóa dropdown cũ nếu có để tránh trùng lặp
    const existingUserDropdown = headerUl.querySelector('.user-dropdown-li');
    if (existingUserDropdown) existingUserDropdown.remove();

    if (finalIsLoggedIn && finalUsername) {
        // --- HIỂN THỊ KHI ĐÃ ĐĂNG NHẬP ---
        if (loginNavLi) loginNavLi.style.display = 'none';

        const userDropdownLi = document.createElement('li');
        userDropdownLi.classList.add('user-dropdown-li');

        userDropdownLi.innerHTML = `
            <a href="#" class="user-dropdown-trigger">
                <i class="fas fa-user-circle"></i> ${finalUsername}
            </a>
            <div class="user-dropdown-content">
                <a href="../pages/taikhoan.html">Tài Khoản Của Tôi</a>
                <a href="#" id="logoutBtnInDropdown">Đăng Xuất</a>
            </div>
        `;
        
        userDropdownLi.addEventListener('mouseenter', () => userDropdownLi.querySelector('.user-dropdown-content').style.display = 'block');
        userDropdownLi.addEventListener('mouseleave', () => userDropdownLi.querySelector('.user-dropdown-content').style.display = 'none');

        headerUl.prepend(userDropdownLi);
        
        // Gắn sự kiện cho nút logout mới (xóa tất cả các key)
        document.getElementById('logoutBtnInDropdown').addEventListener('click', (e) => {
            e.preventDefault();
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('loggedInUser');
            alert('Bạn đã đăng xuất.');
            window.location.href = '../pages/index.html';
        });

    } else {
        // --- HIỂN THỊ KHI CHƯA ĐĂNG NHẬP ---
        if (loginNavLi) loginNavLi.style.display = '';
    }
    console.log("js/header.js: Hàm initHeaderUI() đã chạy xong.");
}

/**
 * Khởi tạo chức năng tìm kiếm đơn giản.
 */
function initSearch() {
    // ... (Hàm này giữ nguyên, không cần thay đổi) ...
    console.log("js/header.js: Bắt đầu chạy hàm initSearch().");
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');
    if (!searchInput || !searchButton) { return; }
    const performSearch = () => {
        const query = searchInput.value.trim();
        if (query) {
            window.location.href = `../pages/timkiem.html?q=${encodeURIComponent(query)}`;
        }
    };
    searchInput.addEventListener('keydown', (event) => { if (event.key === 'Enter') performSearch(); });
    searchButton.addEventListener('click', performSearch);
}

/**
 * Hàm tổng hợp để khởi tạo toàn bộ header.
 */
function initializeHeader() {
    console.log("js/header.js: Bắt đầu chạy hàm initializeHeader().");
    initHeaderUI();
    initSearch();
}

// Lắng nghe sự kiện storage để cập nhật UI giữa các tab
window.addEventListener('storage', (event) => {
    // Lắng nghe tất cả các key liên quan
    if (['token', 'user', 'isLoggedIn', 'loggedInUser'].includes(event.key)) {
        if (typeof initHeaderUI === 'function') initHeaderUI();
    }
});