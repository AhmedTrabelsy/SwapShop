import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BannerComponent, UiComponent } from '@swap-shop/ui';
import { ProductListComponent } from './pages/product-list/product-list.component';

@Component({
  standalone: true,
  imports: [RouterModule, BannerComponent, UiComponent, ProductListComponent],
  selector: 'swap-shop-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
})
export class AppComponent {
  title = 'swap-shop';
}
