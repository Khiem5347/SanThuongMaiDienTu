document.addEventListener('DOMContentLoaded', () => {
  const orderListEl = document.getElementById('order-list');
  orderListEl.innerHTML = '';

  // Lấy dữ liệu đơn hàng từ localStorage
  const orders = JSON.parse(localStorage.getItem('userOrders')) || [];

  if (orders.length === 0) {
    orderListEl.innerHTML = '<tr><td colspan="5">Chưa có đơn hàng nào.</td></tr>';
    return;
  }

  orders.forEach(order => {
    const tr = document.createElement('tr');

    tr.innerHTML = `
      <td><a href="chitietdonhang.html?id=${order.orderId}">${order.orderId}</a></td>
      <td>${order.orderDate}</td>
      <td>${order.paymentMethod}</td>
      <td>${formatPrice(order.shippingFee)}</td>
      <td>${formatPrice(order.itemsTotalAmount + order.shippingFee)}</td>
    `;

    orderListEl.appendChild(tr);
  });
});

function formatPrice(number) {
  return number.toLocaleString('vi-VN') + 'đ';
}
