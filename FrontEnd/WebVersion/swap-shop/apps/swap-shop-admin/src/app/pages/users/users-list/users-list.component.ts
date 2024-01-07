import { UserService } from './../../../../../../../libs/products/src/lib/services/user.service';
import { Component, OnInit } from '@angular/core';
import { user } from 'libs/products/src/lib/models/user';

@Component({
  selector: 'swap-shop-users-list',
  templateUrl: './users-list.component.html',
  styles: ``
})
export class UsersListComponent  implements OnInit{

  users : user[] = [];

  constructor(private userService : UserService) {

  }

  ngOnInit(): void {
    this.getAllUsers();
  }


  getAllUsers(){
    this.userService.getAll().subscribe((res)=>{
      this.users = res
    });
  }

  deleteUser(id:string){
    this.userService.delete(id).subscribe((res)=>{
      this.getAllUsers();
    });
  }
}
