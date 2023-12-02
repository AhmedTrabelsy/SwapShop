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
export class OrderPageComponent {
	formatCurrency(price?: number): string {
		if (price) {
		  if ((price | 0) < price) {
			const formattedPrice = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'TND' }).format(price);
			return formattedPrice.replace(',', ' ');
		  }
		  const formattedPrice = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'TND', maximumFractionDigits: 0 }).format(price);
		  return formattedPrice.replace(',', ' ');
		}
		return 'Undefined';
	  }
}
