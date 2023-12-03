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
// import { WishlistService } from 'libs/products/src/lib/services/wishlist.service';
// import { WishlistItemComponent } from 'libs/products/src/lib/components/wishlist-item/wishlist-item.component';
import { WishListComponent } from './pages/wishlist-page/wish-list.component';
import { CardModule } from 'primeng/card';

@NgModule({
	declarations: [AppComponent, HomePageComponent, ProductListComponent, FooterComponent, HeaderComponent, NavComponent, WishListComponent],
	imports: [CardModule,BrowserModule, RouterModule.forRoot(appRoutes), AccordionModule, BrowserAnimationsModule, UiModule, ProductsModule, HttpClientModule],
	providers: [
		CategoriesService,
		{
			provide: DEFAULT_CURRENCY_CODE,
			useValue: 'TND '
		}
		// WishListService,
	],
	bootstrap: [AppComponent],
	exports: [HomePageComponent]
})
export class AppModule {}
