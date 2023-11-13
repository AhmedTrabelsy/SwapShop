const express = require('express');
const { create, get, deleteByUserId, deleteProductFromUserWishList } = require('./controllers/wishListController');
const app = express();

app.use(express.json({ limit: '10kb' }));

app.get('/:user_id', get);
app.delete('/:user_id', deleteByUserId);
app.delete('/:user_id/:product_id', deleteProductFromUserWishList);
app.post('/', create);

module.exports = app;
