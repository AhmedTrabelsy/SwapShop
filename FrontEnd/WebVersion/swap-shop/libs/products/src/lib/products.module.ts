import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsComponent } from './products/products.component';
import { ProductsSearchComponent } from './components/products-search/products-search.component';
import { CategoriesBannerComponent } from './components/categories-banner/categories-banner.component';
import { ProductItemComponent } from './components/product-item/product-item.component';
import { FeaturedProductsComponent } from './components/featured-products/featured-products.component';
<<<<<<< HEAD
import {ButtonModule} from 'primeng/button'
import { BadgeModule } from 'primeng/badge';

@NgModule({
    declarations: [
        ProductsComponent,
        ProductsSearchComponent,
        CategoriesBannerComponent,
        ProductItemComponent,
        FeaturedProductsComponent
    ],
    imports: [
        CommonModule,
        ButtonModule,
        BadgeModule,
    ],
    exports: [
        ProductsComponent,
        ProductsSearchComponent,
        CategoriesBannerComponent,
        ProductItemComponent,
        FeaturedProductsComponent
    ]
=======
import { ButtonModule } from 'primeng/button';
import { WishlistItemComponent } from './components/wishlist-item/wishlist-item.component';

@NgModule({
	declarations: [
		ProductsComponent,
		ProductsSearchComponent,
		CategoriesBannerComponent,
		ProductItemComponent,
		FeaturedProductsComponent,
		WishlistItemComponent
	],
	imports: [CommonModule, ButtonModule],
	exports: [WishlistItemComponent,ProductsComponent, ProductsSearchComponent, CategoriesBannerComponent, ProductItemComponent, FeaturedProductsComponent]
>>>>>>> 4bd670574288a8f7ab9abbcc04fdd3823939e7bc
})
export class ProductsModule {}
