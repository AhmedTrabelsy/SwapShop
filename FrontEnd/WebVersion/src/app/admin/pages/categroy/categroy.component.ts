import { Component, OnInit } from '@angular/core';
import { Category } from 'src/app/models/category';
import { CategoryService } from 'src/app/services/category.service';

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

}
