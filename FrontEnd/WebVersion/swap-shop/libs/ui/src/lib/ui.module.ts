import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BannerComponent } from './components/banner/banner.component';
import { ButtonModule } from 'primeng/button';
import { LottieModule } from 'ngx-lottie';
import { GalleryComponent } from './components/gallery/gallery.component';



@NgModule({
  declarations: [
    BannerComponent,
    GalleryComponent
  ],
  imports: [
    CommonModule,
    ButtonModule,
    LottieModule.forRoot({ player: playerFactory}),
  ],
  exports: [
    BannerComponent,
    GalleryComponent
  ]
})
export class UiModule { }

export function playerFactory() {
  return import('lottie-web');
}
