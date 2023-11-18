import { Category } from 'src/app/models/category';
import { CategoryService } from './../../../../services/category.service';
import { Component, OnInit } from '@angular/core';
import Swal from 'sweetalert2';
import { Router } from '@angular/router';

@Component({
  selector: 'app-create-category',
  templateUrl: './create-category.component.html',
  styleUrls: ['./create-category.component.css']
})
export class CreateCategoryComponent implements OnInit {

  categories : Category[] = [];

  name : string = '';
  parentCategoryId: number | null = null;
  icon: File | null = null;
  formData = new FormData();
  error: string | null = null;




  constructor(private service:CategoryService,private router: Router){

  }

  ngOnInit() {
    this.service.getAll().subscribe(
      (response) => {
        this.categories = response;
      }
    );
  }

  onFileSelected(event:any) {
    const file:File = event.target.files[0];
    if (file) {
      this.icon = file;
    }else{
      this.icon = null;
    }
}
  onSubmit(){
    this.error = null;

    console.log(this.formData.has('icon'));
    console.log(this.name.length);

    if(this.name == ''){
      this.error = "Choisir le nom de la catégorie";
    }else if(this.icon == null){
      this.error = "Choisir l'icone de la catégorie";
    }


    this.formData.append('name', this.name);
    this.formData.append("icon", this.icon!);
    if(this.parentCategoryId != null){
      this.formData.append('parentCategoryId', this.parentCategoryId?.toString());
    }

    if(this.error != null){

      Swal.fire({
        title: 'Error!',
        text: this.error,
        icon: 'error',
        confirmButtonText: 'Cool'
      })

      return;

    }




    this.service.createCategory(this.formData).subscribe((response)=>{
      this.router.navigate(['/admin/categories']);
    })
  }
}
