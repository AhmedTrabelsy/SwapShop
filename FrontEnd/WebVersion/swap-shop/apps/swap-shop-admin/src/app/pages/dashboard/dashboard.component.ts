import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'swap-shop-dashboard',
  templateUrl: './dashboard.component.html',
  styles: ``
})
export class DashboardComponent implements OnInit, OnDestroy  {
  productsCount = 10;
  currentOrders = 10;
  registredUsers = 10;
  totalSales = 10;

  private productSubscription?: Subscription;
  // constructor(private productService: ProductService) { }

  ngOnInit(): void {
    // this.productSubscription = this.productService
    //   .getProducts().subscribe((products) => {
    //     this.productsCount = products.length;
    //   });
    console.log("api call not implemented");

  }

  ngOnDestroy(): void {
    if (this.productSubscription) {
      this.productSubscription.unsubscribe();
    }
  }
}
