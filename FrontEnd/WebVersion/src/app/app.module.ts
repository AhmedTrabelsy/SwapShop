import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DefaultLayoutComponent } from './layouts/default-layout/default-layout.component';
import { CategroyComponent } from './admin/pages/categroy/categroy.component';
import { NavbarComponent } from './shared/default-layout/navbar/navbar.component';
import { FooterComponent } from './shared/default-layout/footer/footer.component';
import { SideBarComponent } from './shared/default-layout/side-bar/side-bar.component';

@NgModule({
  declarations: [
    AppComponent,
    DefaultLayoutComponent,
    CategroyComponent,
    NavbarComponent,
    FooterComponent,
    SideBarComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
