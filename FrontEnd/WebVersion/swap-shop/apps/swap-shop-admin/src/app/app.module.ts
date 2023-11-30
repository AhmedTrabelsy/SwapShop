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

@NgModule({
	declarations: [AppComponent, NxWelcomeComponent, SidebarComponent, ShellComponent, DashboardComponent, ProductsFormComponent, ProductsListComponent],
	imports: [BrowserModule, RouterModule.forRoot(appRoutes)],
	providers: [],
	bootstrap: [AppComponent],
	exports: [
   SidebarComponent,
   ShellComponent,
   DashboardComponent,
   ProductsFormComponent,
   ProductsListComponent
	]
})
export class AppModule {}
