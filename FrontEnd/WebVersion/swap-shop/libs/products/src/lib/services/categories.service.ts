import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category';
import { Observable } from 'rxjs';
// import { environment } from '@env';
@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  categoriesApiUrl = "http://34.199.239.78:8888" + '/CATEGORY-SERVICE/categories';
  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(this.categoriesApiUrl);
  }

  getCategoryById(id: string): Observable<Category> {
    return this.http.get<Category>(`${this.categoriesApiUrl}/${id}`);
  }

  createCategory(formData: FormData) {
    return this.http.post(this.categoriesApiUrl, formData);
  }

  editCategoryById(id: string, formData: FormData) {
    return this.http.put(`${this.categoriesApiUrl}/${id}`, formData);
  }

  deleteCategoryById(id: string) {
    return this.http.delete(`${this.categoriesApiUrl}/${id}`);
  }

}
