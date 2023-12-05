import { Component, Inject, OnDestroy, OnInit } from '@angular/core';
import { wishlist } from 'libs/products/src/lib/models/wishlist';
import { WishlistService } from 'libs/products/src/lib/services/wishlist.service';
import { SharedService } from 'libs/products/src/lib/services/shared.service';
import { Subscription } from 'rxjs';
import { formatDistanceToNow } from 'date-fns';

@Component({
  selector: 'swap-shop-wish-list',
  templateUrl: './wish-list.component.html',
  styles: []
})
export class WishListComponent {
  wishlists: wishlist[] = [];
  
  private wishlistSubscription?: Subscription;

  constructor(private wishlistService: WishlistService, private sharedService: SharedService) { }

  // ngOnInit(): void {
  //   this.wishlistSubscription = this.wishlistService.getWishlist().subscribe((wishlist: wishlist[]) => {
  //     this.wishlists = wishlist;
  //     console.log(this.wishlists)
  //   });
  // }

  // ngOnDestroy(): void {
  //   if (this.wishlistSubscription) {
  //     this.wishlistSubscription.unsubscribe();
  //   }
  // }
  calculateAnimationDelay(index?: number): string {
    return this.sharedService.calculateAnimationDelay(index);
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
