import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { appRoutes } from './app.routes';
import { HomePageComponent } from './pages/home-page/home-page.component';
import { ProductListComponent } from './pages/product-list/product-list.component';
import { FooterComponent } from './shared/footer/footer.component';
import { HeaderComponent } from './shared/header/header.component';
import { AccordionModule } from 'primeng/accordion';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

@NgModule({
  declarations: [AppComponent, HomePageComponent, ProductListComponent, FooterComponent, HeaderComponent],
  imports: [BrowserModule, RouterModule.forRoot(appRoutes), AccordionModule, BrowserAnimationsModule],
  providers: [],
  bootstrap: [AppComponent],
  exports: [
    HomePageComponent,
    ProductListComponent,
    FooterComponent,
    HeaderComponent
  ]
})
export class AppModule { }
