import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { Category } from '../../models/category';
import { CategoriesService } from '../../services/categories.service';
import { SharedService } from '../../services/shared.service';
@Component({
  selector: 'swap-shop-categories-banner',
  templateUrl: './categories-banner.component.html',
})


export class CategoriesBannerComponent implements OnInit, OnDestroy {
  categories: Category[] = [];
  private categoriesSubscription?: Subscription;

  constructor(private categoriesService: CategoriesService, private sharedService: SharedService) { }

  ngOnInit(): void {
    this.categoriesSubscription = this.categoriesService
      .getCategories().subscribe((categories) => {
        this.categories = categories.filter(category => category.subcategories?.length != 0);

      });
  }

  ngOnDestroy(): void {
    // Unsubscribe from the subscription to avoid memory leaks
    if (this.categoriesSubscription) {
      this.categoriesSubscription.unsubscribe();
    }
  }

  isHovered = false;
  hoveredCategory?: Category;

  onMouseEnter(category: Category) {
    this.isHovered = true;
    this.hoveredCategory = category;
  }

  onMouseLeave() {
    this.isHovered = false;
    this.hoveredCategory = null!;
  }

  // Shared Functions
  calculateAnimationDelay(index?: number): string {
    return this.sharedService.calculateAnimationDelay(index);
  }
}
