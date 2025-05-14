// Chuyển động banner
new Swiper('.swiper', {
  loop: true,
  autoplay: {
    delay: 3000, // Tự động chạy sau mỗi 3 giây
    disableOnInteraction: false, // Tiếp tục tự chạy sau khi người dùng tương tác
  },
  navigation: {
    nextEl: '.swiper-button-next', // Selector của nút Next
    prevEl: '.swiper-button-prev', // Selector của nút Prev
  },
  pagination: {
    el: '.swiper-pagination',
    clickable: true, // Cho phép click vào dấu chấm để chuyển slide
  },
});

// Thêm header , footer
async function loadHTML(id, file) {
  const res = await fetch(file);
  const html = await res.text();
  document.getElementById(id).innerHTML = html;
}
// Gọi khi trang tải xong
window.addEventListener("DOMContentLoaded", () => {
  loadHTML("header", "../components/header.html");
  loadHTML("footer", "../components/footer.html");
});



