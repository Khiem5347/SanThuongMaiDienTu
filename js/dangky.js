document.addEventListener('DOMContentLoaded', function () {
    const registerForm = document.getElementById('registerForm'); // Sử dụng ID của form

    if (registerForm) {
        // Lấy các element từ form một lần khi DOM đã tải
        const usernameInput = document.getElementById('username');
        const emailInput = document.getElementById('email');
        const fullnameInput = document.getElementById('fullname');
        const phoneInput = document.getElementById('phone');
        const dobInput = document.getElementById('dob');
        const passwordInput = document.getElementById('password');
        const confirmPasswordInput = document.getElementById('confirm-password');
        // const genderInput = document.getElementById('gender'); // Nếu bạn thêm trường gender

        registerForm.addEventListener('submit', async function (event) {
            event.preventDefault(); // Ngăn chặn form submit mặc định

            // Lấy giá trị từ các input
            const username = usernameInput.value.trim();
            const email = emailInput.value.trim();
            const fullName = fullnameInput.value.trim();
            const phone = phoneInput.value.trim();
            const dateOfBirth = dobInput.value; // Giữ nguyên dạng "YYYY-MM-DD"
            const password = passwordInput.value;
            const confirmPassword = confirmPasswordInput.value;
            // const gender = genderInput ? genderInput.value : undefined; // Lấy giá trị gender nếu có

            if (password !== confirmPassword) {
                alert('Mật khẩu xác nhận không khớp!');
                return;
            }

            if (!username || !email || !fullName || !phone || !dateOfBirth || !password) {
                alert('Vui lòng điền đầy đủ các trường bắt buộc.');
                return;
            }

            // Tạo đối tượng dữ liệu để gửi lên server
            // Khớp với các trường trong UserRegistrationDTO
            const registrationData = {
                username: username,
                email: email,
                password: password,
                fullName: fullName,
                phone: phone,
                dateOfBirth: dateOfBirth,
                // gender: gender, // Thêm gender nếu có trong form và DTO
                // avatarUrl: null, // Backend có thể xử lý hoặc bạn cho phép upload sau
                // userRole: "USER" // Backend thường tự gán vai trò mặc định
            };

            console.log('Sending registration data:', registrationData); // Để debug

            try {
                const response = await fetch('http://localhost:8080/api/users/register', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(registrationData),
                });

                if (response.status === 201) { // HttpStatus.CREATED
                    alert('Đăng ký thành công! Vui lòng đăng nhập.');
                    window.location.href = '../pages/dangnhap.html'; // Chuyển hướng đến trang đăng nhập
                } else {
                    let errorData = 'Đăng ký thất bại.';
                    try {
                        const errorResult = await response.json();
                        if (errorResult && (errorResult.message || typeof errorResult === 'string')) {
                            errorData = errorResult.message || errorResult;
                        } else if (response.statusText) {
                            errorData = `${response.status} ${response.statusText}`;
                        }
                    } catch (e) {
                        // Nếu không phải JSON, lấy text nếu có, hoặc status text
                        const textError = await response.text();
                        errorData = textError || `${response.status} ${response.statusText}`;
                    }
                    alert('Đăng ký thất bại: ' + errorData);
                }
            } catch (error) {
                console.error('Lỗi khi đăng ký:', error);
                alert('Đã xảy ra lỗi trong quá trình đăng ký. Vui lòng thử lại sau.');
            }
        });
    }
});