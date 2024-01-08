import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { product } from '../../models/product';

@Component({
	selector: 'swap-shop-product-details',
	templateUrl: './product-details.component.html',
	styles: []
})
export class ProductDetailsComponent implements OnInit, OnDestroy{
	product: product | undefined;
	constructor(private productService: ProductService) {}
	ngOnInit(): void {
		throw new Error('Method not implemented.');
	}
	ngOnDestroy(): void {
		throw new Error('Method not implemented.');
	}
	
}
