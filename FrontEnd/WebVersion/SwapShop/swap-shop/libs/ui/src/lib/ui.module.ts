import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BannerComponent } from './components/banner/banner.component';
import { ButtonModule } from 'primeng/button';
import { LottieModule } from 'ngx-lottie';



@NgModule({
  declarations: [
    BannerComponent
  ],
  imports: [
    CommonModule,
    ButtonModule,
    LottieModule.forRoot({ player: playerFactory}),
  ],
  exports: [
    BannerComponent
  ]
})
export class UiModule { }

export function playerFactory() {
  return import('lottie-web');
}
