import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AuthentificationService {
  BASE_URL_AUTH_SERVICE = "http://34.199.239.78:8888/AUTH-SERVICE"

  constructor(private http: HttpClient) { }
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
  getUsersPerMounth(token: string): Observable<any> {
    const getUsersPerMounthUrl = `${this.BASE_URL_AUTH_SERVICE}/getUsersPerMonth`;

    const headers = new HttpHeaders({
      Authorization: `Bearer ${token}`
    });

    return this.http.get<any>(getUsersPerMounthUrl, { headers });
  }
}
