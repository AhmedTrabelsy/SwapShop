import { Route } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { WishListComponent } from './pages/wishlist-page/wish-list.component';

export const appRoutes: Route[] = [
  { path: '', component: HomePageComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'wishlist', component: WishListComponent },
];
