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
