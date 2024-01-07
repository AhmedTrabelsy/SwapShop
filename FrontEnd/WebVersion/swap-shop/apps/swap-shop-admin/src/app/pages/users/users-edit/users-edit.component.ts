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
  formData = new FormData();
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

  onSubmit(){}

}
