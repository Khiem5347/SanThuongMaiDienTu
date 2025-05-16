const express = require('express');
const mysql = require('mysql2');

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


app.get('/', (req, res) => {
  res.send('Chào mừng đến với API ShopDB!');
});


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

app.listen(port, () => {
  console.log(`Server đang chạy tại http://localhost:${port}`);
});

// Truy cập http://localhost:3000/api/products
