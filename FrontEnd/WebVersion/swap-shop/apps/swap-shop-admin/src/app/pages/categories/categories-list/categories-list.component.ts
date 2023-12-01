import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'libs/products/src/lib/models/category';
import { CategoriesService } from 'libs/products/src/lib/services/categories.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Subscription } from 'rxjs';

@Component({
  selector: 'swap-shop-categories-list',
  templateUrl: './categories-list.component.html',
  styles: ``
})
export class CategoriesListComponent implements OnInit, OnDestroy {

  categories: Category[] = [];
  private categoriesSubscription?: Subscription;

  constructor(
    private categoriesService: CategoriesService,
    private messageService: MessageService,
    private confirmationService: ConfirmationService,
    private router: Router
  ) { }


  ngOnInit(): void {
    this._getCategories();
  }


  ngOnDestroy() {
    if (this.categoriesSubscription) {
      this.categoriesSubscription.unsubscribe();
    }
  }

  deleteCategory(categoryId: string) {
    if (this.confirmationService) {
      this.confirmationService.confirm({
        message: 'Do you want to Delete this Category?',
        header: 'Delete Category',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {
          this.categoriesService
            .deleteCategoryById(categoryId)
            .subscribe(
              () => {
                this._getCategories();
                this.messageService.add({
                  severity: 'success',
                  summary: 'Success',
                  detail: 'Category is deleted!'
                });
              },
              () => {
                this.messageService.add({
                  severity: 'error',
                  summary: 'Error',
                  detail: 'Category is not deleted!'
                });
              }
            );
        }
      });
    }
  }


  updateCategory(categoryid: string) {
    this.router.navigateByUrl(`categories/form/${categoryid}`);
  }

  private _getCategories() {
    this.categoriesSubscription = this.categoriesService
      .getCategories().subscribe((categories) => {
        this.categories = categories;
      });
  }

}
