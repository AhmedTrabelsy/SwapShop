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

	return res.status(200).json(response.data);
};
