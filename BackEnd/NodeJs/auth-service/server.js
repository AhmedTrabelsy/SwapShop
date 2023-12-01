const app = require('./app');
const EurekaClient = require('./eureka');

const port = process.env.PORT || 3000;

EurekaClient.start();

const server = app.listen(port, () => console.log(`Server is listening on port ${port}`));
