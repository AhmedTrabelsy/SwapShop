import { Component } from '@angular/core';
import { wishlist } from 'libs/products/src/lib/models/wishlist';
import { SharedService } from 'libs/products/src/lib/services/shared.service';
import { formatDistanceToNow } from 'date-fns';

@Component({
  selector: 'swap-shop-wish-list',
  templateUrl: './wish-list.component.html',
  styles: []
})
export class WishListComponent {
  wishlists: wishlist[] = [];


  constructor(private sharedService: SharedService) { }

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
