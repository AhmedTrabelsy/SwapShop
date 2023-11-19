import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsComponent } from './products/products.component';
import { ProductsSearchComponent } from './components/products-search/products-search.component';
import { CategoriesBannerComponent } from './components/categories-banner/categories-banner.component';



@NgModule({
    declarations: [
        ProductsComponent,
        ProductsSearchComponent,
        CategoriesBannerComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        ProductsComponent,
        ProductsSearchComponent,
        CategoriesBannerComponent
    ]
})
export class ProductsModule { }
