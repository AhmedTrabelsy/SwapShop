import { CategroyComponent } from './admin/pages/categroy/categroy.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DefaultLayoutComponent } from './layouts/default-layout/default-layout.component';
import { CreateCategoryComponent } from './admin/pages/Categroy/create-category/create-category.component';

const routes: Routes = [
  {
    path: '',
    component: DefaultLayoutComponent,
    children:[
    ],
  },
  {
    path: 'admin',
    component: DefaultLayoutComponent,
    children:[
      { path: 'categories/create', component: CreateCategoryComponent},
      { path: 'categories', component: CategroyComponent},
    ],
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
