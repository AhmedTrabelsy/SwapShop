import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { NxWelcomeComponent } from './nx-welcome.component';

@Component({
	standalone: true,
	imports: [NxWelcomeComponent, RouterModule],
	selector: 'swap-shop-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.css']
})
export class AppComponent {
	title = 'swap-shop-order-purshase';
}
