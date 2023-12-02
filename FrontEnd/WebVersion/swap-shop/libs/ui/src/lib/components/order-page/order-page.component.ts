import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FieldsetModule } from 'primeng/fieldset';
@Component({
	selector: 'swap-shop-order-page',
	standalone: true,
	imports: [
		CommonModule,
		FieldsetModule,
	],
	templateUrl: './order-page.component.html',
	styleUrls: ['./order-page.component.css']
})
export class OrderPageComponent {}
