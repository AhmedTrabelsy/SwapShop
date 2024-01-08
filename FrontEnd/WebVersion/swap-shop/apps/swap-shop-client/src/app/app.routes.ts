import { Route } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { WishListComponent } from './pages/wishlist-page/wish-list.component';
import { LoginPageComponent } from 'libs/products/src/lib/components/login-page/login-page.component';
import { IsLoggedInGuard } from '../../../../libs/products/src/lib/is-logged-in-guard/is-logged-in-guard.guard';
import { ContactCreatorsComponent } from './pages/contact-creators/contact-creators.component';
import { ProductDetailsComponent } from '../../../../libs/products/src/lib/components/product-details/product-details.component';

export const appRoutes: Route[] = [
  { path: '', component: HomePageComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'wishlist', component: WishListComponent, canActivate: [IsLoggedInGuard] },
  { path: 'login', component: LoginPageComponent },
  { path: 'contact', component: ContactCreatorsComponent },
  // { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }
  { path: 'products/:id', component: ProductDetailsComponent},
];
