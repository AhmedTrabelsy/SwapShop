import { Component } from '@angular/core';
import { AnimationOptions } from 'ngx-lottie';

@Component({
	selector: 'swap-shop-root',
	templateUrl: './app.component.html',
	styleUrls: ['./app.component.scss']
})
export class AppComponent {
	title = 'swap-shop-client';
  options: AnimationOptions = {
    path: '../assets/images/Animation - 1700407276106.json',
  };
}
