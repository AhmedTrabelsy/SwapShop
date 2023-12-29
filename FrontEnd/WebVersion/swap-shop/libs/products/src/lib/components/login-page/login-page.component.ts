import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

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

  constructor(private formBuilder: FormBuilder) {
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
	console.log(formData.get('username'));
	console.log(formData.get('password'));
    this.form.reset();
    this.isSubmitted = false;
  }
}
