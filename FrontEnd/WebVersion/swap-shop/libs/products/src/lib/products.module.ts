import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsComponent } from './products/products.component';
import { ProductsSearchComponent } from './components/products-search/products-search.component';
import { CategoriesBannerComponent } from './components/categories-banner/categories-banner.component';
import { ProductItemComponent } from './components/product-item/product-item.component';
import { FeaturedProductsComponent } from './components/featured-products/featured-products.component';
import { ButtonModule } from 'primeng/button';
import { BadgeModule } from 'primeng/badge';
import { LoginPageComponent } from './components/login-page/login-page.component';
import { WishlistItemComponent } from './components/wishlist-item/wishlist-item.component';
import { CardModule } from 'primeng/card';
import { InputTextModule } from 'primeng/inputtext';
import { PasswordModule } from 'primeng/password';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MessagesModule } from 'primeng/messages';
import { UnothorizedComponent } from './components/unothorized/unothorized.component';
import { ProductDetailsComponent } from './components/product-details/product-details.component';
import { DividerModule } from 'primeng/divider';
import { SignupPageComponent } from './components/signup-page/signup-page.component';
@NgModule({
	declarations: [
		ProductsComponent,
		ProductsSearchComponent,
		CategoriesBannerComponent,
		ProductItemComponent,
		FeaturedProductsComponent,
		LoginPageComponent,
		WishlistItemComponent,
		UnothorizedComponent,
		UnothorizedComponent,
		ProductDetailsComponent,
    SignupPageComponent,
	],
	imports: [DividerModule,MessagesModule, ReactiveFormsModule, FormsModule, CommonModule, ButtonModule, BadgeModule, CardModule, InputTextModule, PasswordModule],
	exports: [ProductsComponent, ProductsSearchComponent, CategoriesBannerComponent, ProductItemComponent, FeaturedProductsComponent, WishlistItemComponent]
})
export class ProductsModule {}
