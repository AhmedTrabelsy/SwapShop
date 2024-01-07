const axios = require('axios');

exports.send = async (req, res) => {
	let { title, body } = req.body;

	if (title == null || body == null) {
		return res.status(400).json({
			status: 'fail',
			message: 'some fields are missing',
		});
	}
	try {
		const response = await axios.post(
			'https://onesignal.com/api/v1/notifications',
			{
				app_id: 'bc00aad2-c39c-4ffa-a2c3-a33480f770fc',
				headings: {
					en: title,
				},
				contents: {
					en: body,
				},
				included_segments: ['Total Subscriptions'],
			},
			{
				headers: {
					'Content-Type': 'application/json',
					Authorization: 'Bearer NDQ2NjYyZjctNzlhYS00NzUxLTg2NDgtNTZmYjNjYTM4YmQ1',
				},
			},
		);

		return res.status(201).json({
			status: 'success',
			message: 'notification sent successfully',
			data: response.data,
		});
	} catch (err) {
		return res.status(400).json({
			status: 'fail',
			message: err.response.data.errorMessage,
		});
	}
};
