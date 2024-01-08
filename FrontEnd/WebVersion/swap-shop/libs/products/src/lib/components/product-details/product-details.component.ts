import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { product } from '../../models/product';
import { ActivatedRoute } from '@angular/router';

@Component({
	selector: 'swap-shop-product-details',
	templateUrl: './product-details.component.html',
	styleUrl: './product-details.scss'
})
export class ProductDetailsComponent implements OnInit, OnDestroy{
	product?: product;
	productId?: number;

	constructor(private productService: ProductService, private route: ActivatedRoute) {}
	ngOnInit(): void {
		this.route.params.subscribe(params => {
			this.productId = +params['id'];
		});
		if (this.productId) {
			this.productService.getProduct(this.productId).subscribe(product => {
				this.product = product;
				console.log(this.product);
			});
		}
	}
	ngOnDestroy(): void {
		throw new Error('Method not implemented.');
	}

}
