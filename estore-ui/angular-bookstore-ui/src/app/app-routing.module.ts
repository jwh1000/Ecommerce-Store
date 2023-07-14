import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductUserInventoryComponent } from './product-user-inventory/product-user-inventory.component';
import { ProductUserDetailComponent } from './product-user-detail/product-user-detail.component';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';

const routes: Routes = [
  { path: 'admin', component: AdminButtonsComponent},
  { path: 'product', component: ProductUserInventoryComponent},
  { path: 'user-detail/:id', component: ProductUserDetailComponent}
]

@NgModule({
  declarations: [],
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
