import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { appRoutes } from './app.routes';
import { NxWelcomeComponent } from './nx-welcome.component';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { ShellComponent } from './shared/shell/shell.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProductsFormComponent } from './pages/products/products-form/products-form.component';
import { ProductsListComponent } from './pages/products/products-list/products-list.component';
import { CategoriesListComponent } from './pages/categories/categories-list/categories-list.component';
import { CategoriesFormComponent } from './pages/categories/categories-form/categories-form.component';
import { OrdersListComponent } from './pages/orders/orders-list/orders-list.component';
import { OrdersDetailComponent } from './pages/orders/orders-detail/orders-detail.component';

@NgModule({
	declarations: [AppComponent, NxWelcomeComponent, SidebarComponent, ShellComponent, DashboardComponent, ProductsFormComponent, ProductsListComponent, CategoriesListComponent, CategoriesFormComponent, OrdersListComponent, OrdersDetailComponent],
	imports: [BrowserModule, RouterModule.forRoot(appRoutes)],
	providers: [],
	bootstrap: [AppComponent],
	exports: [
   SidebarComponent,
   ShellComponent,
   DashboardComponent,
   ProductsFormComponent,
   ProductsListComponent,
   CategoriesListComponent,
   CategoriesFormComponent,
   OrdersListComponent,
   OrdersDetailComponent
	]
})
export class AppModule {}
