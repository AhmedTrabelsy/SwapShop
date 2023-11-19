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
import { NavComponent } from './shared/nav/nav.component';
import { ProductsSearchComponent } from 'libs/products/src/lib/components/products-search/products-search.component';
import { UiModule } from '@swap-shop/ui';

@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    ProductListComponent,
    FooterComponent,
    HeaderComponent,
    NavComponent,
    ProductsSearchComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    AccordionModule,
    BrowserAnimationsModule,
    UiModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
  exports: [
    HomePageComponent,
  ]
})
export class AppModule { }
