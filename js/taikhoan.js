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
            const tabs = ['profile', 'bank', 'address', 'password'];
            tabs.forEach(tab => {
                const section = document.getElementById(`section-${tab}`);
                if (section) section.style.display = 'none';
            });

            if (this.textContent.includes('Hồ Sơ')) {
                document.getElementById('section-profile').style.display = 'flex';
                sectionTitle.textContent = 'Hồ Sơ Của Tôi';
                sectionDescription.textContent = 'Quản lý thông tin hồ sơ để bảo mật tài khoản';
            } else if (this.textContent.includes('Ngân Hàng')) {
                document.getElementById('section-bank').style.display = 'flex';
                sectionTitle.textContent = 'Ngân Hàng';
                sectionDescription.textContent = 'Quản lý thông tin thẻ ngân hàng của bạn';
            } else if (this.textContent.includes('Địa Chỉ')) {
                document.getElementById('section-address').style.display = 'flex';
                sectionTitle.textContent = 'Địa Chỉ';
                sectionDescription.textContent = 'Danh sách các địa chỉ nhận hàng đã lưu';
            } else if (this.textContent.includes('Đổi Mật Khẩu')) {
                document.getElementById('section-password').style.display = 'flex';
                sectionTitle.textContent = 'Đổi Mật Khẩu';
                sectionDescription.textContent = 'Bảo vệ tài khoản bằng mật khẩu mạnh';
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

        if (usernameDisplay) usernameDisplay.textContent = user.username || '';
        if (fullNameInput) fullNameInput.value = user.fullName || '';
        if (emailDisplay) emailDisplay.textContent = user.email || '';
        if (phoneDisplay) phoneDisplay.textContent = user.phone || '';

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

        if (user.userId) fetchUserAddresses(user.userId);
    }

    async function fetchUserAddresses(userId) {
        try {
            const response = await fetch(`http://localhost:8080/api/users/${userId}/addresses`);
            if (!response.ok) throw new Error(`Lỗi khi tải địa chỉ: ${response.status}`);
            const data = await response.json();
            const addresses = data._embedded?.addresses || [];
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
                <button class="btn-delete" data-url="${addr._links.self.href}" style="margin-left: 10px; color:red;">Xóa</button>
            </div>
        `).join('');

        container.querySelectorAll('.btn-edit').forEach(btn => {
            btn.addEventListener('click', () => {
                const index = btn.getAttribute('data-index');
                const addr = addressCache[index];
                openAddressForm(addr);
            });
        });

        container.querySelectorAll('.btn-delete').forEach(btn => {
            btn.addEventListener('click', async () => {
                const url = btn.getAttribute('data-url');
                if (confirm('Bạn có chắc muốn xóa địa chỉ này?')) {
                    try {
                        await fetch(url, { method: 'DELETE' });
                        alert('Đã xóa địa chỉ.');
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
            const updatedFullName = fullNameInput.value;
            const updatedDob = dobInput.value;
            const selectedGender = document.querySelector('input[name="gender"]:checked')?.value;

            const updatedData = {
                fullName: updatedFullName,
                dateOfBirth: updatedDob,
                gender: selectedGender
            };

            console.log('Dữ liệu cập nhật:', updatedData);
            alert('Chức năng lưu thông tin chưa được cài đặt hoàn chỉnh. Dữ liệu đã được log ra console.');
        });
    }

    // === Xử lý popup địa chỉ ===
    btnAdd.addEventListener('click', () => openAddressForm());

    btnClose.addEventListener('click', () => {
        modal.style.display = 'none';
    });

    function openAddressForm(address = null) {
    document.getElementById('modalTitle').textContent = address ? 'Chỉnh sửa địa chỉ' : 'Thêm địa chỉ';
    document.getElementById('addressId').value = address?.addressId || '';
    document.getElementById('recipientName').value = address?.recipientName || '';
    document.getElementById('addressPhone').value = address?.phone || '';
    document.getElementById('detailAddress').value = address?.detailAddress || '';
    document.getElementById('isDefault').checked = !!address?.isDefault;
    document.getElementById('addressUpdateUrl').value = address?._links?.self?.href || '';
    modal.style.display = 'flex';
}

addressForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const updateUrl = document.getElementById('addressUpdateUrl').value;
    const payload = {
        recipientName: document.getElementById('recipientName').value,
        phone: document.getElementById('addressPhone').value,
        detailAddress: document.getElementById('detailAddress').value,
        isDefault: document.getElementById('isDefault').checked
    };

    const isEdit = !!updateUrl;

    if (!isEdit && !currentUserId) {
        alert("Không thể thêm địa chỉ vì chưa có thông tin người dùng.");
        return;
    }

    const url = isEdit
        ? updateUrl
        : `http://localhost:8080/api/users/${currentUserId}/addresses`;
    const method = isEdit ? 'PATCH' : 'POST';

    try {
        const res = await fetch(url, {
            method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!res.ok) {
            const text = await res.text();
            throw new Error(`Server trả về lỗi ${res.status}: ${text}`);
        }

        modal.style.display = 'none';
        fetchUserAddresses(currentUserId);
    } catch (error) {
        console.error(error);
        alert('Lỗi khi lưu địa chỉ: ' + error.message);
    }
});

    fetchUserInfo(username);
});
