import express, { ErrorRequestHandler } from 'express';
import multer from 'multer';
import { handleFileUpload, retrieveUploadedFile, retrieveUploadedFileByName } from './controllers/news-controller';
import path from 'path';
require('express-async-errors');
export const app = express();

app.use(express.json({ limit: '10kb' }));

const storage = multer.diskStorage({
    destination: (req, file, cb) => cb(null, path.join(__dirname, '/uploads/')),
    filename: (req, file, cb) => cb(null, file.originalname),
})

const upload = multer({storage});


const errorHandler: ErrorRequestHandler = (err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send('Something went wrong!');
};
app.use(errorHandler);

app.post('/upload', upload.single('image'), handleFileUpload);
app.get('/lastUpload', retrieveUploadedFile);
app.get('/uploads/:filename', retrieveUploadedFileByName);

module.exports = app;