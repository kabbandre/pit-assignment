const mongoose = require('mongoose');

const ImageSchema = mongoose.Schema({
    title: String,
    width: Number,
    filterId: Number,
    image: String,
    createdAt: String,
    processedImage: String
});

module.exports = mongoose.model("Images", ImageSchema);
