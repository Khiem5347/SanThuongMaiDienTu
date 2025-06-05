// js/taikhoan.js
document.addEventListener('DOMContentLoaded', function() {
    // Kiểm tra xem người dùng đã đăng nhập chưa
    const isLoggedIn = localStorage.getItem('isLoggedIn');
    const username = localStorage.getItem('loggedInUser');

    if (isLoggedIn !== 'true' || !username) {
        // Nếu chưa đăng nhập, chuyển hướng về trang đăng nhập
        alert('Bạn cần đăng nhập để xem trang này.');
        window.location.href = '../pages/dangnhap.html'; // Điều chỉnh đường dẫn nếu cần
        return;
    }

    // Lấy các phần tử trên form
    const usernameDisplay = document.getElementById('username');
    const fullNameInput = document.getElementById('fullName');
    const emailDisplay = document.getElementById('email');
    const phoneDisplay = document.getElementById('phone');
    const dobInput = document.getElementById('dateOfBirth');
    const avatarPreview = document.getElementById('avatarPreview');
    const profileForm = document.getElementById('profileForm');

    // Hàm để fetch thông tin người dùng từ API
    async function fetchUserInfo(loggedInUsername) {
        try {
            // API endpoint của bạn là /api/users/{username}
            const response = await fetch(`http://localhost:8080/api/users/${loggedInUsername}`);
            if (!response.ok) {
                if (response.status === 404) {
                    throw new Error(`Không tìm thấy người dùng với tên: ${loggedInUsername}`);
                }
                throw new Error(`Lỗi khi tải thông tin người dùng: ${response.statusText}`);
            }
            const userData = await response.json();
            populateProfileData(userData);
        } catch (error) {
            console.error('Lỗi khi fetchUserInfo:', error);
            alert(`Không thể tải thông tin tài khoản: ${error.message}`);
            // Có thể chuyển hướng hoặc hiển thị thông báo lỗi trên trang
        }
    }

    // Hàm để điền thông tin người dùng vào form
    function populateProfileData(user) {
        if (!user) return;

        if (usernameDisplay) usernameDisplay.textContent = user.username || '';
        if (fullNameInput) fullNameInput.value = user.fullName || '';
        if (emailDisplay) emailDisplay.textContent = user.email || ''; // Email thường không cho sửa trực tiếp
        if (phoneDisplay) phoneDisplay.textContent = user.phone || ''; // Số điện thoại có thể cho sửa ở form khác
        
        // Xử lý ngày sinh (Date of Birth)
        // Backend có thể trả về dateOfBirth dưới dạng timestamp hoặc chuỗi "YYYY-MM-DDTHH:mm:ss.sssZ"
        // Input type="date" cần giá trị "YYYY-MM-DD"
        if (dobInput && user.dateOfBirth) {
            try {
                const date = new Date(user.dateOfBirth);
                // Lấy YYYY-MM-DD, chú ý múi giờ có thể ảnh hưởng nếu chỉ dùng toISOString().substring(0,10)
                // Cách an toàn hơn là lấy từng phần:
                const year = date.getFullYear();
                const month = ('0' + (date.getMonth() + 1)).slice(-2); // Tháng từ 0-11
                const day = ('0' + date.getDate()).slice(-2);
                dobInput.value = `${year}-${month}-${day}`;
            } catch (e) {
                console.error("Lỗi định dạng ngày sinh:", e);
                dobInput.value = ''; // Để trống nếu lỗi
            }
        }

        // Xử lý giới tính
        if (user.gender) {
            const genderRadio = document.querySelector(`input[name="gender"][value="${user.gender.toUpperCase()}"]`);
            if (genderRadio) {
                genderRadio.checked = true;
            }
        }

        // Xử lý Avatar
        if (avatarPreview && user.avatarUrl) {
            avatarPreview.src = user.avatarUrl;
        } else if (avatarPreview) {
            // Nếu không có avatarUrl, giữ lại ảnh mặc định đã có trong HTML
            // Hoặc đặt một ảnh mặc định khác từ JS nếu muốn
            // avatarPreview.src = '../assets/default-avatar.png';
        }
    }

    // Xử lý sự kiện submit form (Lưu thay đổi)
    if (profileForm) {
        profileForm.addEventListener('submit', async function(event) {
            event.preventDefault();
            // TODO: Thu thập dữ liệu từ form
            const updatedFullName = fullNameInput.value;
            const updatedDob = dobInput.value;
            const selectedGender = document.querySelector('input[name="gender"]:checked')?.value;

            const updatedData = {
                fullName: updatedFullName,
                dateOfBirth: updatedDob,
                gender: selectedGender
                // Thêm các trường khác bạn muốn cho phép cập nhật
                // Ví dụ: email, phone nếu bạn làm chức năng thay đổi và xác thực chúng
            };

            console.log('Dữ liệu cập nhật:', updatedData);
            alert('Chức năng lưu thông tin chưa được cài đặt hoàn chỉnh ở ví dụ này. Dữ liệu đã được log ra console.');

            // TODO: Gửi dữ liệu cập nhật lên server (ví dụ dùng API PUT /api/users/{username})
            // try {
            //     const response = await fetch(`http://localhost:8080/api/users/${username}`, {
            //         method: 'PUT', // Hoặc PATCH
            //         headers: {
            //             'Content-Type': 'application/json',
            //             // Thêm Authorization header nếu API yêu cầu token
            //         },
            //         body: JSON.stringify(updatedData)
            //     });
            //     if (!response.ok) {
            //         const errorData = await response.text();
            //         throw new Error(`Lỗi khi cập nhật thông tin: ${errorData}`);
            //     }
            //     alert('Cập nhật thông tin thành công!');
            //     const updatedUser = await response.json();
            //     populateProfileData(updatedUser); // Cập nhật lại form với data mới nhất từ server (nếu có)
            // } catch (error) {
            //     console.error('Lỗi khi lưu thông tin:', error);
            //     alert(`Lỗi: ${error.message}`);
            // }
        });
    }

    // Gọi hàm fetch thông tin khi trang tải xong
    fetchUserInfo(username);
});