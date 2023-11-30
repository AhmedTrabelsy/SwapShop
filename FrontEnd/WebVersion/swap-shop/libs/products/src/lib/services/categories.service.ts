import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>("http://34.199.239.78:8888/CATEGORY-SERVICE/categories");
  }

  getCategoryById(id: number): Observable<Category> {
    return this.http.get<Category>("http://34.199.239.78:8888/CATEGORY-SERVICE/categories/" + id);
  }

  createCategory(category: Category) {
    return this.http.post("http://34.199.239.78:8888/CATEGORY-SERVICE/categories", category);
  }

  editCategoryById(id: number, category: Category) {
    return this.http.put("http://34.199.239.78:8888/CATEGORY-SERVICE/categories/" + id, category);
  }

  deleteCategoryById(id: number) {
    return this.http.delete("http://34.199.239.78:8888/CATEGORY-SERVICE/categories/" + id);
  }

}
