import { Route } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CategoriesFormComponent } from './pages/categories/categories-form/categories-form.component';
import { CategoriesListComponent } from './pages/categories/categories-list/categories-list.component';
import { ProductsListComponent } from './pages/products/products-list/products-list.component';
import { ProductsFormComponent } from './pages/products/products-form/products-form.component';
import { UsersListComponent } from './pages/users/users-list/users-list.component';
import { UsersFormComponent } from './pages/users/users-form/users-form.component';
import { OrdersListComponent } from './pages/orders/orders-list/orders-list.component';
import { OrdersDetailComponent } from './pages/orders/orders-detail/orders-detail.component';
import { AuthentificationComponent } from './pages/auth/Authentification.component';
import { HasRoleGuard } from '../../../../libs/products/src/lib/Auth-Roles-guard/has-role.guard';
import { UnothorizedComponent } from 'libs/products/src/lib/components/unothorized/unothorized.component';
export const appRoutes: Route[] = [
  { path: '', component: DashboardComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'categories', component: CategoriesListComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'categories/form', component: CategoriesFormComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'categories/form/:id', component: CategoriesFormComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'products', component: ProductsListComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'products/form', component: ProductsFormComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'products/form/:id', component: ProductsFormComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'users', component: UsersListComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'users/form', component: UsersFormComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'users/form/:id', component: UsersFormComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'orders', component: OrdersListComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'orders/:id', component: OrdersDetailComponent, canActivate: [HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'login', component: AuthentificationComponent },
  { path: 'unauthorized', component: UnothorizedComponent },
];
