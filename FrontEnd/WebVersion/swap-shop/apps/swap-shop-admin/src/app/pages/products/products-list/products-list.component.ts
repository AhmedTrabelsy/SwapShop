import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { ProductService } from 'libs/products/src/lib/services/product.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
@Component({
  selector: 'swap-shop-products-list',
  templateUrl: './products-list.component.html',
  styles: ``
})

export class ProductsListComponent implements OnInit, OnDestroy{
  products = [];
  endsubs$: Subject<unknown> = new Subject();

  constructor(
    private productsService: ProductService,
    private router: Router,
    private messageService: MessageService,
    private confirmationService: ConfirmationService
  ) {}

  ngOnInit(): void {
    this._getProducts();
  }

  ngOnDestroy() {
    this.endsubs$.complete();
  }

  private _getProducts() {
    this.productsService
      .getProducts()
      .pipe(takeUntil(this.endsubs$))
      .subscribe((products) => {
        this.products = products;
      });
  }

  updateProduct(productid: string) {
    this.router.navigateByUrl(`products/form/${productid}`);
  }

  deleteProduct(productId: string) {
    this.confirmationService.confirm({
      message: 'Do you want to delete this Product?',
      header: 'Delete Product',
      icon: 'pi pi-exclamation-triangle',
      accept: () => {
        this.productsService
          .deleteProduct(productId)
          .pipe(takeUntil(this.endsubs$))
          .subscribe(
            () => {
              this._getProducts();
              this.messageService.add({
                severity: 'success',
                summary: 'Success',
                detail: 'Product is deleted!'
              });
            },
            () => {

              this._getProducts();
              this.messageService.add({
                severity: 'success',
                summary: 'Success',
                detail: 'Product is deleted!'
              });
            }
          );
      }
    });
  }
}
