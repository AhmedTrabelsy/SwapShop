import { Component, Input, OnInit } from '@angular/core';
import { Product } from 'libs/products/src/lib/models/product';
import { ProductService } from '@swap-shop/products';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
})
export class ProductDetailsComponent implements OnInit {
  productId!: number;
  product: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit(): void {
    this.productService.getProduct(this.productId).subscribe(
      (product) => {
        this.product = [product]; // Wrap the single product in an array
      },
      (error) => {
        console.error('Error fetching product:', error);
      }
    );
  }
}
