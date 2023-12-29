import { Component } from '@angular/core';

@Component({
  selector: 'swap-shop-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
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
