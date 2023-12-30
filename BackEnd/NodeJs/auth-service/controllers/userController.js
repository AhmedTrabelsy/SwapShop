const axios = require('axios');
const { getAdminToken } = require('./authController');
const jwt = require('jsonwebtoken');
const axios = require('axios');

exports.getUserData = async (req, res) => {
	const token = req.headers.authorization;

	if (token == null) {
		return res.status(400).json({
			status: 'fail',
			message: 'token is missing',
		});
	}

	const response = await axios.get('http://34.199.239.78:8080/realms/SwapShop/protocol/openid-connect/userinfo', {
		headers: {
			Authorization: token,
		},
	});

	let username = response.data['preferred_username'];

	const adminToken = await getAdminToken();

	const response2 = await axios.get('http://34.199.239.78:8080/admin/realms/SwapShop/users?username=' + username, {
		headers: {
			Authorization: `Bearer ${adminToken}`,
		},
	});

	return res.status(200).json(response2.data[0]);
};

exports.updateProfile = async (req, res, next) => {
	const token = req.headers.authorization;

	if (token == null) {
		return res.status(400).json({
			status: 'fail',
			message: 'token is missing',
		});
	}

	let { firstName, lastName, phoneNumber, email } = req.body;

	if (firstName == null || lastName == null || phoneNumber == null || email == null) {
		return res.status(400).json({
			status: 'fail',
			message: 'some fields are missing',
		});
	}

	const response = await axios.get('http://34.199.239.78:8080/realms/SwapShop/protocol/openid-connect/userinfo', {
		headers: {
			Authorization: token,
		},
	});

	let id = response.data['sub'];

	const adminToken = await getAdminToken();

	await axios.put(
		`http://34.199.239.78:8080/admin/realms/swapShop/users/${id}`,
		{
			firstName: firstName,
			lastName: lastName,
			email: email,
			attributes: {
				phone_number: phoneNumber,
			},
		},
		{
			headers: {
				Authorization: `Bearer ${adminToken}`,
			},
		},
	);

	return res.status(200).json({
		status: 'success',
	});
};


exports.getUserCount = async (req, res) => {
    try {
        const adminToken = await getAdminToken();

        const response = await axios.get('http://34.199.239.78:8080/admin/realms/SwapShop/users/count', {
            headers: {
                Authorization: `Bearer ${adminToken}`,
            },
        });

        const userCount = response.data;

        return res.status(200).json({
            status: 'success',
            userCount: userCount,
        });
    } catch (error) {
        console.error('Error retrieving user count:', error.message);
        return res.status(500).json({
            status: 'error',
            message: 'Failed to retrieve user count',
        });
    }
};


exports.getUserRegistrationsByMonth = async (req, res) => {
    try {
        const adminToken = await getAdminToken();
        const response = await axios.get('http://34.199.239.78:8080/admin/realms/SwapShop/users', {
            headers: {
                Authorization: `Bearer ${adminToken}`,
            },
        });
        const users = response.data;
        const registrationsByMonth = {};

        users.forEach(user => {
            const registrationDate = new Date(user.createdTimestamp);
            const monthYear = `${registrationDate.getMonth() + 1}-${registrationDate.getFullYear()}`;

            if (!registrationsByMonth[monthYear]) {
                registrationsByMonth[monthYear] = 0;
            }

            registrationsByMonth[monthYear]++;
        });

        const table = [];
        const currentDate = new Date();
        for (let i = 0; i < 12; i++) {
            const monthYearKey = `${currentDate.getMonth() + 1}-${currentDate.getFullYear()}`;
            const monthName = currentDate.toLocaleString('default', { month: 'long' });
            const count = registrationsByMonth[monthYearKey] || 0;

            table.push({
                month: monthName,
                usersCount: count,
            });

            currentDate.setMonth(currentDate.getMonth() - 1); // Move to the previous month
        }

        return res.status(200).json({
            status: 'success',
            data: table.reverse(),
        });
    } catch (error) {
        console.error('Error retrieving user registrations by month:', error.message);
        return res.status(500).json({
            status: 'error',
            message: 'Failed to retrieve user registrations by month',
        });
    }
};

exports.getUserDetailsFromToken = async (req, res) => {
    try {
        const token = req.headers.authorization.split(' ')[1];

        const decoded = jwt.decode(token);

        if (!decoded || !decoded.sub) {
            return res.status(400).json({
                status: 'error',
                message: 'Invalid token or missing user ID',
            });
        }

        const userId = decoded.sub;
        const adminToken = await getAdminToken();

        return res.status(200).json({
            status: 'success',
            userId: userId,
        });
    } catch (error) {
        console.error('Error retrieving user Id from token:', error.message);
        return res.status(500).json({
            status: 'error',
            message: 'Failed to retrieve user Id from token',
        });
    }
};
