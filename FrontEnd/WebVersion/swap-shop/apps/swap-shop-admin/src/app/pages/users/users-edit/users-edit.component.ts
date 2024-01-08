import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { user } from 'libs/products/src/lib/models/user';
import { UserService } from 'libs/products/src/lib/services/user.service';

@Component({
  selector: 'swap-shop-users-edit',
  templateUrl: './users-edit.component.html',
  styles: ``
})
export class EditUsersComponent implements OnInit {

  user: user = new user('','','','','','',{},'',0);
  error: string | null = null;
  phoneNumber: string = '';
  userId : string | null = null;



  constructor(
    private userService: UserService,
    private router: Router,
    private route: ActivatedRoute,
  ) {}


  ngOnInit(): void {
    this.route.paramMap.subscribe(params=> {
      this.userId = params.get('id');
      this.userService.getById(this.userId!).subscribe(user => {

        this.user = user;

        this.phoneNumber = user.attributes ? user.attributes['phone_number'] : ''

      });
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
    }).subscribe(()=>{
      this.router.navigate(['/users']);
    },()=>{
      this.error = "Email already exists"
    });


  }

}
