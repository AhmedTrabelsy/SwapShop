import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { ProductService } from 'libs/products/src/lib/services/product.service';
import { OrderService } from 'libs/products/src/lib/services/order.service';
import { Observable } from 'rxjs';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
import { product } from 'libs/products/src/lib/models/product';
import { Order } from 'libs/products/src/lib/models/order';
import { AuthentificationService } from 'libs/products/src/lib/services/authentification.service';

@Component({
  selector: 'swap-shop-dashboard',
  templateUrl: './dashboard.component.html',
  styles: ``
})
export class DashboardComponent implements OnInit, OnDestroy  {
  constructor(private productsService: ProductService, private orderService: OrderService, private authService: AuthentificationService){}
  productsCount = 0;
  currentOrders = 10;
  registredUsers = 10;
  totalSales = 0;
  chartData: any;
  chartDataForUsers: any;
  orderPricePerMounth = [0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0];
  usersPerMounth = [0,0,0,0,0,0,0,0,0,0,0,0];
  products: product[] = [];
  orders: Order[] = [];
  endsubs$: Subject<unknown> = new Subject();
  private productSubscription?: Subscription;
  private orderSubscription?: Subscription;
  private authSubscription?: Subscription;
  // constructor(private productService: ProductService) { }

  ngOnInit(): void {
    const accessToken = sessionStorage.getItem('access_token');
    if (accessToken) {
    this.authService.getUserCount(accessToken).subscribe(
      (response) => {
        console.log('User count:', response.userCount);
        this.registredUsers = response.userCount;
      },
      (error) => {
        console.error('Failed to get user count:', error.message);
      }
    );
    this.authService.getUsersPerMounth(accessToken).subscribe(
      (response) => {
        console.log('User count:', response.data);
        response.data.forEach((monthData: { month: string; usersCount: number; }) => {
          const monthIndex = [
            'January', 'February', 'March', 'April', 'May', 'June',
            'July', 'August', 'September', 'October', 'November', 'December'
          ].indexOf(monthData.month);
    
          if (monthIndex !== -1) {
            this.usersPerMounth[monthIndex] = monthData.usersCount;
            console.log(this.usersPerMounth)
          }
        });
        this.chartDataForUsers = {
          labels: ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October',
          'Nouvember','December'],
          datasets: [
            {
              label: 'Total Scales',
              data: this.usersPerMounth,
              backgroundColor: 'rgba(54, 162, 235, 0.5)',
              borderColor: 'rgba(54, 162, 235, 1)',
              borderWidth: 1
            }
          ]
        };
      },
      (error) => {
        console.error('Failed to get user count:', error.message);
      }
    );
    }
    this.orderService
      .getLastUpdatedOrders()
      .pipe(takeUntil(this.endsubs$))
      .subscribe((orders) => {
        this.orders = orders;
        console.log(orders);
      });

    this.orderService
    .getOrdersPricePerMounth()
    .pipe(takeUntil(this.endsubs$))
    .subscribe((orderPricePerMounth) => {
      this.orderPricePerMounth = orderPricePerMounth;
      this.chartData = {
        labels: ['January', 'February', 'March', 'April', 'May', 'June','July','August','September','October',
        'Nouvember','December'],
        datasets: [
          {
            label: 'Total Scales',
            data: orderPricePerMounth,
            backgroundColor: 'rgba(54, 162, 235, 0.5)',
            borderColor: 'rgba(54, 162, 235, 1)',
            borderWidth: 1
          }
        ]
      };
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
  }
    /*this._getData().pipe(takeUntil(this.endsubs$)).subscribe(
      (data: number[]) => {
        this.chartData.datasets[0].data = data;
        console.log(data);
      },
      (error) => {
        console.error('Error fetching data:', error);
      }
    );
    console.log("api call not implemented");
  }*/
 /* private _getData(): Observable<number[]> {
    return this.productsService.getNumberProductsInMounth();
  }*/
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
