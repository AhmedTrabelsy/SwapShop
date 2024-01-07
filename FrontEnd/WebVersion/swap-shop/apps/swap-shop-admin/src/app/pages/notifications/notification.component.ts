import { NotificationService } from './../../../../../../libs/products/src/lib/services/notification.service';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { user } from 'libs/products/src/lib/models/user';
import { UserService } from 'libs/products/src/lib/services/user.service';
import { MessageService } from 'primeng/api';

@Component({
  selector: 'swap-shop-notifications',
  templateUrl: './notification.component.html',
  styles: ``
})
export class NotificationComponent {

  error: string | null = null;
  title: string = '';
  description: string = '';


  constructor(
    private notificationService: NotificationService,
    private router: Router,
    private route: ActivatedRoute,
    private messageService: MessageService,

  ) {}


  onSubmit(){

    if(this.title == ''){
      this.error = 'title is required';
      return;
    }

    if(this.description == ''){
      this.error = 'description is required';
      return;
    }
    this.error = null;


   this.notificationService.create({title:this.title,body:this.description}).subscribe(res => {

    this.messageService.add({
      severity: 'success',
      summary: 'Success',
      detail: 'Notification is created!'
    });

    this.title = '';
    this.description = '';

   });


  }

}
