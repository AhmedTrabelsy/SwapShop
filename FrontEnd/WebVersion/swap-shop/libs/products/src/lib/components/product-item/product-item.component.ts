import { product } from '../../models/product';
import { Component, Input } from '@angular/core';
import { formatDistanceToNow } from 'date-fns';
import { BadgeModule } from 'primeng/badge';

@Component({
  selector: 'swap-shop-product-item',
  templateUrl: './product-item.component.html',
})
export class ProductItemComponent {
  @Input() product?: product

  badgeStatus: 'success' | 'info' | 'warning' | 'danger' | null | undefined ='danger'
  badgeValue="Unsigned"
  sellerName="name"
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





