import { DEFAULT_CURRENCY_CODE, NgModule } from '@angular/core';
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
import { UiModule } from '@swap-shop/ui';
import { ProductsModule } from '@swap-shop/products';
import { HttpClientModule } from '@angular/common/http';
import { CategoriesService } from 'libs/products/src/lib/services/categories.service';
import { BadgeModule } from 'primeng/badge';
import { ButtonModule } from 'primeng/button';
import { WishlistService } from 'libs/products/src/lib/services/wishlist.service';
import { WishListComponent } from './pages/wishlist-page/wish-list.component';
import { CardModule } from 'primeng/card';
import { ToolbarModule } from 'primeng/toolbar';
import { DataViewModule } from 'primeng/dataview';
@NgModule({
  declarations: [
    AppComponent,
    HomePageComponent,
    ProductListComponent,
    FooterComponent,
    HeaderComponent,
    NavComponent,
    WishListComponent,
  ],
  imports: [
    DataViewModule,
    ToolbarModule,
    BrowserModule,
    RouterModule.forRoot(appRoutes),
    AccordionModule,
    BrowserAnimationsModule,
    UiModule,
    ProductsModule,
    HttpClientModule,
    ButtonModule,
    BadgeModule,
    CardModule
  ],
  providers: [
    CategoriesService,
    {
      provide: DEFAULT_CURRENCY_CODE,
      useValue: 'TND'
    },
    WishlistService
  ],
  bootstrap: [AppComponent],
  exports: [HomePageComponent]
})
export class AppModule {}
