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

  chartData = {
    labels: ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October',
    'Nouvember','December'],
    datasets: [
      {
        label: 'Scale Values',
        data: [50, 80, 60, 90, 55, 75, 102, 50, 40, 96, 50, 80],
        backgroundColor: 'rgba(54, 162, 235, 0.5)',
        borderColor: 'rgba(54, 162, 235, 1)',
        borderWidth: 1
      }
    ]
  };

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
