import { Component, OnInit } from '@angular/core';
import { CategoriesService, ProductService } from '@swap-shop/products';
import { Category } from 'libs/products/src/lib/models/category';
import { Product } from 'libs/products/src/lib/models/product';
import { Subscription } from 'rxjs';

@Component({
  selector: 'product-list',
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit {
  categories: Category[] = [];
  products: Product[] = [];
  selectedCategories: string[] = [];

  private productSubscription?: Subscription;

  constructor(
    private productService: ProductService,
    private categoryService: CategoriesService
  ) {}

  ngOnInit(): void {
    this.categoryService.getCategories().subscribe((categories) => {
      this.categories = categories;
      console.log(categories);
    });

    this.productSubscription = this.productService
      .getProducts()
      .subscribe((products) => {
        this.products = products;
      });
  }

  onCategoryCheckboxChange(categoryId: string): void {
    const index = this.selectedCategories.indexOf(categoryId);

    if (index !== -1) {
      this.selectedCategories.splice(index, 1);
    } else {
      this.selectedCategories.push(categoryId);
    }
  }

  getFilteredProducts(): Product[] {
    if (this.selectedCategories.length === 0) {
      return this.products;
    } else {
      return this.products.filter((product) =>
        product.category && this.selectedCategories.includes(product.category.id || '')
      );
    }
  }
}
