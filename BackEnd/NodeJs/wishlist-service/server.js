const mongoose = require('mongoose');

const app = require('./app');
const EurekaClient = require('./eureka');

const port = process.env.PORT || 3000;

const DB = process.env.DATABASE;

EurekaClient.start();

mongoose
	.connect(DB, {
		useNewUrlParser: true,
	})
	.then((con) => {
		console.log('DB connection successful');
	});

const server = app.listen(port, () => console.log(`Server is listening on port ${port}`));
