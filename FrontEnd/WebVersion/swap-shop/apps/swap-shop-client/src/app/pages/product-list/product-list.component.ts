import { Component, OnInit, OnDestroy } from '@angular/core';
import { product } from 'libs/products/src/lib/models/product';
import { ProductService } from 'libs/products/src/lib/services/product.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'swap-shop-product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit, OnDestroy {

  products: product[] = [];
  private productSubscription?: Subscription;
  constructor(private productService: ProductService) { }
  
  ngOnInit(): void {
    this.productSubscription = this.productService
      .getProducts().subscribe((products) => {
        this.products = products;
      });
  }
  ngOnDestroy(): void {
    if (this.productSubscription) {
      this.productSubscription.unsubscribe();
    }
  }

}
