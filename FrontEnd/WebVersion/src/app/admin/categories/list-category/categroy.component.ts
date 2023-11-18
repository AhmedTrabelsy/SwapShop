import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-categroy',
  templateUrl: './categroy.component.html',
  styleUrls: ['./categroy.component.css']
})
export class CategroyComponent implements OnInit {

  categories : Category[] = [];

  constructor(private service:CategoryService){

  }


  ngOnInit() {
    this.service.getAll().subscribe(
      (response) => {
        this.categories = response;
      }
    );
  }

  onDeleteCategory(id:number){

    Swal.fire({
      title: "Etes vous sure de vouloir supprimer cette categorie?",
      showDenyButton: true,
      confirmButtonText: "Oui",
      denyButtonText: `Non`
    }).then((result) => {
      if (result.isConfirmed) {
        this.service.delete(id).subscribe((response)=>{
          this.categories = this.categories.filter((category)=>category.id != id);
        });
      }
    });


  }

}
