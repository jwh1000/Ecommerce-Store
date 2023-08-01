import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';

import { LoginComponent } from './login/login.component';
import { NavigationComponent } from './navigation/navigation.component';

import { ProductUserInventoryComponent } from './product-user-inventory/product-user-inventory.component';
import { ProductUserDetailComponent } from './product-user-detail/product-user-detail.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { ShoppingcartComponent } from './shoppingcart/shoppingcart.component';
import { CheckOutButtonComponent } from './check-out-button/check-out-button.component';


/*
*routes to different pages for app routing
*/
const routes: Routes = [
  { path: 'main', component: NavigationComponent},
  { path: 'admin', component: AdminPageComponent},

  { path: 'login', component: LoginComponent},
  { path: '', redirectTo: '/login', pathMatch: 'full' },

  { path: 'user-product', component: ProductUserInventoryComponent},
  { path: 'user-detail/:id', component: ProductUserDetailComponent},
  { path: 'user-detail/:id', component: ShoppingcartComponent},

  { path: 'cart', component: CheckOutButtonComponent}


]

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
