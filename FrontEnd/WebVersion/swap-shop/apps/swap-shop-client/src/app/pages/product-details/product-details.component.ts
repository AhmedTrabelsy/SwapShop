import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Product } from 'libs/products/src/lib/models/product';
import { ProductService } from '@swap-shop/products';

@Component({
  selector: 'app-product-details',
  templateUrl: './product-details.component.html',
})
export class ProductDetailsComponent implements OnInit {
  productData: Product | null = null;

  constructor(private activeRoute: ActivatedRoute, private product: ProductService) { }

  ngOnInit(): void {
    const productIdString = this.activeRoute.snapshot.paramMap.get('productId');
    console.warn(productIdString);

    if (productIdString) {
      const productId: number = parseInt(productIdString, 10);

      if (!isNaN(productId)) {
        this.product.getProduct(productId).subscribe((result) => {
          this.productData = result;
          console.warn(result)
        });
      } else {
        console.error('error for feching product-  productId:', productIdString);
      }
    }
  }
}
