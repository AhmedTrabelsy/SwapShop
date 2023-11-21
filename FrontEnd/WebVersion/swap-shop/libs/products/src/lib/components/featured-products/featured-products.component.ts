import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ProductService } from '../../services/product.service';
import { product } from '../../models/product';


@Component({
  selector: 'swap-shop-featured-products',
  templateUrl: './featured-products.component.html',
})
export class FeaturedProductsComponent  implements OnInit, OnDestroy  {
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
