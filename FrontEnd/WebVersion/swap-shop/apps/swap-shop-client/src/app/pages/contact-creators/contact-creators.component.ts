import { Component } from '@angular/core';

@Component({
	selector: 'swap-shop-contact-creators',
	templateUrl: './contact-creators.component.html',
	styles: []
})
export class ContactCreatorsComponent {
	constructor() {}
	users: any[] = [
		{
			name: 'Abdel hakim barbaria',
			email: 'hakimbarbaria589@gmail.com',
			description: 'I am a creative full stack developer',
			linkedIn: 'https://www.linkedin.com/in/hakim-barbaria-7574b3232/',
		},
		{
			name: 'Dridi haythem',
			email: 'haythem@gmail.com',
			description: 'hello',
			linkedIn: 'https://www.linkedin.com/in/haythem-dridi-1b1b3a1b3/',
		},
	];
}
