import { Component } from '@angular/core';
@Component({
  selector: 'swap-shop-categories-banner',
  templateUrl: './categories-banner.component.html',
})
export class CategoriesBannerComponent{
  // categories: Category[] = [];
  // endSubs$: Subject<any> = new Subject();

  // constructor(private categoriesService: CategoriesService) {}

  // ngOnInit(): void {
  //   this.categoriesService
  //     .getCategories()
  //     .pipe(takeUntil(this.endSubs$))
  //     .subscribe((categories) => {
  //       this.categories = categories;
  //     });
  // }

  // ngOnDestroy() {
  //   this.endSubs$.next();
  //   this.endSubs$.complete();
  // }
}
