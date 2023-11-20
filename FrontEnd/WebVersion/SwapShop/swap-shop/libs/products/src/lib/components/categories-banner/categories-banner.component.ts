import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Category } from '../../models/category';
import { CategoriesService } from '../../services/categories.service';
@Component({
  selector: 'swap-shop-categories-banner',
  templateUrl: './categories-banner.component.html',
})


export class CategoriesBannerComponent implements OnInit, OnDestroy {
  categories: Category[] = [];
  private categoriesSubscription?: Subscription;

  constructor(private categoriesService: CategoriesService) { }

  ngOnInit(): void {
    this.categoriesSubscription = this.categoriesService
      .getCategories().subscribe((categories) => {
        this.categories = categories;
        console.log(this.categories.length);
      });
  }

  ngOnDestroy(): void {
    // Unsubscribe from the subscription to avoid memory leaks
    if (this.categoriesSubscription) {
      this.categoriesSubscription.unsubscribe();
    }
  }
}
