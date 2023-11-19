import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { UiModule } from '@swap-shop/ui';
import { ProductsModule } from '@swap-shop/products';

@Component({
  standalone: true,
  imports: [RouterModule, ProductListComponent, UiModule, ProductsModule],
  selector: 'swap-shop-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'swap-shop';
}
