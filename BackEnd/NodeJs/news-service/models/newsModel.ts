const _mongoose = require('mongoose');

const newsSchema = _mongoose.Schema({
    path: {
        type: String,
        required: [true],
    },
});

const news = _mongoose.model('news', newsSchema);

module.exports = news;