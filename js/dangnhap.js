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
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({ username, password }),
                });

                const result = await response.json();

                if (!response.ok) {
                    throw new Error(result.message || `Lỗi ${response.status}`);
                }
                
                // --- LOGIC LƯU TRỮ KHI ĐĂNG NHẬP THÀNH CÔNG ---
                // API đã trả về token, userId, và username
                if (result.token && result.userId && result.username) {
                    
                    // 1. LƯU THEO CHUẨN MỚI (Bắt buộc cho các chức năng như đánh giá, đặt hàng)
                    localStorage.setItem('token', result.token);
                    
                    // Tạo một user object để lưu trữ nhất quán
                    const user = {
                        userId: result.userId,
                        username: result.username
                        // Thêm email hoặc các thông tin khác nếu API trả về
                    };
                    localStorage.setItem('user', JSON.stringify(user));

                    // 2. LƯU THEO CHUẨN CŨ (để tương thích với các script cũ nếu có)
                    localStorage.setItem('isLoggedIn', 'true');
                    localStorage.setItem('loggedInUser', result.username);
                    alert(result.message || 'Đăng nhập thành công!');
                    window.location.href = '../pages/index.html';

                } else {
                    // Xử lý trường hợp API trả về thành công nhưng thiếu dữ liệu
                    throw new Error('Dữ liệu đăng nhập trả về không đầy đủ.');
                }

            } catch (error) {
                console.error('Lỗi khi đăng nhập:', error);
                alert(error.message || 'Tên đăng nhập hoặc mật khẩu không chính xác.');
                
                // Dọn dẹp tất cả các key khi có lỗi
                localStorage.removeItem('token');
                localStorage.removeItem('user');
                localStorage.removeItem('isLoggedIn');
                localStorage.removeItem('loggedInUser');
            }
        });
    }
});