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

    const isLoggedIn = localStorage.getItem('isLoggedIn');
    const username = localStorage.getItem('loggedInUser');

    const existingUserDropdown = headerUl.querySelector('.user-dropdown-li');
    if (existingUserDropdown) existingUserDropdown.remove();

    if (isLoggedIn === 'true' && username) {
        if (loginNavLi) loginNavLi.style.display = 'none';

        const userDropdownLi = document.createElement('li');
        userDropdownLi.classList.add('user-dropdown-li');

        const usernameLink = document.createElement('a');
        usernameLink.href = '#';
        usernameLink.textContent = username;
        usernameLink.classList.add('user-dropdown-trigger');
        userDropdownLi.appendChild(usernameLink);

        const dropdownContent = document.createElement('div');
        dropdownContent.classList.add('user-dropdown-content');

        const myAccountLink = document.createElement('a');
        myAccountLink.href = '../pages/taikhoan.html';
        myAccountLink.textContent = 'Tài Khoản Của Tôi';
        dropdownContent.appendChild(myAccountLink);

        const logoutLinkDropdown = document.createElement('a');
        logoutLinkDropdown.href = '#';
        logoutLinkDropdown.textContent = 'Đăng Xuất';
        logoutLinkDropdown.addEventListener('click', function (e) {
            e.preventDefault();
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('loggedInUser');
            alert('Bạn đã đăng xuất.');
            window.location.href = '../pages/index.html';
        });
        dropdownContent.appendChild(logoutLinkDropdown);

        userDropdownLi.appendChild(dropdownContent);

        userDropdownLi.addEventListener('mouseenter', () => dropdownContent.style.display = 'block');
        userDropdownLi.addEventListener('mouseleave', () => dropdownContent.style.display = 'none');

        headerUl.prepend(userDropdownLi);
    } else {
        if (loginNavLi) loginNavLi.style.display = '';
    }
    console.log("js/header.js: Hàm initHeaderUI() đã chạy xong.");
}

/**
 * Khởi tạo chức năng tìm kiếm đơn giản.
 */
function initSearch() {
    console.log("js/header.js: Bắt đầu chạy hàm initSearch() đã được đơn giản hóa.");

    const searchInput = document.getElementById('searchInput');
    const searchButton = document.getElementById('searchButton');

    if (!searchInput || !searchButton) {
        console.error("js/header.js: (Lỗi trong initSearch) Không tìm thấy searchInput hoặc searchButton.");
        return;
    }

    // Hàm thực hiện chuyển trang khi tìm kiếm
    const performSearch = () => {
        const query = searchInput.value.trim();
        if (query) {
            window.location.href = `../pages/timkiem.html?q=${encodeURIComponent(query)}`;
        }
    };

    // Gán sự kiện cho nút Enter trên ô input
    searchInput.addEventListener('keydown', (event) => {
        if (event.key === 'Enter') {
            performSearch();
        }
    });

    // Gán sự kiện cho nút tìm kiếm
    searchButton.addEventListener('click', performSearch);

    console.log("js/header.js: Hàm initSearch() đã chạy xong.");
}

/**
 * Hàm tổng hợp để khởi tạo toàn bộ header.
 */
function initializeHeader() {
    console.log("js/header.js: Bắt đầu chạy hàm initializeHeader().");
    initHeaderUI();
    initSearch();
    console.log("js/header.js: Hàm initializeHeader() đã chạy xong.");
}

// Lắng nghe sự kiện storage để cập nhật UI giữa các tab
window.addEventListener('storage', (event) => {
    if (event.key === 'isLoggedIn' || event.key === 'loggedInUser') {
        if (typeof initHeaderUI === 'function') initHeaderUI();
    }
});
