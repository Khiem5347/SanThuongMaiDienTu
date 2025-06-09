// js/dangnhap.js
document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('.login-container form');
    const usernameInput = document.getElementById('username');
    const passwordInput = document.getElementById('password');

    if (loginForm) {
        loginForm.addEventListener('submit', async function (event) {
            event.preventDefault();

            const username = usernameInput.value.trim();
            const password = passwordInput.value.trim();

            if (!username || !password) {
                alert('Vui lòng nhập tên đăng nhập và mật khẩu.');
                return;
            }

            try {
                const response = await fetch('http://localhost:8080/api/users/login', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        username: username,
                        password: password,
                    }),
                });

                const result = await response.json();

                if (!response.ok) {
                    throw new Error(result.message || `Lỗi ${response.status}`);
                }
                
                // --- ĐÂY LÀ PHẦN CẬP NHẬT CHÍNH ---

                // Giả định API trả về một object chứa token và thông tin user
                // Ví dụ: result = { token: "...", user: { userId: 1, username: "..." } }
                if (result.token && result.user) {
                    // 1. LƯU THEO CHUẨN MỚI (ƯU TIÊN)
                    localStorage.setItem('token', result.token);
                    localStorage.setItem('user', JSON.stringify(result.user));

                    // 2. LƯU THEO CHUẨN CŨ ĐỂ TƯƠNG THÍCH VỚI HEADER.JS CŨ
                    localStorage.setItem('isLoggedIn', 'true');
                    localStorage.setItem('loggedInUser', result.user.username);

                    alert('Đăng nhập thành công!');
                    window.location.href = '../pages/index.html';
                } else {
                    // Nếu API trả về thành công nhưng không có token/user (trường hợp này ít xảy ra)
                    // thì chỉ lưu theo chuẩn cũ
                    localStorage.setItem('isLoggedIn', 'true');
                    localStorage.setItem('loggedInUser', username); // Dùng tạm username đã nhập
                    alert('Đăng nhập thành công! (Chế độ tương thích)');
                    window.location.href = '../pages/index.html';
                }

            } catch (error) {
                console.error('Lỗi khi đăng nhập:', error);
                alert(error.message || 'Tên đăng nhập hoặc mật khẩu không chính xác.');
                
                // Xóa tất cả các key khi có lỗi
                localStorage.removeItem('token');
                localStorage.removeItem('user');
                localStorage.removeItem('isLoggedIn');
                localStorage.removeItem('loggedInUser');
            }
        });
    }
});