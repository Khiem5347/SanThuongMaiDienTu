document.addEventListener('DOMContentLoaded', function () {
    const loginForm = document.querySelector('.login-container form');
    const usernameInput = document.getElementById('username'); // Cần đảm bảo id này có trong dangnhap.html
    const passwordInput = document.getElementById('password');

    if (loginForm) {
        loginForm.addEventListener('submit', async function (event) {
            event.preventDefault(); // Ngăn chặn form submit mặc định

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
                    credentials: 'include',
                    body: JSON.stringify({
                        username: username,
                        password: password,
                    }),
                });

                const result = await response.json();

                if (response.ok && result.success) {
                    // Đăng nhập thành công
                    localStorage.setItem('isLoggedIn', 'true');
                    localStorage.setItem('loggedInUser', username); // Lưu username
                    // Hoặc nếu API trả về username/thông tin user trong result:
                    // localStorage.setItem('loggedInUser', result.username);

                    alert(result.message || 'Đăng nhập thành công!');
                    window.location.href = '../pages/index.html'; // Chuyển hướng đến trang chủ
                } else {
                    // Đăng nhập thất bại
                    alert(result.message || 'Tên đăng nhập hoặc mật khẩu không chính xác.');
                    localStorage.removeItem('isLoggedIn');
                    localStorage.removeItem('loggedInUser');
                }
            } catch (error) {
                console.error('Lỗi khi đăng nhập:', error);
                alert('Đã xảy ra lỗi trong quá trình đăng nhập. Vui lòng thử lại.');
                localStorage.removeItem('isLoggedIn');
                localStorage.removeItem('loggedInUser');
            }
        });
    }
});