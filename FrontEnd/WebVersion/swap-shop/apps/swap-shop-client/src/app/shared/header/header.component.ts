import { Component } from '@angular/core';

@Component({
  selector: 'swap-shop-header',
  templateUrl: './header.component.html',
})
export class HeaderComponent {
// sessionStorage.setItem('key', 'value');
isAuthenticated = false;
constructor() {
  // Retrieve the value from session storage
  const retrievedValue: string | null = sessionStorage.getItem('key');

  if (retrievedValue !== null) {
    this.isAuthenticated = true;
  } else {
    console.log('Value not found in session storage');
  }
}

}
