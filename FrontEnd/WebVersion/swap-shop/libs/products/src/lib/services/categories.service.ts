import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Category } from '../models/category';
import { Observable, map, of } from 'rxjs';
import { Router } from '@angular/router';
// import { environment } from '@env';
import { AuthentificationService } from './authentification.service';
@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  categoriesApiUrl = "http://34.199.239.78:8888" + '/CATEGORY-SERVICE/categories';
  constructor(private http: HttpClient, private router: Router, private authService: AuthentificationService) { }
  getToken(): string {
    return sessionStorage.getItem('access_token') || '';
  }
  isAuthenticated() {
    return of(this.getToken() !== '');
  }

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

  deleteCategoryById(id: string): Promise<void> {
    return new Promise<void>((resolve, reject) => {
      this.authService.getUserInfo(sessionStorage.getItem('access_token') || "").subscribe(
        (userInfo: any) => {
          const userRoles = userInfo.realm_access?.roles || [];
          
          const requiredRoles = ['client-admin'];
          
          const hasRequiredRole = userRoles.some((role: any) => requiredRoles.includes(role));
  
          if (this.isAuthenticated() && hasRequiredRole) {
            this.http.delete<void>(`${this.categoriesApiUrl}/${id}`).subscribe(
              () => {
                resolve();
              },
              (error) => {
                reject(error);
              }
            );
          } else {
            this.router.navigate(['/unauthorized']);
            reject('User not authenticated or unauthorized');
          }
        },
        (error) => {
          reject(error);
        }
      );
    });
  }
  
}
