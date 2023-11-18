import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-edit-category',
  templateUrl: './edit-category.component.html',
  styleUrls: ['./edit-category.component.css']
})
export class EditCategoryComponent implements OnInit {

  id : number = 0;
  categories : Category[] = [];
  category : Category =  new Category(0,"","",[]);
  error: string | null = null;
  icon: File | null = null;
  formData = new FormData();


  constructor(private service:CategoryService,private router: Router,private route: ActivatedRoute){

  }

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.id = params['id'];
      this.service.getById(this.id).subscribe(
        (response) => {
          this.category = response;
        }
      );
    });


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

    if(this.category.name == ''){
      this.error = "Choisir le nom de la catégorie";
    }

    this.formData.append('name', this.category.name);
    if(this.icon != null){
      this.formData.append("icon", this.icon!);
    }
    // if(this.parentCategoryId != null){
    //   this.formData.append('parentCategoryId', this.parentCategoryId?.toString());
    // }

    if(this.error != null){

      Swal.fire({
        title: 'Error!',
        text: this.error,
        icon: 'error',
        confirmButtonText: 'Cool'
      })

      return;

    }




    this.service.edit(this.id,this.formData).subscribe((response)=>{
      this.router.navigate(['/admin/categories']);
    })
  }

}
