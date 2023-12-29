import { Component } from '@angular/core';

@Component({
  selector: 'swap-shop-sidebar',
  templateUrl: './sidebar.component.html',
  styles: ``
})
export class SidebarComponent {
logoutUser() {
throw new Error('Method not implemented.');
}
isAuthenticated = false;
constructor() {
  const retrievedValue: string | null = sessionStorage.getItem('access_token');

  if (retrievedValue !== null) {
    this.isAuthenticated = true;
  } else {
    console.log('Value not found in session storage');
  }
}

}
