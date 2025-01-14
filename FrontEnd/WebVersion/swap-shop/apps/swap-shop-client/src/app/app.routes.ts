import { SignupPageComponent } from './../../../../libs/products/src/lib/components/signup-page/signup-page.component';
import { Route } from '@angular/router';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { WishListComponent } from './pages/wishlist-page/wish-list.component';
import { LoginPageComponent } from 'libs/products/src/lib/components/login-page/login-page.component';
import { IsLoggedInGuard } from '../../../../libs/products/src/lib/is-logged-in-guard/is-logged-in-guard.guard';
import { ContactCreatorsComponent } from './pages/contact-creators/contact-creators.component';
import { ProductDetailsComponent } from '../../../../libs/products/src/lib/components/product-details/product-details.component';
import { CheckoutComponent } from 'libs/products/src/lib/components/checkout/checkout.component';
import { SuccessComponent } from 'libs/products/src/lib/components/success/success.component';
import { OrdersListComponent } from './pages/orders/orders-list.component';

export const appRoutes: Route[] = [
  { path: '', component: HomePageComponent },
  { path: 'products', component: ProductListComponent },
  { path: 'wishlist', component: WishListComponent, canActivate: [IsLoggedInGuard] },
  { path: 'orders', component: OrdersListComponent, canActivate: [IsLoggedInGuard] },
  { path: 'login', component: LoginPageComponent },
  { path: 'signup', component: SignupPageComponent },
  { path: 'contact', component: ContactCreatorsComponent },
  { path: 'checkout/:id', component: CheckoutComponent, canActivate: [IsLoggedInGuard]},
  { path: 'success', component: SuccessComponent, canActivate: [IsLoggedInGuard]},

  // { path: 'admin', loadChildren: () => import('./admin/admin.module').then(m => m.AdminModule) }
  { path: 'products/:id', component: ProductDetailsComponent},
];
