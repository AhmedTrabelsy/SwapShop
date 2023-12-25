const express = require('express');
const { signup, login } = require('./controllers/authController');
const { getUserData, updateProfile,getUserCount } = require('./controllers/userController');
const app = express();
require('express-async-errors');

app.use(express.json({ limit: '10kb' }));

app.use((err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send('Something went wrong!');
});

app.post('/signup', signup);
app.post('/login', login);
app.get('/user', getUserData);
app.put('/update', updateProfile);
app.get('/getUsersCount', getUserCount);

module.exports = app;
