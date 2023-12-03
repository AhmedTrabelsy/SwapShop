import { Component, OnInit,Injectable } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FieldsetModule } from 'primeng/fieldset';
import { HttpClient } from '@angular/common/http';
import { HttpClientModule } from '@angular/common/http';
import { Observable } from 'rxjs';
@Component({
	selector: 'swap-shop-order-page',
	standalone: true,
	imports: [
		CommonModule,
		FieldsetModule,
		HttpClientModule
	],
	templateUrl: './order-page.component.html',
	styleUrls: ['./order-page.component.css']
})
@Injectable({
	providedIn: 'root'
  })
export class OrderPageComponent implements OnInit{
	private apiUrl = 'http://34.199.239.78:8888/PRODUCT-SERVICE/products/21';
	public data:any={

	};
	constructor(private http: HttpClient) { }
	ngOnInit(): void {
		// Call the method to fetch data when the component is initialized
	this.http.get<any>(this.apiUrl).subscribe((res) => {
        this.data = res;
        console.log(this.data);
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
	
	  }

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
