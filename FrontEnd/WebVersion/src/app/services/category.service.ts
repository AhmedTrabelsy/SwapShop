import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Category } from '../models/category';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  constructor(private http:HttpClient) { }

  getAll(){
    return this.http.get<Category[]>("http://34.199.239.78:8888/CATEGORY-SERVICE/categories");
  }

  getById(id:number){
    return this.http.get<Category>("http://34.199.239.78:8888/CATEGORY-SERVICE/categories/"+id);
  }

  createCategory(formData:FormData){
      return this.http.post("http://34.199.239.78:8888/CATEGORY-SERVICE/categories",formData);
  }

  delete(id:number){
    return this.http.delete("http://34.199.239.78:8888/CATEGORY-SERVICE/categories/"+id);
  }

  edit(id:number,formData:FormData){
    return this.http.put("http://34.199.239.78:8888/CATEGORY-SERVICE/categories/"+id,formData);
  }
}
