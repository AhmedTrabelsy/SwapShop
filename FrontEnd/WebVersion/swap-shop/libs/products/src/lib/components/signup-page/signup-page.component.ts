import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthentificationService } from '../../services/authentification.service';
import { Message } from 'primeng/api/message';
@Component({
  selector: 'swap-shop-login-page',
  templateUrl: './signup-page.component.html',
  styles: []
})
export class SignupPageComponent {
  form: FormGroup;
  isSubmitted = false;
  username = '';
  firstName = '';
  lastName = '';
  email = '';
  phoneNumber = '';
  password = '';
  isAuthenticating = true;
  alertMessage = "Invalid Data. Please try again.";

  constructor(private formBuilder: FormBuilder, private authService: AuthentificationService ) {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      email: ['', Validators.required],
      phoneNumber: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    this.isSubmitted = true;

    if (this.form.invalid) {
      return;
    }


    this.authService.register({
      username: this.form.get('username')?.value,
      firstName: this.form.get('firstName')?.value,
      lastName: this.form.get('lastName')?.value,
      phoneNumber: this.form.get('phoneNumber')?.value,
      email: this.form.get('email')?.value,
      password: this.form.get('password')?.value
    }).subscribe(
      (response) => {
        this.authService.login(this.form.get('username')?.value, this.form.get('password')?.value).subscribe((response)=>{
          if (response && response.access_token) {
            console.log(response);
            sessionStorage.setItem('access_token', response.access_token);
            this.authService.getUserInfo(response.access_token);
            this.authService.getUserId(response.access_token).subscribe(
              (response) => {
                sessionStorage.setItem('userId', response.userId);
                console.log('Get user id successful:', response.userId)
                this.isAuthenticating = true;
                window.location.href = '/';
              },
              (error) => {
                console.error('Get user id failed:', error);
              }
            );
          }
        });
      },
      (error) => {
        console.error('Login failed:', error);
        this.alertMessage  = error.error.message;
        this.isAuthenticating = false;
      }
    );
    this.isSubmitted = false;
  }
}
