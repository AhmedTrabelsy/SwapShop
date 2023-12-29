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
  getUserData(): Observable<any> {
    const getUserDataUrl = `${this.BASE_URL_AUTH_SERVICE}/user`;

    // Get the access token from session storage
    const accessToken = sessionStorage.getItem('access_token');

    if (!accessToken) {
      return new Observable<any>();
    }
    const headers = new HttpHeaders({
      Authorization: accessToken
    });

    return this.http.get<any>(getUserDataUrl, { headers });
  }
}
