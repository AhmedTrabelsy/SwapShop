import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'libs/products/src/lib/models/category';
import { product } from 'libs/products/src/lib/models/product';
import { CategoriesService } from 'libs/products/src/lib/services/categories.service';
import { ProductService } from 'libs/products/src/lib/services/product.service';

@Component({
  selector: 'swap-shop-products-form',
  templateUrl: './products-edit.component.html',
  styles: ``
})
export class ProductEditComponent implements OnInit {


  product: product = new product();
  categories : Category[]  = [];
  formData = new FormData();
  error: string | null = null;
  images: File[] = [];
  productId : number | null = null;



  constructor(
    private categoriesService: CategoriesService,
    private productService : ProductService,
    private router: Router,
    private route: ActivatedRoute,
  ) {

  }

  ngOnInit(): void {
     this.categoriesService.getCategories().subscribe((res)=>this.categories = res);


     this.route.paramMap.subscribe(params=> {
      this.productId = Number(params.get('id'));

      this.productService.getProduct(this.productId).subscribe((res)=>{
        this.product = res;

       })

      });
  }

  onChangeCategory(event: any) {
    this.product.categoryID = event.value.id;
  }


  onFileSelected(event:any) {
    const file:File = event.target.files[0];
    if (file) {
      this.images.push(file);
    }
  }

  onSubmit(){
    if(this.product.categoryID == null){
      this.error = 'Category is required';
      return;
    }

    if(this.product.name == ''){
      this.error = 'Name is required';
      return;
    }

    if(this.product.description == ''){
      this.error = 'Description is required';
      return;
    }

    if(this.product.price == 0){
      this.error = 'Price is required';
      return;
    }


    this.error = null;

    this.formData = new FormData();

    this.formData.append('name', this.product.name!);
    this.formData.append("description", this.product.description!);
    this.formData.append("price", this.product.price!.toString());
    if(this.product.categoryID != null){
      this.formData.append('categoryID', this.product.categoryID.toString());
    }

    this.images.forEach((image)=>{
      this.formData.append('images', image);
    })

    this.formData.append('sellerID', sessionStorage.getItem('userId')!);

  this.productService.updateProduct(this.productId!,this.formData).subscribe((response)=>{
    this.router.navigate(['/products']);
  })


  }

}
