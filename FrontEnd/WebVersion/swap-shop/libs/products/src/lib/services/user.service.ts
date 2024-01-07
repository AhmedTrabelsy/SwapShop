import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { user } from '../models/user';


@Injectable({
  providedIn: 'root'
})
export class UserService {
  categoriesApiUrl = "http://34.199.239.78:8888" + '/AUTH-SERVICE/users';

  constructor(private http: HttpClient) {}

  getAll(): Observable<user[]> {
    return this.http.get<user[]>(this.categoriesApiUrl);
  }


  getById(id:string): Observable<user> {
    return this.http.get<user>(this.categoriesApiUrl+'/'+id);
  }


  delete(id:string): Observable<unknown> {
    return this.http.delete<unknown>(this.categoriesApiUrl+'/'+id);
  }
}
