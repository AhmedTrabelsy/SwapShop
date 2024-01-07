import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { user } from '../models/user';


@Injectable({
  providedIn: 'root'
})
export class NotificationService {
  notificationApiUrl = "http://34.199.239.78:8888" + '/NOTIFICATION-SERVICE';

  constructor(private http: HttpClient) {}

  create(data:{ [key: string]: string }): Observable<unknown> {
    return this.http.post<unknown>(this.notificationApiUrl, data);
  }


}
