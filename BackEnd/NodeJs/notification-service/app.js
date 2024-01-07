const express = require('express');
const { send } = require('./controllers/notificationController');
const app = express();
require('express-async-errors');

app.use(express.json({ limit: '10kb' }));

app.use((err, req, res, next) => {
	console.error(err.stack);
	res.status(500).send('Something went wrong!');
});

app.post('/', send);

module.exports = app;
