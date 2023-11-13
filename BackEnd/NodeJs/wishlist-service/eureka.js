const Eureka = require('eureka-js-client').Eureka;

const EurekaClient = new Eureka({
	instance: {
		app: 'WISHLIST-SERVICE',
		hostName: 'localhost',
		ipAddr: '127.0.0.1',
		vipAddress: 'WISHLIST-SERVICE',
		port: {
			$: 8084,
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

module.exports = EurekaClient;
