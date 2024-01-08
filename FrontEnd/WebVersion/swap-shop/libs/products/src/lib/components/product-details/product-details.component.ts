import { Component, OnInit, OnDestroy } from '@angular/core';
import { ProductService } from '../../services/product.service';
import { product } from '../../models/product';
import { ActivatedRoute } from '@angular/router';
import { AuthentificationService } from '../../services/authentification.service';
import { Subscription } from 'rxjs';
import { WishlistService } from '../../services/wishlist.service';

@Component({
  selector: 'swap-shop-product-details',
  templateUrl: './product-details.component.html',
  styleUrl: './product-details.scss'
})
export class ProductDetailsComponent implements OnInit, OnDestroy {
  product?: product;
  productId?: number;
  wishlistProducts?: product[];
  isAuthenticated = false;
  private userDataSubscription?: Subscription;
  heartClass: string = 'pi pi-heart';
  wishlistText: string = ' Add to Wishlist';



  constructor(private productService: ProductService, private wishlistService: WishlistService, private route: ActivatedRoute, private authService: AuthentificationService) {
    const retrievedValue: string | null = sessionStorage.getItem('access_token');

    if (retrievedValue !== null) {
      this.isAuthenticated = true;
    } else {
      console.log('Value not found in session storage');
    }
  }
  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.productId = +params['id'];
    });
    if (this.productId) {
      this.productService.getProduct(this.productId).subscribe(product => {
        this.product = product;
        console.log(this.product);
      });
    }
  }
  ngOnDestroy(): void {
    if (this.userDataSubscription) {
      this.userDataSubscription.unsubscribe();
    }
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

  toggleHeart(idString: string | undefined): void {
    const id = parseInt(idString || '0');
    if (this.heartClass === 'pi pi-heart') {
      this.onAddToWishlist(id);
      this.heartClass = 'pi pi-heart-fill';
      this.wishlistText = ' Added succefully';
    } else {
      this.deleteFromWishlist(idString);
      this.heartClass = 'pi pi-heart';
      this.wishlistText = ' Add to Wishlist';
    }
  }



}
