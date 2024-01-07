import { Route } from '@angular/router';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { CategoriesFormComponent } from './pages/categories/categories-form/categories-form.component';
import { CategoriesListComponent } from './pages/categories/categories-list/categories-list.component';
import { ProductsListComponent } from './pages/products/products-list/products-list.component';
import { ProductsFormComponent } from './pages/products/products-form/products-form.component';
import { UsersListComponent } from './pages/users/users-list/users-list.component';
import { OrdersListComponent } from './pages/orders/orders-list/orders-list.component';
import { OrdersDetailComponent } from './pages/orders/orders-detail/orders-detail.component';
import { AuthentificationComponent } from './pages/auth/Authentification.component';
import { HasRoleGuard } from '../../../../libs/products/src/lib/Auth-Roles-guard/has-role.guard';
import { IsLoggedInGuard } from '../../../../libs/products/src/lib/is-logged-in-guard/is-logged-in-guard.guard';
import { UnothorizedComponent } from 'libs/products/src/lib/components/unothorized/unothorized.component';
import { ProductsAddComponent } from './pages/products/products-add/products-add.component';
import { ProductEditComponent } from './pages/products/products-edit/products-edit.component';
import { EditUsersComponent } from './pages/users/users-edit/users-edit.component';
export const appRoutes: Route[] = [
  { path: '', component: DashboardComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'categories', component: CategoriesListComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'categories/form', component: CategoriesFormComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'categories/form/:id', component: CategoriesFormComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'products', component: ProductsListComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'products/add', component: ProductsAddComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'products/:id/edit', component: ProductEditComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  // { path: 'products/form', component: ProductsFormComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  // { path: 'products/form/:id', component: ProductsFormComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'users', component: UsersListComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'users/:id/edit', component: EditUsersComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin']} },
  { path: 'orders', component: OrdersListComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'orders/:id', component: OrdersDetailComponent, canActivate: [IsLoggedInGuard,HasRoleGuard], data: {userRole: ['client-admin','client-seller']} },
  { path: 'login', component: AuthentificationComponent },
  { path: 'unauthorized', component: UnothorizedComponent, canActivate: [IsLoggedInGuard]},
];
