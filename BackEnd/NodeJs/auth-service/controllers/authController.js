const EurekaClient = require('../eureka');
const axios = require('axios');

exports.getAdminToken = async () => {
    const response = await axios.post(
        'http://34.199.239.78:8080/realms/master/protocol/openid-connect/token',
        {
            username: 'admin',
            password: 'dsi31',
            grant_type: 'password',
            client_id: 'admin-cli',
        },
        {
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
        },
    );

    return response.data.access_token;
};

exports.signup = async (req, res, next) => {
    let { username, firstName, lastName, phoneNumber, email, password } = req.body;

    if (
        username == null ||
        firstName == null ||
        lastName == null ||
        phoneNumber == null ||
        email == null ||
        password == null
    ) {
        return res.status(400).json({
            status: 'fail',
            message: 'some fields are missing',
        });
    }

    try {
        const token = await this.getAdminToken();

        const response = await axios.post(
            'http://34.199.239.78:8080/admin/realms/SwapShop/users',
            {
                username: username,
                firstName: firstName,
                lastName: lastName,
                email: email,
                enabled: true,
                attributes: {
                    phone_number: phoneNumber,
                },
                credentials: [
                    {
                        type: 'password',
                        value: password,
                        temporary: false,
                    },
                ],
            },
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
            },
        );

        if (response.status === 201) {
            sendEmail(email, `${firstName} ${lastName}`);
        }

        return res.status(201).json({
            status: 'success',
            message: 'user created successfully',
            data: response.data,
        });
    } catch (err) {
        return res.status(400).json({
            status: 'fail',
            message: err.response.data.errorMessage,
        });
    }
};

exports.login = async (req, res, next) => {
    let { username, password } = req.body;

    if (username == null || password == null) {
        return res.status(400).json({
            status: 'fail',
            message: 'some fields are missing',
        });
    }

    try {
        const response = await axios.post(
            'http://34.199.239.78:8080/realms/SwapShop/protocol/openid-connect/token',
            {
                username: username,
                password: password,
                grant_type: 'password',
                client_id: 'app',
                scope: 'openid',
            },
            {
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
            },
        );

        return res.status(200).json(response.data);
    } catch (err) {
        return res.status(400).json({
            status: 'fail',
            message: err,
        });
    }
};
