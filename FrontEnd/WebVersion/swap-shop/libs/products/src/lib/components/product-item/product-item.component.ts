import { product } from '../../models/product';
import { Component, Input, OnInit, OnDestroy } from '@angular/core';
import { formatDistanceToNow } from 'date-fns';
import { WishlistService } from '../../services/wishlist.service';
import { AuthentificationService } from '../../services/authentification.service';
@Component({
  selector: 'swap-shop-product-item',
  templateUrl: './product-item.component.html',
})
export class ProductItemComponent implements OnInit, OnDestroy {

  @Input() product?: product
  wishlistProducts?: product[];
  isAuthenticated = false;
  badgeStatus: 'success' | 'info' | 'warning' | 'danger' | null | undefined ='danger'
  badgeValue="Unsigned"
  sellerName="name"

  constructor(private wishlistService: WishlistService, private authService: AuthentificationService) { 
    const retrievedValue: string | null = sessionStorage.getItem('access_token');

  if (retrievedValue !== null) {
    this.isAuthenticated = true;
  } else {
    console.log('Value not found in session storage');
  }
   }
  ngOnInit(): void {
    const accessToken = sessionStorage.getItem('access_token');
    const userId = sessionStorage.getItem('userId');
    if (accessToken && userId) {
      console.log(this.product?.sellerID);
      if (this.product?.sellerID) {
      this.authService.getUserDataFromId(accessToken,this.product?.sellerID).subscribe(
      (response) => {
        console.log('User Data:', response.username);
        this.sellerName = response.username;
      },
      (error) => {
        console.error('Failed to get user count:', error.message);
      }
    );
      }
}
}
  ngOnDestroy(): void {
    throw new Error('Method not implemented.');
  }
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





