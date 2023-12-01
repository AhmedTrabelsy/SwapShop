import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Category } from 'libs/products/src/lib/models/category';
import { CategoriesService } from 'libs/products/src/lib/services/categories.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { FileUploadEvent } from 'primeng/fileupload';
import { Subscription, timer } from 'rxjs';



@Component({
  selector: 'swap-shop-categories-form',
  templateUrl: './categories-form.component.html',
  styles: ``
})

export class CategoriesFormComponent implements OnInit, OnDestroy {
  uploadedFiles: File[] = [];
  form: FormGroup;
  isSubmitted = false;
  editmode = false;
  currentCategoryId?: string;
  categories?: Category[];
  private confirmationService?: ConfirmationService;
  private categoriesSubscription?: Subscription;


  constructor(
    private messageService: MessageService,
    private formBuilder: FormBuilder,
    private categoriesService: CategoriesService,
    private location: Location,
    private route: ActivatedRoute,
  ) {
    this.form = this.formBuilder.group({
      name: ['', Validators.required],
      selectedCategory: [''],
      uploadedFiles: [''],
    });
  }

  ngOnInit(): void {
    // this._checkEditMode();
    this._getCategories();
    console.log("oninitcategories form not implemented !")
  }

  ngOnDestroy() {
    if (this.categoriesSubscription) {
      this.categoriesSubscription.unsubscribe();
    }
  }

  onSubmit() {
    this.isSubmitted = true;

    if (this.form.invalid) {
      return;
    }


    const formData = new FormData();
    formData.append('name', this.categoryForm['name'].value);
    for (const file of this.uploadedFiles) {
      formData.append('icon', file);
    }
    formData.append('parentCategoryId', this.categoryForm['selectedCategory'].value.id);

    if (this.editmode) {
      // this._updateCategory(category);
    } else {
      this._addCategory(formData);
    }
  }

  onCancle() {
    this.location.back();
  }

  private _addCategory(category: FormData) {
    this.categoriesService.createCategory(category).subscribe((category: Category) => {
      this.messageService.add({
        severity: 'success',
        summary: 'Success',
        detail: `Category ${category.name} is created ! `,
      });
      timer(2000)
        .toPromise()
        .then(() => {
          // this.location.back();
        });
    },
      () => {
        this.messageService.add({
          severity: 'error',
          summary: 'Error',
          detail: 'An error has occured !'
        });
      }
    );
  }

  private _getCategories() {
    this.categoriesService
      .getCategories()
      .pipe()
      .subscribe((categories) => {
        this.categories = categories;
      });
  }

  // private _checkEditMode() {
  //   this.route.params.pipe(takeUntil(this.endsubs$)).subscribe((params) => {
  //     if (params.id) {
  //       this.editmode = true;
  //       this.currentCategoryId = params.id;
  //       this.categoriesService
  //         .getCategory(params.id)
  //         .pipe(takeUntil(this.endsubs$))
  //         .subscribe((category) => {
  //           this.categoryForm.name.setValue(category.name);
  //           this.categoryForm.icon.setValue(category.icon);
  //           this.categoryForm.color.setValue(category.color);
  //         });
  //     }
  //   });
  // }

  onUpload(event: FileUploadEvent) {
    console.log("Upload")
    for (const file of event.files) {
      this.uploadedFiles.push(file);
    }

    this.messageService.add({ severity: 'info', summary: 'File Uploaded', detail: '' });
  }

  get categoryForm() {
    return this.form.controls;
  }
}
