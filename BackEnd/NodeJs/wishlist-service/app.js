const express = require('express');
const { create, get, deleteByUserId, deleteProductFromUserWishList } = require('./controllers/wishListController');
const app = express();
require('express-async-errors');

app.use(express.json({ limit: '10kb' }));

app.use((err, req, res, next) => {
	console.error(err.stack);
	res.status(500).send('Something went wrong!');
});

app.get('/:user_id', get);
app.delete('/:user_id', deleteByUserId);
app.delete('/:user_id/:product_id', deleteProductFromUserWishList);
app.post('/', create);

module.exports = app;
