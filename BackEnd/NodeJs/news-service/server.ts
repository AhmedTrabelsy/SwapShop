const app = require('./app');

const EurekaClient = require('./eureka');
EurekaClient.start();

// Start the server
const port = process.env.PORT || 3000;
app.listen(port, () => {
    console.log(`Server is running on http://localhost:${port}`);
});
