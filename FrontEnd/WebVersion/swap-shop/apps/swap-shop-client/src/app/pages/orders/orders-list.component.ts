import { Order } from './../../../../../../libs/products/src/lib/models/order';
import { OrderService } from 'libs/products/src/lib/services/order.service';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';

@Component({
  selector: 'swap-shop-orders-list',
  templateUrl: './orders-list.component.html',
  styles: []
})
export class OrdersListComponent implements OnInit, OnDestroy{
  orders: Order[] = [];
  private ordersSubscription?: Subscription;
  constructor(private orderService: OrderService) { }
  ngOnInit(): void {
    this.orderService.getOrders().subscribe(orders => {
      console.log(orders);
      this.orders = orders;
    });
  }
  ngOnDestroy(): void {
    this.ordersSubscription?.unsubscribe();
  }

}
