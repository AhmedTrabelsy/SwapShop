import { Component, Inject, Input } from '@angular/core';
import { wishlist } from '../../models/wishlist';
import { formatDistanceToNow } from 'date-fns';
import { Subscription } from 'rxjs';
import { WishlistService } from '../../services/wishlist.service';
import { SharedService } from '../../services/shared.service';
import { product } from '../../models/product';

@Component({
	selector: 'swap-shop-wishlist-item',
	templateUrl: './wishlist-item.component.html',
	styles: []
})
export class WishlistItemComponent {

  products: product[] = [];
  // private wishlistSubscription?: Subscription;

  constructor(private wishlistService: WishlistService, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.wishlistService.getProducts().subscribe(
      (products: any[]) => {
        if (Array.isArray(products)) {
          this.products = products;
          console.log('Filtered Products:', this.products);
        } else {
          console.error('Received Products data is invalid:', products);
        }
      },
      (error) => {
        console.error('Error fetching Products:', error);
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
    // if (this.wishlistSubscription) {
    //   this.wishlistSubscription.unsubscribe();
    // }
  }

  // Shared Functions
  calculateAnimationDelay(index?: number): string {
    return this.sharedService.calculateAnimationDelay(index);
  }

  heartClass: string = 'pi pi-heart';
  wishlistText: string = ' Add to Wishlist';
  
  toggleHeart(): void {
    if (this.heartClass === 'pi pi-heart') {
      this.heartClass = 'pi pi-heart-fill';
      this.wishlistText = ' Added succefully';
    } else {
      this.heartClass = 'pi pi-heart';
      this.wishlistText = ' Add to Wishlist';
    }
  }
  getTimeAgo(creationDate?: Date): string {
    if (creationDate) {
      return formatDistanceToNow(new Date(creationDate), { addSuffix: true });
    }
    return '';
  }

  formatCurrency(price?: number): string {
    if (price) {
      if ((price | 0) < price) {
        const formattedPrice = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'TND' }).format(price);
        return formattedPrice.replace(',', ' ');
      }
      const formattedPrice = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'TND', maximumFractionDigits: 0 }).format(price);
      return formattedPrice.replace(',', ' ');
    }
    return 'Undefined';
  }
}
