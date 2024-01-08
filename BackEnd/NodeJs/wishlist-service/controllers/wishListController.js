const EurekaClient = require('../eureka');
const WishList = require('../models/wishListModel');
const axios = require('axios');

exports.create = async (req, res, next) => {
    try {
        const { user_id, product_id } = req.body;
        const token = req.headers.authorization;

        if (!token) {
            return res.status(401).json({
                status: 'fail',
                message: 'Token is missing',
            });
        }

        if (!user_id || !product_id) {
            return res.status(400).json({
                status: 'fail',
                message: 'user_id and product_id are required',
            });
        }

        const headers = {
            Authorization: `Bearer ${token}`,
        };

        const userIdResponse = await axios.get(`${process.env.GATEWAY_URL}/AUTH-SERVICE/getUserId`, {
            headers,
        });

        const userId = userIdResponse.data.userId;

        if (user_id !== userId) {
            return res.status(403).json({
                status: 'fail',
                message: 'Access forbidden. User does not have permission to access this resource.',
            });
        }

        const existingWishList = await WishList.findOne({ user_id });

        if (existingWishList) {
            if (!existingWishList.products.includes(product_id)) {
                existingWishList.products.push(product_id);
                await existingWishList.save();
            }
        } else {
            const newWishList = new WishList({
                user_id,
                products: [product_id],
            });
            await newWishList.save();
        }

        return res.status(201).json({
            status: 'success',
        });
    } catch (error) {
        console.error('Error:', error);
        return res.status(500).json({
            status: 'error',
            message: 'An error occurred while processing the request',
        });
    }
};


exports.get = async (req, res, next) => {
    try {
        const user_id = req.params.userId;
        const token = req.headers.authorization;

        if (!token) {
            return res.status(401).json({
                status: 'fail',
                message: 'Token is missing',
            });
        }

        if (!user_id) {
            return res.status(400).json({
                status: 'fail',
                message: 'Invalid user_id',
            });
        }

        const headers = {
            Authorization: `Bearer ${token}`,
        };
        const userIdResponse = await axios.get(`${process.env.GATEWAY_URL}/AUTH-SERVICE/getUserId`, {
            headers,
        });
        console.log(userIdResponse);
        const userId = userIdResponse.data.userId;

        if (user_id !== userId) {
            return res.status(403).json({
                status: 'fail',
                message: 'Access forbidden. User does not have permission to access this resource.',
            });
        }

        const wishlist = await WishList.findOne({ user_id });

        let responseData = {};

        if (wishlist) {
            const productPromises = wishlist.products.map(async (product_id) => {
                try {
                    const response = await axios.get(`${process.env.GATEWAY_URL}/PRODUCT-SERVICE/products/${product_id}`);
                    return response.data;
                } catch (error) {
                    console.error(`Error fetching product ${product_id}:`, error.message);
                    return null;
                }
            });

            const productData = await Promise.all(productPromises);

            responseData = {
                user_id,
                products: productData,
            };
        }

        return res.status(200).json(responseData);
    } catch (error) {
        console.error('Error:', error);
        return res.status(500).json({
            status: 'error',
            message: 'An error occurred while processing the request',
        });
    }
};


exports.deleteByUserId = async (req, res, next) => {
    try {
        const user_id = req.params.user_id;
        const token = req.headers.authorization;
        if (!token) {
            return res.status(401).json({
                status: 'fail',
                message: 'Token is missing',
            });
        }
        const isAuthenticated = await validateTokenAndGetUser(token);

        if (!isAuthenticated) {
            return res.status(401).json({
                status: 'fail',
                message: 'Invalid token',
            });
        }
        if (!user_id) {
            return res.status(400).json({
                status: 'fail',
                message: 'Invalid user_id',
            });
        }
        if (user_id !== isAuthenticated.user_id) {
            return res.status(403).json({
                status: 'fail',
                message: 'Access forbidden. User does not have permission to perform this action.',
            });
        }
        await WishList.findOneAndDelete({ user_id });

        return res.status(204).json({
            status: 'success',
        });
    } catch (error) {
        console.error('Error:', error);
        return res.status(500).json({
            status: 'error',
            message: 'An error occurred while processing the request',
        });
    }
};

exports.deleteProductFromUserWishList = async (req, res, next) => {
    try {
        const user_id = req.params.user_id;
        const product_id = req.params.product_id;
        const token = req.headers.authorization;
        if (!token) {
            return res.status(401).json({
                status: 'fail',
                message: 'Token is missing',
            });
        }
        const isAuthenticated = await validateTokenAndGetUser(token);

        if (!isAuthenticated) {
            return res.status(401).json({
                status: 'fail',
                message: 'Invalid token',
            });
        }
        if (!user_id || !product_id) {
            return res.status(400).json({
                status: 'fail',
                message: 'Invalid user_id or product_id',
            });
        }
        if (user_id !== isAuthenticated.user_id) {
            return res.status(403).json({
                status: 'fail',
                message: 'Access forbidden. User does not have permission to perform this action.',
            });
        }
        const result = await WishList.updateOne({ user_id }, { $pull: { products: parseInt(product_id) } });

        if (result.nModified === 0) {
            return res.status(404).json({ message: 'Product not found in wishlist' });
        }

        return res.status(204).json({
            status: 'success',
        });
    } catch (error) {
        console.error('Error:', error);
        return res.status(500).json({
            status: 'error',
            message: 'An error occurred while processing the request',
        });
    }
};

