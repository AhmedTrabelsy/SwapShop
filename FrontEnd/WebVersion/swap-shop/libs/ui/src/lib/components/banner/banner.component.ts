import { Component } from '@angular/core';
import { AnimationOptions } from 'ngx-lottie';

@Component({
  selector: 'swap-shop-ui-banner',
  templateUrl: './banner.component.html',
  styles: ``
})
export class BannerComponent {
  isHovered: boolean = false;
  options: AnimationOptions = {
    path: '../assets/images/Animation - 1700407276106.json',
  };

  toggleHover(bool: boolean) {
    this.isHovered = bool;
  }
}
