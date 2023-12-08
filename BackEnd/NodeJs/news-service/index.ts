import express, { ErrorRequestHandler } from 'express';
import multer from 'multer';
import { handleFileUpload, retrieveUploadedFile } from './controllers/news-controller';
require('express-async-errors');
const app = express();

app.use(express.json({ limit: '10kb' }));

const storage = multer.diskStorage({
    destination: (req, file, cb) => cb(null, "./uploads/"),
    filename: (req, file, cb) => cb(null, file.originalname),
})

const upload = multer({storage});


const errorHandler: ErrorRequestHandler = (err, req, res, next) => {
    console.error(err.stack);
    res.status(500).send('Something went wrong!');
};
app.use(errorHandler);

app.post('/upload', upload.single('image'), handleFileUpload);
app.get('/uploads/:filename', retrieveUploadedFile);


// Start the server
const port: number = 3000;
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});

module.exports = app;
