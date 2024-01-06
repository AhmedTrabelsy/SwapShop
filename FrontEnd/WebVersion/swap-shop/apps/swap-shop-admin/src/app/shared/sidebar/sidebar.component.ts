import { Component, OnDestroy, OnInit} from '@angular/core';
import { AuthentificationService } from 'libs/products/src/lib/services/authentification.service';
import { map } from 'rxjs';

@Component({
  selector: 'swap-shop-sidebar',
  templateUrl: './sidebar.component.html',
  styles: ``
})
export class SidebarComponent implements OnInit, OnDestroy {
logoutUser() {
  sessionStorage.removeItem('access_token');
  window.location.href = '/login';
}
isAuthenticated = false;
constructor( private authService: AuthentificationService) {
  const retrievedValue: string | null = sessionStorage.getItem('access_token');
  if (retrievedValue !== null) {
    this.isAuthenticated = true;
  } else {
    console.log('Value not found in session storage');
  }
}
  userData: any;
  userRole: string[] = [];
  ngOnInit(): void {
  const accessToken = sessionStorage.getItem('access_token');
  const userId = sessionStorage.getItem('userId');
    if (accessToken && userId) {
    const userData = JSON.parse(atob(accessToken.split('.')[1]));
    console.log('Role:', userData.realm_access?.roles);
    for (const role of userData.realm_access.roles) {
      if (role === 'client-admin') {
        this.userRole.push('admin');
      }
      if (role === 'client-seller') {
        this.userRole.push('seller');
      }
      if (role === 'client-user') {
        this.userRole.push('user');
      }
    }
    this.authService.getUserDataFromId(accessToken,userId).subscribe(
      (response) => {
        console.log('User Data:', response);
        this.userData = response;
      },
      (error) => {
        console.error('Failed to get user count:', error.message);
      }
    );
    }
  }
  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }

}
