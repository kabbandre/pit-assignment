const express = require('express');
const router = express.Router();
const Image = require('../models/Image.js');

router.get('/', (req, res, next) => {
  Image.find({}).then((images) => {
    console.log('Images received')
    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    res.json(images)
  }).catch((e) => {
    next(e)
  })
});

router.get('/:imageId', (req, res, next) => {
  Image.findById(req.params.imageId).then((image) => {
    console.log('Image received')
    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    res.json(image)
  }).catch((e) => {
    next(e)
  })
})

router.post('/save', (req, res, next) => {
  Image.create(req.body).then((image) => {
    console.log('Image added')
    res.statusCode = 200
    res.setHeader('Content-Type', 'application/json')
    res.json(image)
  }).catch((e) => {
    next(e)
  })
})

module.exports = router;
