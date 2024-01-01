import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Category } from 'libs/products/src/lib/models/category';
import { CategoriesService } from 'libs/products/src/lib/services/categories.service';
import { ProductService } from 'libs/products/src/lib/services/product.service';

@Component({
  selector: 'swap-shop-products-form',
  templateUrl: './products-add.component.html',
  styles: ``
})
export class ProductsAddComponent implements OnInit {

  categories : Category[]  = [];
  name : string = '';
  categoryID : number | null = null;
  description : string = '';
  price: string = '0';
  formData = new FormData();
  error: string | null = null;
  images: File[] = [];



  constructor(
    private categoriesService: CategoriesService,
    private productService : ProductService,
    private router: Router,
  ) {

  }

  ngOnInit(): void {
     this.categoriesService.getCategories().subscribe((res)=>this.categories = res);
  }

  onChangeCategory(event: any) {
    this.categoryID = event.value.id;
  }


  onFileSelected(event:any) {
    const file:File = event.target.files[0];
    if (file) {
      this.images.push(file);
    }
  }

  onSubmit(){

    this.formData = new FormData();

    this.formData.append('name', this.name);
    this.formData.append("description", this.description!);
    this.formData.append("price", this.price!);
    if(this.categoryID != null){
      this.formData.append('categoryID', this.categoryID.toString());
    }

    this.images.forEach((image)=>{
      this.formData.append('images', image);
    })

  this.productService.createProduct(this.formData).subscribe((response)=>{
    this.router.navigate(['/products']);
  })


  }


}
