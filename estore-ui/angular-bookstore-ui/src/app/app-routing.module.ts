import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';

const routes: Routes = [
  { path: 'admin', component: AdminButtonsComponent},
  { path: '', redirectTo: '/user-product', pathMatch: 'full' },
]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
