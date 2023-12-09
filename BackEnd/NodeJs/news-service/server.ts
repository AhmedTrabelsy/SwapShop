const app = require('./app');
const mongoose = require('mongoose');

require('dotenv').config();

const EurekaClient = require('./eureka');
EurekaClient.start();

const port = process.env.PORT || 3000;
const DB = process.env.DATABASE;

mongoose
    .connect(DB, {
        useNewUrlParser: true,
    })
    .then(() => {
        console.log('DB connection successful');
    });

// Start the server
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
