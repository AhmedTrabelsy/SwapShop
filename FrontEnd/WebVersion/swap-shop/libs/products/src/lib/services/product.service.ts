import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { product } from '../models/product';


@Injectable({
  providedIn: 'root'
})
export class ProductService {
  categoriesApiUrl = "http://34.199.239.78:8888" + '/PRODUCT-SERVICE/products';

  constructor(private http: HttpClient) {}
  getLastUpdatedProducts(): Observable<never[]> {
    return this.http.get<never>("http://34.199.239.78:8888" + '/PRODUCT-SERVICE/lastUpdatedProduct');
  }
  getNumberProductsInMounth(): Observable<number[]> {
    return this.http.get<never>("http://34.199.239.78:8888" + '/PRODUCT-SERVICE/countProductInMounths');
  }
  getProductCount(): Observable<number> {
    return this.http.get<number>("http://34.199.239.78:8888" + '/PRODUCT-SERVICE/productCount');
  }
  getProducts(): Observable<never[]> {
    return this.http.get<never>(this.categoriesApiUrl);
  }
  createProduct(productData: FormData): Observable<product> {
    return this.http.post<product>(this.categoriesApiUrl, productData);
  }

  getProduct(productId: number): Observable<product> {
    return this.http.get<product>(`${this.categoriesApiUrl}/${productId}`);
  }

  updateProduct(productid: number,productData: FormData): Observable<product> {
    return this.http.put<product>(`${this.categoriesApiUrl}/${productid}`, productData);
  }

  deleteProduct(productId: string): Observable<unknown> {
    return this.http.delete<unknown>(`${this.categoriesApiUrl}/${productId}`);
  }
}
