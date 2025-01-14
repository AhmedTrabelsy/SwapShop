const Eureka = require('eureka-js-client').Eureka;

const createEurekaClient = () => {
	return new Eureka({
		instance: {
			app: 'NEWS-SERVICE',
			hostName: 'news',
			ipAddr: '127.0.0.1',
			vipAddress: 'NEWS-SERVICE',
			port: {
				$: 8086,
				'@enabled': 'true',
			},
			dataCenterInfo: {
				'@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
				name: 'MyOwn',
			},
		},
		eureka: {
			host: process.env.EUREKA_HOST || 'localhost',
			port: 8761,
		},
	});
};

const connectToEureka = (eurekaClient: typeof Eureka, maxRetries: number, retryInterval: number) => {
	let retries = 0;

	const tryConnect = () => {
		eurekaClient.start((error: Error) => {
			if (error) {
				console.error(`Failed to connect to Eureka server. Retrying in ${retryInterval / 1000} seconds...`);
				retries++;

				if (retries < maxRetries || maxRetries === -1) {
					setTimeout(tryConnect, retryInterval);
				} else {
					console.error(`Max retries reached. Unable to connect to Eureka server.`);
				}
			} else {
				console.log('Connected to Eureka server.');
			}
		});
	};

	tryConnect();
};

const maxRetries = -1;
const retryInterval = 5000;

const eurekaClient = createEurekaClient();
connectToEureka(eurekaClient, maxRetries, retryInterval);

module.exports = eurekaClient;
