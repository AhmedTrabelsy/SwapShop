import { UserService } from './../../../../../../../libs/products/src/lib/services/user.service';
import { Component } from '@angular/core';
import { user } from 'libs/products/src/lib/models/user';

@Component({
  selector: 'swap-shop-users-list',
  templateUrl: './users-list.component.html',
  styles: ``
})
export class UsersListComponent {

  users : user[] = [];

  constructor(private userService : UserService) {
    this.userService.getAll().subscribe((res)=>{
      this.users = res
      console.log('xxx');
      // alert(this.users[1].attributes!['phone_number']);

    });
  }
}
