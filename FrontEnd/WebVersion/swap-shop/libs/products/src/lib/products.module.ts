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
@NgModule({
	declarations: [ProductsComponent, ProductsSearchComponent, CategoriesBannerComponent, ProductItemComponent, FeaturedProductsComponent, LoginPageComponent],
	imports: [CommonModule, ButtonModule, BadgeModule],
	exports: [ProductsComponent, ProductsSearchComponent, CategoriesBannerComponent, ProductItemComponent, FeaturedProductsComponent]
})
export class ProductsModule {}
