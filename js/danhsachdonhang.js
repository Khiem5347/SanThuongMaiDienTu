document.addEventListener('DOMContentLoaded', () => {
  const orderListEl = document.getElementById('order-list');
  
  const orders = JSON.parse(localStorage.getItem('userOrders')) || [];

  orderListEl.innerHTML = ''; 

  if (orders.length === 0) {
    // Cập nhật colspan thành 7
    orderListEl.innerHTML = '<tr><td colspan="7" style="text-align: center;">Chưa có đơn hàng nào.</td></tr>';
    return;
  }

  orders.sort((a, b) => new Date(b.orderDate) - new Date(a.orderDate));

  orders.forEach(order => {
    const tr = document.createElement('tr');
    tr.dataset.orderId = order.orderId; // Thêm data-attribute để dễ dàng tìm thấy hàng cần xóa

    let productsHtml = '<div class="product-list">';
    
    if (order.items && order.items.length > 0) {
      order.items.forEach(item => {
        const imageUrl = item.variation?.imageUrl || '../assets/default-product.jpg';
        const productName = item.productName || 'Sản phẩm không xác định';
        const variationOption = item.variation?.option || '';
        const quantity = item.quantity || 1;

        productsHtml += `
          <div class="product-item">
            <img src="${imageUrl}" alt="${productName}" class="product-image-small">
            <div>
                <span>${productName} - ${variationOption}</span><br>
                <small>Số lượng: ${quantity}</small>
            </div>
          </div>
        `;
      });
    } else {
      productsHtml += '<span>Không có thông tin sản phẩm.</span>';
    }
    
    productsHtml += '</div>';

    // Thêm cột mới với nút xóa vào cuối hàng
    tr.innerHTML = `
      <td><a href="chitietdonhang.html?id=${order.orderId}">${order.orderId}</a></td>
      <td>${productsHtml}</td>
      <td>${order.orderDate}</td>
      <td>${order.paymentMethod}</td>
      <td>${formatPrice(order.shippingFee)}</td>
      <td>${formatPrice(order.itemsTotalAmount + order.shippingFee)}</td>
      <td>
        <button class="delete-btn" data-order-id="${order.orderId}">Xóa</button>
      </td>
    `;

    orderListEl.appendChild(tr);
  });

  // ===============================================
  // === LOGIC XÓA ĐƠN HÀNG ĐƯỢC THÊM VÀO TẠI ĐÂY ===
  // ===============================================
  orderListEl.addEventListener('click', function(event) {
    // Chỉ thực hiện khi click đúng vào nút có class 'delete-btn'
    if (event.target.classList.contains('delete-btn')) {
      const button = event.target;
      const orderIdToDelete = button.dataset.orderId;

      // Hiển thị hộp thoại xác nhận
      if (confirm(`Bạn có chắc chắn muốn xóa đơn hàng ${orderIdToDelete} không?`)) {
        // 1. Lấy danh sách đơn hàng hiện tại từ localStorage
        let currentOrders = JSON.parse(localStorage.getItem('userOrders')) || [];

        // 2. Lọc ra những đơn hàng không bị xóa
        const updatedOrders = currentOrders.filter(order => order.orderId !== orderIdToDelete);

        // 3. Lưu danh sách đã cập nhật trở lại localStorage
        localStorage.setItem('userOrders', JSON.stringify(updatedOrders));

        // 4. Xóa hàng (<tr>) tương ứng khỏi bảng trên giao diện
        button.closest('tr').remove();

        // 5. Kiểm tra nếu không còn đơn hàng nào, hiển thị thông báo
        if (updatedOrders.length === 0) {
          orderListEl.innerHTML = '<tr><td colspan="7" style="text-align: center;">Chưa có đơn hàng nào.</td></tr>';
        }
      }
    }
  });
});

function formatPrice(number) {
    if (typeof number !== 'number' || isNaN(number)) {
        return '0đ';
    }
    return number.toLocaleString('vi-VN') + 'đ';
}