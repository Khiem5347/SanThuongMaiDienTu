// js/header.js
console.log("js/header.js: File đã tải.");

/**
 * Khởi tạo giao diện người dùng cho header (đăng nhập, dropdown).
 */
function initHeaderUI() {
    const loginNavLi = document.getElementById('loginNav')?.parentElement;
    const headerUl = document.querySelector('header ul');
    if (!headerUl) {
        console.error("Lỗi: Không tìm thấy <ul> trong header.");
        return;
    }

    // --- LOGIC KIỂM TRA "HYBRID" ---
    // 1. Ưu tiên kiểm tra theo chuẩn mới (token và user object)
    const token = localStorage.getItem('token');
    const userString = localStorage.getItem('user');
    const user = userString ? JSON.parse(userString) : null;

    // 2. Nếu không có, kiểm tra theo chuẩn cũ để tương thích
    const isLoggedIn_old = localStorage.getItem('isLoggedIn');
    const username_old = localStorage.getItem('loggedInUser');

    // 3. Quyết định trạng thái cuối cùng
    const finalIsLoggedIn = !!token || (isLoggedIn_old === 'true');
    const finalUsername = user?.username || username_old;

    // Xóa dropdown cũ để tránh hiển thị trùng lặp khi re-render
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
        
        // Gắn sự kiện cho nút logout (xóa tất cả các key liên quan)
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
}

/**
 * Khởi tạo chức năng tìm kiếm đơn giản.
 */
function initSearch() {
    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');
    if (!searchInput || !searchButton) return;
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
    initHeaderUI();
    initSearch();
}

// Lắng nghe sự kiện storage để cập nhật UI giữa các tab
window.addEventListener('storage', (event) => {
    if (['token', 'user', 'isLoggedIn', 'loggedInUser'].includes(event.key)) {
        if (typeof initHeaderUI === 'function') initHeaderUI();
    }
});