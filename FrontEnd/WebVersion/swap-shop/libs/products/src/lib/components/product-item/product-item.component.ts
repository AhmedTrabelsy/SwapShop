import { product } from '../../models/product';
import { Component, Input } from '@angular/core';
import { formatDistanceToNow } from 'date-fns';
import { WishlistService } from '../../services/wishlist.service';
@Component({
  selector: 'swap-shop-product-item',
  templateUrl: './product-item.component.html',
})
export class ProductItemComponent{
  @Input() product?: product
  wishlistProducts?: product[];

  badgeStatus: 'success' | 'info' | 'warning' | 'danger' | null | undefined ='danger'
  badgeValue="Unsigned"
  sellerName="name"

  constructor(private wishlistService: WishlistService) { }

  heartClass: string = 'pi pi-heart';
  wishlistText: string = ' Add to Wishlist';

  toggleHeart(idString: string|undefined): void {
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
}





