import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';

import { LoginComponent } from './login/login.component';
import { NavigationComponent } from './navigation/navigation.component';

import { ProductUserInventoryComponent } from './product-user-inventory/product-user-inventory.component';
import { ProductUserDetailComponent } from './product-user-detail/product-user-detail.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';


/*
*routes to different pages for app routing
*/
const routes: Routes = [
  { path: 'login', component: LoginComponent},
  { path: 'main', component: NavigationComponent},
  { path: 'admin', component: AdminButtonsComponent},

  { path: '', redirectTo: '/login', pathMatch: 'full' },

  { path: '', redirectTo: '/user-product', pathMatch: 'full' },
  { path: 'user-product', component: ProductUserInventoryComponent},
  { path: 'user-detail/:id', component: ProductUserDetailComponent},
  { path: 'purchase-history', component: PurchaseHistoryComponent}

]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
