const EurekaClient = require('../eureka');
const WishList = require('../models/wishListModel');
const axios = require('axios');

exports.create = async (req, res, next) => {
	let { user_id, product_id } = req.body;

	if (user_id == null || product_id == null) {
		return res.status(400).json({
			status: 'fail',
			message: 'user_id and product_id are required',
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
};

exports.get = async (req, res, next) => {
	const user_id = req.params.user_id;

	const wishlist = await WishList.findOne({ user_id });

	let responseData = {};
	if (wishlist) {
		const productPromises = wishlist.products.map(async (product_id) => {
			try {
				const response = await axios.get(`${process.env.GATEWAY_URL}/PRODUCT-SERVICE/products/${product_id}`);
				return response.data; // Assuming the product data is in the 'data' property of the response
			} catch (error) {
				console.error(`Error fetching product ${product_id}:`, error.message);
				return null; // Or handle the error in a way that makes sense for your application
			}
		});

		const productData = await Promise.all(productPromises);

		responseData = {
			user_id,
			products: productData,
		};
	}

	return res.status(200).json(responseData);
};

exports.deleteByUserId = async (req, res, next) => {
	const user_id = req.params.user_id;

	await WishList.findOneAndDelete({ user_id });

	return res.status(204).json({
		status: 'success',
	});
};

exports.deleteProductFromUserWishList = async (req, res, next) => {
	const user_id = req.params.user_id;
	const product_id = req.params.product_id;

	const result = await WishList.updateOne({ user_id }, { $pull: { products: parseInt(product_id) } });

	if (result.nModified === 0) {
		return res.status(404).json({ message: 'Product not found in wishlist' });
	}

	return res.status(204).json({
		status: 'success',
	});
};
