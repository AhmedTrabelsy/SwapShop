import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { wishlist } from '../models/wishlist';
import { product } from '../models/product';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  constructor(private http: HttpClient, private router: Router) { }
  getToken(): string {
    return sessionStorage.getItem('access_token') || '';
  }
  isAuthenticated() {
    return of(this.getToken() !== '');
  }
  userId: number = 1;

  getWishlist(): Observable<wishlist> {
    console.log("hello");
    return this.http.get<any>(`http://34.199.239.78:8888/WISHLIST-SERVICE/${sessionStorage.getItem('userId')}`).pipe(
      map((data: any) => {
        console.log("data", data);
        const processedWishlist: wishlist = {
          userId: data.user_id,
          product: data.products.filter((product: any) => product !== null && product !== undefined)
        };
        const headers = new HttpHeaders({
          'Authorization': `Bearer ${this.getToken()}`
        });
        return processedWishlist;
      })
    );
  }
  getProducts(): Observable<product[]> {
    return this.http.get<any>(`http://34.199.239.78:8888/WISHLIST-SERVICE/${sessionStorage.getItem('userId')}`).pipe(
      map((data: any) => data.products.filter((product: any) => product !== null && product !== undefined))
    );
  }
  deleteProduct(productId: number): Promise<void> {
    if ( this.isAuthenticated()){
    return new Promise((resolve, reject) => {
      this.http.delete(`http://34.199.239.78:8888/WISHLIST-SERVICE/${sessionStorage.getItem('userId')}/${productId}`).subscribe(
        () => {
          console.log("Deleted Product ID:", productId);
          resolve();
        },
        (error) => {
          console.error("Error deleting product:", error);
          reject(error);
        }
      );
    });
  }else {
    this.router.navigate(['/login']);
    return Promise.reject('User not authenticated');
  }
  }
  
  addToWishlist(productId: number): Observable<any> {
    if ( this.isAuthenticated()){
    const payload = {
      user_id: sessionStorage.getItem('userId'),
      product_id: productId
    };

    return this.http.post<any>("http://34.199.239.78:8888/WISHLIST-SERVICE/", payload);
  }  
  else {
    this.router.navigate(['/login']);
    return of('User not authenticated');
  }
  }
}
