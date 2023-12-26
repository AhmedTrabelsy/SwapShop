import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ProductService } from 'libs/products/src/lib/services/product.service';
import { OrderService } from 'libs/products/src/lib/services/order.service';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { product } from 'libs/products/src/lib/models/product';

@Component({
  selector: 'swap-shop-dashboard',
  templateUrl: './dashboard.component.html',
  styles: ``
})
export class DashboardComponent implements OnInit, OnDestroy  {
  constructor(private productsService: ProductService, private orderService: OrderService){}
  productsCount = 0;
  currentOrders = 10;
  registredUsers = 10;
  totalSales = 0;
  chartData: any;
  orderPricePerMounth = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0];
  products: product[] = [];
  endsubs$: Subject<unknown> = new Subject();

  private productSubscription?: Subscription;
  private orderSubscription?: Subscription;
  // constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.orderService
    .getOrdersPricePerMounth()
    .pipe(takeUntil(this.endsubs$))
    .subscribe((orderPricePerMounth) => {
      this.orderPricePerMounth = orderPricePerMounth;
      for (let i = 0; i < this.orderPricePerMounth.length; i++) {
        this.totalSales += this.orderPricePerMounth[i];
      }
      console.log(orderPricePerMounth);
    });

    this.orderService
    .getOrderCount()
    .pipe(takeUntil(this.endsubs$))
    .subscribe((count) => {
      this.currentOrders = count;
      console.log(count);
    });
    this.productsService
      .getLastUpdatedProducts()
      .pipe(takeUntil(this.endsubs$))
      .subscribe((products) => {
        this.products = products;
        console.log(products);
      });

    this.productsService
      .getProductCount().subscribe((count) => {
        this.productsCount = count;
        console.log(count);
      });
    // this._getData();
    this.chartData = {
      labels: ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October',
      'Nouvember','December'],
      datasets: [
        {
          label: 'Scale Values',
          data: [],
          backgroundColor: 'rgba(54, 162, 235, 0.5)',
          borderColor: 'rgba(54, 162, 235, 1)',
          borderWidth: 1
        }
      ]
    };
    this._getData().pipe(takeUntil(this.endsubs$)).subscribe(
      (data: number[]) => {
        this.chartData.datasets[0].data = data;
        console.log(data);
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
    console.log("api call not implemented");
  }
  private _getData(): Observable<number[]> {
    return this.productsService.getNumberProductsInMounth();
  }
  // private _getData(){
  //   this.productsService
  //     .getNumberProductsInMounth()
  //     .subscribe((data) => {
  //       this.chartData.datasets[0].data = data;
  //       console.log(data);
  //     });
  // }
  ngOnDestroy(): void {
    if (this.productSubscription) {
      this.productSubscription.unsubscribe();
    }
    this.endsubs$.complete();
  }
}
