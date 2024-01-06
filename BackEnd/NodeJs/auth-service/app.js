const express = require('express');
const { signup, login } = require('./controllers/authController');
const {
	getUserData,
	updateProfile,
	getUserCount,
	getUserRegistrationsByMonth,
	getUserIdFromToken,
	getAllUsers,
	deleteUser,
	updateUser,
	getUser,
	getUserDataFromId,
} = require('./controllers/userController');
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
app.get('/getUsersPerMonth', getUserRegistrationsByMonth);
app.get('/getUserId', getUserIdFromToken);
app.get('/getUserDetailsFromId', getUserDataFromId);

app.get('/users', getAllUsers);
app.delete('/users/:id', deleteUser);
app.put('/users/:id', updateUser);
app.get('/users/:id', getUser);

module.exports = app;
