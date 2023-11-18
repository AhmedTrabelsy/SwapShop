import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import {HttpClientModule} from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { DefaultLayoutComponent } from './layouts/default-layout/default-layout.component';
import { CategroyComponent } from './admin/categories/list-category/categroy.component';
import { NavbarComponent } from './shared/default-layout/navbar/navbar.component';
import { FooterComponent } from './shared/default-layout/footer/footer.component';
import { SideBarComponent } from './shared/default-layout/side-bar/side-bar.component';
import { CreateCategoryComponent } from './admin/categories/create-category/create-category.component';
import { FormsModule } from '@angular/forms';
import { EditCategoryComponent } from './admin/categories/edit-category/edit-category.component';
import { RouterModule } from '@angular/router';

@NgModule({
  declarations: [
    AppComponent,
    DefaultLayoutComponent,
    CategroyComponent,
    NavbarComponent,
    FooterComponent,
    SideBarComponent,
    CreateCategoryComponent,
    EditCategoryComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    RouterModule,
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
