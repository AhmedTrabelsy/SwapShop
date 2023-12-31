import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AuthentificationService } from '../../services/authentification.service';
import { Message } from 'primeng/api/message';
@Component({
  selector: 'swap-shop-login-page',
  templateUrl: './login-page.component.html',
  styles: []
})
export class LoginPageComponent {
  form: FormGroup;
  isSubmitted = false;
  username = '';
  password = '';
  isAuthenticating = true;
  alertMessage = "Invalid username or password. Please try again.";

  constructor(private formBuilder: FormBuilder, private authService: AuthentificationService ) {
    this.form = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    this.isSubmitted = true;

    if (this.form.invalid) {
      return;
    }

    const formData = new FormData();
    formData.append('username', this.form.get('username')?.value);
    formData.append('password', this.form.get('password')?.value);

	const username = this.form.get('username')?.value;
    const password = this.form.get('password')?.value;

    this.authService.login(username, password).subscribe(
      (response) => {
        console.log('Login successful:', response);
        if (response && response.access_token) {
          console.log(response);
          sessionStorage.setItem('access_token', response.access_token);
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
      },
      (error) => {
        console.error('Login failed:', error);
        this.isAuthenticating = false;
      }
    );
	console.log(formData.get('username'));
	console.log(formData.get('password'));
    this.form.reset();
    this.isSubmitted = false;
  }
}
