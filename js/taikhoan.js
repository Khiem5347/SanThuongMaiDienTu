document.addEventListener('DOMContentLoaded', function () {
    const isLoggedIn = localStorage.getItem('isLoggedIn');
    const username = localStorage.getItem('loggedInUser');

    if (isLoggedIn !== 'true' || !username) {
        alert('Bạn cần đăng nhập để xem trang này.');
        window.location.href = '../pages/dangnhap.html';
        return;
    }

    const usernameDisplay = document.getElementById('username');
    const fullNameInput = document.getElementById('fullName');
    const emailDisplay = document.getElementById('email');
    const phoneDisplay = document.getElementById('phone');
    const dobInput = document.getElementById('dateOfBirth');
    const avatarPreview = document.getElementById('avatarPreview');
    const profileForm = document.getElementById('profileForm');
    const sectionTitle = document.getElementById('section-title');
    const sectionDescription = document.getElementById('section-description');

    const modal = document.getElementById('addressModal');
    const btnAdd = document.getElementById('btnAddAddress');
    const btnClose = document.getElementById('btnCloseModal');
    const addressForm = document.getElementById('addressForm');

    let currentUserId = null;
    let addressCache = [];

    // === Chuyển TAB ===
    document.querySelectorAll('.account-sidebar ul ul li a').forEach(link => {
        link.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelectorAll('.profile-content').forEach(s => s.style.display = 'none');

            const tabMap = {
                'Hồ Sơ': 'profile',
                'Ngân Hàng': 'bank',
                'Địa Chỉ': 'address',
                'Đổi Mật Khẩu': 'password'
            };

            const text = this.textContent.trim();
            const tab = tabMap[text];
            if (tab) {
                document.getElementById(`section-${tab}`).style.display = 'flex';
                sectionTitle.textContent = text;
                sectionDescription.textContent = {
                    'Hồ Sơ': 'Quản lý thông tin hồ sơ để bảo mật tài khoản',
                    'Ngân Hàng': 'Quản lý thông tin thẻ ngân hàng của bạn',
                    'Địa Chỉ': 'Danh sách các địa chỉ nhận hàng đã lưu',
                    'Đổi Mật Khẩu': 'Bảo vệ tài khoản bằng mật khẩu mạnh'
                }[text];
            }

            document.querySelectorAll('.account-sidebar a').forEach(el => el.classList.remove('active'));
            this.classList.add('active');
        });
    });

    async function fetchUserInfo(loggedInUsername) {
        try {
            const response = await fetch(`http://localhost:8080/api/users/search/findByUsername?username=${loggedInUsername}`);
            if (!response.ok) throw new Error(`Lỗi khi tải thông tin người dùng: ${response.status}`);
            const userData = await response.json();
            populateProfileData(userData);
        } catch (error) {
            console.error('Lỗi khi fetchUserInfo:', error);
            alert(`Không thể tải thông tin tài khoản: ${error.message}`);
        }
    }

    function populateProfileData(user) {
        if (!user) return;
        currentUserId = user.userId;

        usernameDisplay.textContent = user.username || '';
        fullNameInput.value = user.fullName || '';
        emailDisplay.textContent = user.email || '';
        phoneDisplay.textContent = user.phone || '';

        if (dobInput && user.dateOfBirth) {
            try {
                const date = new Date(user.dateOfBirth);
                const year = date.getFullYear();
                const month = ('0' + (date.getMonth() + 1)).slice(-2);
                const day = ('0' + date.getDate()).slice(-2);
                dobInput.value = `${year}-${month}-${day}`;
            } catch (e) {
                console.error("Lỗi định dạng ngày sinh:", e);
                dobInput.value = '';
            }
        }

        if (user.gender) {
            const genderRadio = document.querySelector(`input[name="gender"][value="${user.gender.toUpperCase()}"]`);
            if (genderRadio) genderRadio.checked = true;
        }

        if (avatarPreview && user.avatarUrl) avatarPreview.src = user.avatarUrl;

        fetchUserAddresses(currentUserId);
    }

    async function fetchUserAddresses(userId) {
        try {
            const response = await fetch(`http://localhost:8080/api/addresses/user/${userId}`);
            if (!response.ok) throw new Error(`Lỗi khi tải địa chỉ: ${response.status}`);
            const addresses = await response.json();
            renderAddressList(addresses);
        } catch (error) {
            console.error('Lỗi khi fetch địa chỉ:', error);
            document.getElementById('addressList').innerHTML =
                `<p style="color:red;">Không thể tải địa chỉ: ${error.message}</p>`;
        }
    }

    function renderAddressList(addresses) {
        addressCache = addresses;
        const container = document.getElementById('addressList');
        if (!container) return;

        if (!addresses.length) {
            container.innerHTML = "<p>Không có địa chỉ nào.</p>";
            return;
        }

        container.innerHTML = addresses.map((addr, index) => `
            <div class="address-item" style="border:1px solid #ccc; border-radius:6px; padding:15px; margin-bottom:15px; background:#f9f9f9;">
                <p><strong>Người nhận:</strong> ${addr.recipientName}</p>
                <p><strong>SĐT:</strong> ${addr.phone}</p>
                <p><strong>Địa chỉ:</strong> ${addr.detailAddress}</p>
                ${addr.isDefault ? '<p style="color: green;"><em>Địa chỉ mặc định</em></p>' : ''}
                <button class="btn-edit" data-index="${index}">Sửa</button>
                <button class="btn-delete" data-id="${addr.addressId}">Xóa</button>
            </div>
        `).join('');

        container.querySelectorAll('.btn-edit').forEach(btn => {
            btn.addEventListener('click', () => {
                const addr = addressCache[btn.dataset.index];
                openAddressForm(addr);
            });
        });

        container.querySelectorAll('.btn-delete').forEach(btn => {
            btn.addEventListener('click', async () => {
                const id = btn.getAttribute('data-id');
                const deleteUrl = `http://localhost:8080/api/addresses/${id}/user/${currentUserId}`;
                if (confirm('Bạn có chắc muốn xóa địa chỉ này?')) {
                    try {
                        await fetch(deleteUrl, { method: 'DELETE' });
                        alert('Xóa thành công');
                        fetchUserAddresses(currentUserId);
                    } catch (err) {
                        alert('Lỗi khi xóa địa chỉ: ' + err.message);
                    }
                }
            });
        });
    }

    if (profileForm) {
        profileForm.addEventListener('submit', async function (event) {
            event.preventDefault();
            const updatedData = {
                fullName: fullNameInput.value,
                dateOfBirth: dobInput.value,
                gender: document.querySelector('input[name="gender"]:checked')?.value
            };

            try {
                const res = await fetch(`http://localhost:8080/api/users/${currentUserId}`, {
                    method: 'PATCH',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify(updatedData)
                });

                if (!res.ok) throw new Error(`Cập nhật thất bại: ${res.status}`);
                alert('Cập nhật thông tin thành công!');
            } catch (err) {
                alert('Lỗi cập nhật hồ sơ: ' + err.message);
            }
        });
    }

    // === Xử lý popup địa chỉ ===
    btnAdd.addEventListener('click', () => openAddressForm());

    btnClose.addEventListener('click', () => {
        modal.style.display = 'none';
        addressForm.reset();
    });

    function openAddressForm(address = null) {
        document.getElementById('modalTitle').textContent = address ? 'Chỉnh sửa địa chỉ' : 'Thêm địa chỉ';
        document.getElementById('addressId').value = address?.addressId || '';
        document.getElementById('recipientName').value = address?.recipientName || '';
        document.getElementById('addressPhone').value = address?.phone || '';
        document.getElementById('detailAddress').value = address?.detailAddress || '';
        document.getElementById('isDefault').checked = !!address?.isDefault;
        modal.style.display = 'flex';
    }

    addressForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const isEdit = !!document.getElementById('addressId').value;
        const addressId = document.getElementById('addressId').value;

        const payload = {
            userId: currentUserId,
            recipientName: document.getElementById('recipientName').value,
            phone: document.getElementById('addressPhone').value,
            detailAddress: document.getElementById('detailAddress').value,
            isDefault: document.getElementById('isDefault').checked
        };

        const url = isEdit
            ? `http://localhost:8080/api/addresses/${addressId}`
            : `http://localhost:8080/api/addresses`;
        const method = isEdit ? 'PUT' : 'POST';

        try {
            const res = await fetch(url, {
                method,
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!res.ok) throw new Error(`Lỗi: ${res.status}`);

            // Nếu đặt là mặc định thì gọi thêm API đặt mặc định
            if (payload.isDefault && isEdit) {
                const setDefaultUrl = `http://localhost:8080/api/addresses/user/${currentUserId}/default/${addressId}`;
                await fetch(setDefaultUrl, { method: 'PATCH' });
            }

            modal.style.display = 'none';
            fetchUserAddresses(currentUserId);
        } catch (error) {
            console.error(error);
            alert('Lỗi khi lưu địa chỉ: ' + error.message);
        }
    });

    // === ĐỔI MẬT KHẨU ===
    const passwordForm = document.querySelector('#section-password form');

    if (passwordForm) {
        passwordForm.addEventListener('submit', async function (event) {
            event.preventDefault();

            const currentPassword = document.getElementById('currentPassword').value.trim();
            const newPassword = document.getElementById('newPassword').value.trim();
            const confirmPassword = document.getElementById('confirmPassword').value.trim();

            if (!currentPassword || !newPassword || !confirmPassword) {
                alert("Vui lòng nhập đầy đủ thông tin.");
                return;
            }

            if (newPassword !== confirmPassword) {
                alert("Mật khẩu mới và xác nhận không khớp.");
                return;
            }

            try {
                const res = await fetch('http://localhost:8080/api/users/change-password', {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        username: username,
                        oldPassword: currentPassword,
                        newPassword: newPassword
                    })
                });

                if (!res.ok) {
                    const errorText = await res.text();
                    throw new Error(errorText || `Lỗi ${res.status}`);
                }

                alert("Đổi mật khẩu thành công!");
                passwordForm.reset();
            } catch (error) {
                console.error("Lỗi đổi mật khẩu:", error);
                alert("Đổi mật khẩu thất bại: " + error.message);
            }
        });
    }

    // Gọi API lần đầu
    fetchUserInfo(username);
});