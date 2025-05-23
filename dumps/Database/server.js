const express = require('express');
const mysql = require('mysql2');

const app = express();
const port = 3000;

const pool = mysql.createPool({
  host: 'gvdp4.h.filess.io',
  port: 61002,
  user: 'shopdb_lookwordno',
  password: 'duyen123456',
  database: 'shopdb_lookwordno',
  waitForConnections: true,
  connectionLimit: 5,     
  queueLimit: 0
});

function extractTableName(query) {
  const lower = query.toLowerCase();

  if (lower.startsWith('select')) {
    const match = query.match(/from\s+(\w+)/i);
    return match ? match[1] : null;
  }

  if (lower.startsWith('insert into')) {
    const match = query.match(/insert\s+into\s+(\w+)/i);
    return match ? match[1] : null;
  }

  if (lower.startsWith('delete from')) {
    const match = query.match(/delete\s+from\s+(\w+)/i);
    return match ? match[1] : null;
  }

  if (lower.startsWith('update')) {
    const match = query.match(/update\s+(\w+)/i);
    return match ? match[1] : null;
  }

  return null;
}
/////////////////////////////////////////////////
query = 
`SELECT ps.price
FROM ProductSizes ps
JOIN ProductVariants pv ON ps.product_variant_id = pv.variant_id
WHERE pv.product_id = 1 AND pv.color = 'Đen' AND ps.size = 'M'`;
table = extractTableName(query);
/////////////////////////////////////////////////

app.get(`/api/${table}`, (req, res) => {
  pool.query(query, (err, results) => {
    if (err) {
      console.error('Lỗi truy vấn:', err);
      res.status(500).send('Lỗi truy vấn CSDL');
    } else {
      console.log('Truy vấn thành công');
      res.json(results);
    }
  });
});

app.listen(port, () => {
  console.log(`http://localhost:${port}/api/${table}`);
});

process.on('SIGINT', () => {
  console.log('\nĐang đóng MySQL...');
  pool.end(err => {
    if (err) {
      console.error('Lỗi khi đóng pool:', err);
    } else {
      console.log('MySQL đã được đóng.');
    }
    process.exit();
  });
});

process.on('uncaughtException', (err) => {
  console.error('Lỗi không mong đợi:', err);
  pool.end(() => process.exit(1));
});
