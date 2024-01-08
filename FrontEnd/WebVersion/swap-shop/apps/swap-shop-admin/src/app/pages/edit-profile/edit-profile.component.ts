import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { user } from 'libs/products/src/lib/models/user';
import { UserService } from 'libs/products/src/lib/services/user.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'swap-shop-users-edit',
  templateUrl: './edit-profile.component.html',
  styles: ``
})
export class EditProfileComponent implements OnInit {

  user: user = new user('','','','','','',{},'',0);
  error: string | null = null;
  phoneNumber: string = '';
  userId : string | null = null;



  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,
  ) {}


  ngOnInit(): void {
      this.userId = sessionStorage.getItem('userId');
      this.userService.getById(this.userId!).subscribe(user => {

        this.user = user;

        this.phoneNumber = user.attributes ? user.attributes['phone_number'] : ''

      });

  }


  onSubmit(){

    if(this.user.firstName == ''){
      this.error = 'FirstName is required';
      return;
    }

    if(this.user.lastName == ''){
      this.error = 'LastName is required';
      return;
    }

    if(this.user.email == ''){
      this.error = 'Email is required';
      return;
    }

    if(this.user.email.indexOf('@') == -1){
      this.error = 'Email is invalid';
      return;
    }

    if(this.phoneNumber == ''){
      this.error = 'PhoneNumber is required';
      return;
    }

    if(this.phoneNumber.length != 8 || isNaN(Number(this.phoneNumber)) ){
      this.error = 'PhoneNumber is invalid';
      return;
    }

    this.error = null;


    this.userService.update(this.userId!,{
      username: this.user.username,
      firstName: this.user.firstName,
      lastName: this.user.lastName,
      email: this.user.email,
      phoneNumber: this.phoneNumber
    }).subscribe((response)=>{
      this.messageService.add({
        severity: 'success',
        summary: 'Success',
        detail: 'Profile updated'
      });
    },(error)=>{
      this.error = "Email already exists"
    });


  }

}
