import { Component, OnInit, OnDestroy } from '@angular/core';
import { product } from 'libs/products/src/lib/models/product';
import { ProductService } from 'libs/products/src/lib/services/product.service';
import { WishlistService } from 'libs/products/src/lib/services/wishlist.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'swap-shop-product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit, OnDestroy {
  isAuthenticated = false;
  products: product[] = [];
  private productSubscription?: Subscription;
  constructor(private productService: ProductService,private wishlistService: WishlistService) { 
    const retrievedValue: string | null = sessionStorage.getItem('access_token');

    if (retrievedValue !== null) {
      this.isAuthenticated = true;
    } else {
      console.log('Value not found in session storage');
    }
  }
  icons: string[] = [];
  onClick(event: MouseEvent, index: number, idString: string|undefined) {
    const id = parseInt(idString || '0');
    event.preventDefault();
    if (this.icons[index] === "pi pi-heart-fill") {
      this.deleteFromWishlist(idString);
      this.icons[index] = "pi pi-heart";
    }else {
      this.onAddToWishlist(id);
      this.icons[index] = "pi pi-heart-fill";
    }
  }
  ngOnInit(): void {
    this.productSubscription = this.productService
      .getProducts().subscribe((products) => {
        this.products = products;
        this.icons = new Array(this.products.length).fill("pi pi-heart");
        console.log(this.icons);
      });
  }
  onAddToWishlist(productId: number): void {
    this.wishlistService.addToWishlist(productId)
      .subscribe(
        (response) => {
          console.log('Added to wishlist:', response);
        },
        (error) => {
          console.error('Error adding to wishlist:', error);
        }
      );
  }
  deleteFromWishlist(idString?: string | undefined): void {
    if (idString !== undefined) {
      const id = parseInt(idString, 10);
      this.wishlistService.deleteProduct(id);
    } else {
      console.error('Item ID is undefined.');
    }
  }
  ngOnDestroy(): void {
    if (this.productSubscription) {
      this.productSubscription.unsubscribe();
    }
  }

}
