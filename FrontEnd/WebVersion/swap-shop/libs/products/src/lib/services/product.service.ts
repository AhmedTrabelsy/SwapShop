import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { product } from '../models/product';


@Injectable({
  providedIn: 'root'
})
export class ProductService {

  constructor(private http: HttpClient) {}

  getProducts(): Observable<never> {
    return this.http.get<never>("http://34.199.239.78:8888/PRODUCT-SERVICE/products");
  }

  createProduct(productData: FormData): Observable<product> {
    return this.http.post<product>("http://34.199.239.78:8888/PRODUCT-SERVICE/products", productData);
  }

  getProduct(productId: string): Observable<product> {
    return this.http.get<product>(`${"http://34.199.239.78:8888/PRODUCT-SERVICE/products"}/${productId}`);
  }

  updateProduct(productData: FormData, productid: string): Observable<product> {
    return this.http.put<product>(`${"http://34.199.239.78:8888/PRODUCT-SERVICE/products"}/${productid}`, productData);
  }

  deleteProduct(productId: string): Observable<unknown> {
    return this.http.delete<unknown>(`${"http://34.199.239.78:8888/PRODUCT-SERVICE/products"}/${productId}`);
  }
}
