import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { user } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {
  BASE_URL_AUTH_SERVICE = "http://34.199.239.78:8888/AUTH-SERVICE"
  user!: user;
  getToken(): string {
    return sessionStorage.getItem('access_token') || '';
  }
  constructor(private http: HttpClient) {
    // this.user = this.getUserInfo(this.getToken());
  }
  isAuthenticated() {
    return of(this.getToken() !== '');
  }
  login(username: string, password: string): Observable<any> {
    const loginUrl = `${this.BASE_URL_AUTH_SERVICE}/login`;

    const body = {
      username,
      password
    };

    return this.http.post<any>(loginUrl, body);
  }

  getUserCount(token: string): Observable<any> {
    const getUserCountUrl = `${this.BASE_URL_AUTH_SERVICE}/getUsersCount`;

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get<any>(getUserCountUrl, { headers });
  }
  getUserId(token: string): Observable<any> {
    // this.user = this.getUserInfo(token);
    const getUserIdUrl = `${this.BASE_URL_AUTH_SERVICE}/getUserId`;
  
    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });
    return this.http.get<any>(getUserIdUrl, { headers: headers });
  }  
  getUsersPerMounth(token: string): Observable<any> {
    const getUsersPerMounthUrl = `${this.BASE_URL_AUTH_SERVICE}/getUsersPerMonth`;

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get<any>(getUsersPerMounthUrl, { headers });
  }

  getUserInfo(token: string): Observable<any> {
    const userData = JSON.parse(atob(token.split('.')[1]));
    return of(userData);
  }
}
