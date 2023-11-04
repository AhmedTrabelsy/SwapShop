import { Component } from '@angular/core';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {
  userMenuOpen = false;

  toggleUserMenu(): void {
    this.userMenuOpen = !this.userMenuOpen;
  }
}


