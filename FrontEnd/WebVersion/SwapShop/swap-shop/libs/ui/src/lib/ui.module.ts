import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BannerComponent } from './components/banner/banner.component';
import { ButtonModule } from 'primeng/button';



@NgModule({
  declarations: [
    BannerComponent
  ],
  imports: [
    CommonModule,
    ButtonModule,
  ],
  exports: [
    BannerComponent
  ]
})
export class UiModule { }
