function formatPrice(number) {
  return number.toLocaleString('vi-VN') + 'đ';
}

document.addEventListener('DOMContentLoaded', () => {
  const params = new URLSearchParams(window.location.search);
  const orderId = params.get('id');

  const orders = JSON.parse(localStorage.getItem('userOrders')) || [];
  const order = orders.find(o => o.orderId === orderId);

  const container = document.getElementById('order-details');

  if (!order) {
    container.innerHTML = "<p>Không tìm thấy đơn hàng.</p>";
    return;
  }

  const itemsHtml = order.items.map(item => {
  const name = item.productName || 'Không rõ';
  const option = item.variation?.option || 'N/A';
  const quantity = item.quantity || 1;
  const price = item.variation?.price || 0;
  const total = price * quantity;
  const imageUrl = item.variation?.imageUrl || '../assets/default-product.jpg'; // ảnh mặc định nếu không có

  return `
    <tr>
      <td><img src="${imageUrl}" alt="ảnh" style="width: 60px; height: 60px; object-fit: cover;"></td>
      <td>${name}</td>
      <td>${option}</td>
      <td>${quantity}</td>
      <td>${formatPrice(price)}</td>
      <td>${formatPrice(total)}</td>
    </tr>
  `;
}).join('');


  const totalItemsAmount = order.itemsTotalAmount || 0;
  const shipping = order.shippingFee || 0;
  const totalOrder = totalItemsAmount + shipping;

  container.innerHTML = `
    <p><strong>Mã đơn hàng:</strong> ${order.orderId}</p>
    <p><strong>Ngày đặt:</strong> ${order.orderDate}</p>
    <p><strong>Phương thức thanh toán:</strong> ${order.paymentMethod}</p>

    <table>
      <thead>
        <tr>
          <th>Ảnh</th>
          <th>Tên sản phẩm</th>
          <th>Phân loại</th>
          <th>Số lượng</th>
          <th>Giá</th>
          <th>Thành tiền</th>
        </tr>
      </thead>
      <tbody>
        ${itemsHtml}
      </tbody>
    </table>

    <div class="summary">
      <p>Tạm tính: ${formatPrice(totalItemsAmount)}</p>
      <p>Phí vận chuyển: ${formatPrice(shipping)}</p>
      <p>Tổng thanh toán: ${formatPrice(totalOrder)}</p>
    </div>
  `;
});
