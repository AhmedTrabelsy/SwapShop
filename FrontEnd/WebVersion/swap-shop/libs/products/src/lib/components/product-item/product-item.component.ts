import { product } from '../../models/product';
import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { ProductService } from '../../services/product.service';

@Component({
  selector: 'swap-shop-product-item',
  templateUrl: './product-item.component.html',
  styleUrl: './product-item.component.scss'
})
export class ProductItemComponent implements OnInit, OnDestroy {

   products: product[] = [];
   private productSubscription?: Subscription;
   constructor(private productService: ProductService) { }

   ngOnInit(): void {
     this.productSubscription = this.productService
       .getProducts().subscribe((products) => {
         this.products = products;
       });
   }

   ngOnDestroy(): void {
     // Unsubscribe from the subscription to avoid memory leaks
     if (this.productSubscription) {
       this.productSubscription.unsubscribe();
     }
   }
}





