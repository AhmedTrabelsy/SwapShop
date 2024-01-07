import { UserService } from './../../../../../../../libs/products/src/lib/services/user.service';
import { Component, OnInit } from '@angular/core';
import { user } from 'libs/products/src/lib/models/user';
import { ConfirmationService, MessageService } from 'primeng/api';

@Component({
  selector: 'swap-shop-users-list',
  templateUrl: './users-list.component.html',
  styles: ``
})
export class UsersListComponent  implements OnInit{

  users : user[] = [];

  constructor(private userService : UserService,
    private confirmationService: ConfirmationService,
    private messageService: MessageService,

    ) {

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


      this.confirmationService.confirm({
        message: 'Do you want to delete this User?',
        header: 'Delete User',
        icon: 'pi pi-exclamation-triangle',
        accept: () => {

          this.userService.delete(id).subscribe((res)=>{
            this.getAllUsers();

            this.messageService.add({
              severity: 'success',
              summary: 'Success',
              detail: 'User is deleted!'
            });
          });

        }
      });

  }
}
