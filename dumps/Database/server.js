const express = require('express');
const mysql = require('mysql2');
const cors = require('cors'); // Import cors

const app = express();
const port = 3000;


const connection = mysql.createConnection({
  host: 'gvdp4.h.filess.io',
  port: 61002,
  user: 'shopdb_lookwordno',
  password: 'duyen123456',
  database: 'shopdb_lookwordno'
});

connection.connect(err => {
  if (err) {
    console.error('Lỗi kết nối:', err);
    return;
  }
  console.log('Kết nối MySQL thành công!');
});

app.use(cors()); // Sử dụng middleware cors
app.get('/', (req, res) => {
  res.send('Chào mừng đến với API ShopDB!');
});

// API lấy danh sách sản phẩm
app.get('/api/products', (req, res) => {
  connection.query('SELECT * FROM Products', (err, results) => {
    if (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).send('Lỗi truy vấn CSDL');
    } else {
      res.json(results);
    }
  });
});
// API lấy chi tiết 1 sản phẩm theo ID
app.get('/api/products/:id', (req, res) => {
    const productId = req.params.id; // Lấy ID từ URL (ví dụ: /api/products/1)
    // Chú ý: Sử dụng prepared statement hoặc sanitization để tránh SQL Injection
    const query = 'SELECT * FROM Products WHERE product_id = ?';
    connection.query(query, [productId], (err, results) => {
        if (err) {
            console.error('Lỗi truy vấn chi tiết sản phẩm:', err);
            res.status(500).send('Lỗi truy vấn CSDL');
        } else {
            // results là một mảng, nhưng nếu product_id là khóa chính thì chỉ có 0 hoặc 1 kết quả
            if (results.length > 0) {
                res.json(results[0]); // Trả về đối tượng sản phẩm đầu tiên (và duy nhất)
            } else {
                res.status(404).send('Không tìm thấy sản phẩm'); // Trả về 404 nếu không tìm thấy
            }
        }
    });
});

app.listen(port, () => {
  console.log(`Server đang chạy tại http://localhost:${port}`);
});

// Truy cập http://localhost:3000/api/products
