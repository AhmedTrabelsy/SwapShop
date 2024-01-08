import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class OrderService {
  BASE_URL_ORDER_SERVICE = "http://34.199.239.78:8888/ORDER-SERVICE/"
  constructor(private http: HttpClient) {
  }
  getOrders(): Observable<never[]> {
    return this.http.get<never>(this.BASE_URL_ORDER_SERVICE + "orders");
  }
  getOrderCount(): Observable<number> {
    return this.http.get<number>(this.BASE_URL_ORDER_SERVICE + "orderCount");
  }
  getOrdersPricePerMounth(): Observable<never[]> {
    return this.http.get<never[]>(this.BASE_URL_ORDER_SERVICE + "orderPerMonth");
  }
  getLastUpdatedOrders(): Observable<never[]> {
    return this.http.get<never>(this.BASE_URL_ORDER_SERVICE + "getLastOrders");
  }
  deleteOrder(orderId: string): Observable<unknown> {
    return this.http.delete<unknown>(this.BASE_URL_ORDER_SERVICE + "orders/" + orderId);
  }
}
