import { CategroyComponent } from './admin/pages/categroy/categroy.component';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DefaultLayoutComponent } from './layouts/default-layout/default-layout.component';
import { CreateCategoryComponent } from './admin/pages/Categroy/create-category/create-category.component';
import { EditCategoryComponent } from './admin/pages/categroy/edit-category/edit-category.component';

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
      { path: 'categories/:id/edit', component: EditCategoryComponent}
    ],
  }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
