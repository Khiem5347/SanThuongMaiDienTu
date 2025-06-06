// [Thư mục gốc]/js/header.js

console.log("js/header.js: File đã tải, hàm initHeaderUI đã được định nghĩa.");

function initHeaderUI() {
    console.log("js/header.js: hàm initHeaderUI() được gọi.");

    const loginNavLi = document.getElementById('loginNav')?.parentElement;
    const headerUl = document.querySelector('header ul');

    console.log("js/header.js: (trong initHeaderUI) loginNavLi tìm thấy:", loginNavLi);
    console.log("js/header.js: (trong initHeaderUI) headerUl tìm thấy:", headerUl);

    if (!headerUl) {
        console.error("js/header.js: (trong initHeaderUI) Vẫn không tìm thấy thẻ ul trong header.");
        return;
    }
    if (!loginNavLi && !localStorage.getItem('isLoggedIn')) {
        console.warn("js/header.js: (trong initHeaderUI) Không tìm thấy loginNavLi. Đảm bảo thẻ a đăng nhập có id='loginNav'.");
    }

    const isLoggedIn = localStorage.getItem('isLoggedIn');
    const username = localStorage.getItem('loggedInUser');

    console.log("js/header.js: (trong initHeaderUI) Trạng thái đăng nhập (isLoggedIn):", isLoggedIn);
    console.log("js/header.js: (trong initHeaderUI) Tên người dùng (username):", username);

    // Xóa các mục tài khoản/dropdown cũ trước khi thêm mới
    const existingUserDropdown = headerUl.querySelector('.user-dropdown-li');
    if (existingUserDropdown) existingUserDropdown.remove();


    if (isLoggedIn === 'true' && username) {
        console.log("js/header.js: (trong initHeaderUI) Người dùng đã đăng nhập, tạo dropdown tài khoản.");
        if (loginNavLi) loginNavLi.style.display = 'none';

        // Tạo phần tử <li> chính cho dropdown
        const userDropdownLi = document.createElement('li');
        userDropdownLi.classList.add('user-dropdown-li'); // Class để CSS

        // Tạo link hiển thị tên người dùng (trigger cho dropdown)
        const usernameLink = document.createElement('a');
        usernameLink.href = '#'; // Không điều hướng khi click vào tên
        usernameLink.textContent = username;
        usernameLink.classList.add('user-dropdown-trigger');
        userDropdownLi.appendChild(usernameLink);

        // Tạo nội dung dropdown (ban đầu ẩn)
        const dropdownContent = document.createElement('div');
        dropdownContent.classList.add('user-dropdown-content');

        // Tạo link "Tài Khoản Của Tôi"
        const myAccountLink = document.createElement('a');
        myAccountLink.href = '../pages/taikhoan.html'; // Đường dẫn đến trang tài khoản
        myAccountLink.textContent = 'Tài Khoản Của Tôi';
        dropdownContent.appendChild(myAccountLink);

        // Tạo link "Đăng Xuất"
        const logoutLinkDropdown = document.createElement('a');
        logoutLinkDropdown.href = '#';
        logoutLinkDropdown.textContent = 'Đăng Xuất';
        logoutLinkDropdown.addEventListener('click', function(e) {
            e.preventDefault();
            localStorage.removeItem('isLoggedIn');
            localStorage.removeItem('loggedInUser');
            alert('Bạn đã đăng xuất.');
            window.location.href = '../pages/index.html'; // Hoặc trang đăng nhập
        });
        dropdownContent.appendChild(logoutLinkDropdown);

        userDropdownLi.appendChild(dropdownContent); // Thêm nội dung dropdown vào <li>

        // Thêm logic để hiện/ẩn dropdown khi di chuột hoặc click (ví dụ đơn giản với hover)
        userDropdownLi.addEventListener('mouseenter', () => {
            dropdownContent.style.display = 'block';
        });
        userDropdownLi.addEventListener('mouseleave', () => {
            dropdownContent.style.display = 'none';
        });

        // Chèn dropdown vào đầu <ul>
        headerUl.prepend(userDropdownLi);
        console.log("js/header.js: Đã chèn userDropdownLi vào đầu headerUl.");

    } else {
        console.log("js/header.js: (trong initHeaderUI) Người dùng chưa đăng nhập.");
        if (loginNavLi) {
            loginNavLi.style.display = '';
            const loginNavLink = loginNavLi.querySelector('a');
            if(loginNavLink) {
                loginNavLink.textContent = 'Đăng nhập';
                loginNavLink.href = "../pages/dangnhap.html";
            }
        }
    }
}

window.addEventListener('storage', function(event) {
    console.log("js/header.js: Sự kiện storage thay đổi:", event.key);
    if (event.key === 'isLoggedIn' || event.key === 'loggedInUser') {
        if (typeof initHeaderUI === 'function') {
            initHeaderUI();
        }
    }
});