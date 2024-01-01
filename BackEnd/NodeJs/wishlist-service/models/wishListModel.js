const mongoose = require('mongoose');

const wishListSchema = mongoose.Schema({
	user_id: {
		type: String,
		required: [true],
	},
	products: {
		type: [],
		required: [true],
	},
});

const WishList = mongoose.model('WishList', wishListSchema);

module.exports = WishList;
