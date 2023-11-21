import { product } from '../../models/product';
import { Component, Input } from '@angular/core';

@Component({
  selector: 'swap-shop-product-item',
  templateUrl: './product-item.component.html',
})
export class ProductItemComponent{

   @Input () product?: product

}





