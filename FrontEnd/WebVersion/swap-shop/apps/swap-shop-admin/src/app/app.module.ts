import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { appRoutes } from './app.routes';
import { SidebarComponent } from './shared/sidebar/sidebar.component';
import { DashboardComponent } from './pages/dashboard/dashboard.component';
import { ProductsFormComponent } from './pages/products/products-form/products-form.component';
import { ProductsListComponent } from './pages/products/products-list/products-list.component';
import { CategoriesListComponent } from './pages/categories/categories-list/categories-list.component';
import { CategoriesFormComponent } from './pages/categories/categories-form/categories-form.component';
import { OrdersListComponent } from './pages/orders/orders-list/orders-list.component';
import { OrdersDetailComponent } from './pages/orders/orders-detail/orders-detail.component';
import { UsersListComponent } from './pages/users/users-list/users-list.component';
import { UsersFormComponent } from './pages/users/users-form/users-form.component';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { ToastModule } from 'primeng/toast';
import { ToolbarModule } from 'primeng/toolbar';
import { TableModule } from 'primeng/table';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { HttpClientModule } from '@angular/common/http';
import { CategoriesService } from 'libs/products/src/lib/services/categories.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { InputTextModule } from 'primeng/inputtext';
import { FileUploadModule } from 'primeng/fileupload';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { DropdownModule } from 'primeng/dropdown';
import { ChartModule } from 'primeng/chart';
import { AuthentificationComponent } from './pages/auth/Authentification.component';

@NgModule({
	declarations: [
		AppComponent,
		SidebarComponent,
		DashboardComponent,
		ProductsFormComponent,
		ProductsListComponent,
		CategoriesListComponent,
		CategoriesFormComponent,
		OrdersListComponent,
		OrdersDetailComponent,
		UsersListComponent,
		UsersFormComponent,
		AuthentificationComponent
	],
	imports: [
		ChartModule,
		BrowserModule,
		RouterModule.forRoot(appRoutes),
		CardModule,
		ButtonModule,
		ToastModule,
		ToolbarModule,
		TableModule,
		ConfirmDialogModule,
		HttpClientModule,
		FormsModule,
		ReactiveFormsModule,
		InputTextModule,
		FileUploadModule,
		BrowserAnimationsModule,
		DropdownModule
	],
	providers: [CategoriesService, MessageService, ConfirmationService],
	bootstrap: [AppComponent],
	exports: [
		SidebarComponent,
		DashboardComponent,
		ProductsFormComponent,
		ProductsListComponent,
		CategoriesListComponent,
		CategoriesFormComponent,
		OrdersListComponent,
		OrdersDetailComponent,
		UsersListComponent,
		UsersFormComponent
	]
})
export class AppModule {}
