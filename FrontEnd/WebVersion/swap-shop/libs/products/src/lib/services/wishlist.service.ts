import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { wishlist } from '../models/wishlist';
import { product } from '../models/product';

@Injectable({
  providedIn: 'root'
})
export class WishlistService {
  constructor(private http: HttpClient) { }

  userId: number = 1;

  getWishlist(): Observable<wishlist> {
    console.log("hello");
    return this.http.get<any>("http://34.199.239.78:8888/WISHLIST-SERVICE/1").pipe(
      map((data: any) => {
        console.log("data", data);
        const processedWishlist: wishlist = {
          userId: data.user_id,
          product: data.products.filter((product: any) => product !== null && product !== undefined)
        };
        return processedWishlist;
      })
    );
  }
  getProducts(): Observable<product[]> {
    return this.http.get<any>("http://34.199.239.78:8888/WISHLIST-SERVICE/1").pipe(
      map((data: any) => data.products.filter((product: any) => product !== null && product !== undefined))
    );
  }
  deleteProduct(productId: number): void {
    this.http.delete(`http://34.199.239.78:8888/WISHLIST-SERVICE/1/${productId}`).subscribe(
      () => {
        console.log("Deleted Product ID:", productId);
      },
      (error) => {
        console.error("Error deleting product:", error);
      }
    );
  }
  addToWishlist(productId: number): Observable<any> {
    const payload = {
      user_id: 1,
      product_id: productId
    };

    return this.http.post<any>("http://34.199.239.78:8888/WISHLIST-SERVICE/", payload);
  }  
}
