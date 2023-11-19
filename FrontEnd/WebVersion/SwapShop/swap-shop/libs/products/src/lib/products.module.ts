import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsComponent } from './products/products.component';
import { ProductsSearchComponent } from './components/products-search/products-search.component';



@NgModule({
    declarations: [
        ProductsComponent,
        ProductsSearchComponent
    ],
    imports: [
        CommonModule
    ],
    exports: [
        ProductsComponent,
        ProductsSearchComponent
    ]
})
export class ProductsModule { }
