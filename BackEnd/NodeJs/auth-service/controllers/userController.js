const axios = require('axios');
const { getAdminToken } = require('./authController');

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