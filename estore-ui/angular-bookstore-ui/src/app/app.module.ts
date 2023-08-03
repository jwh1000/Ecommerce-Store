import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import { FormsModule } from '@angular/forms';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { AppRoutingModule } from './app-routing.module';
import { InventorySearchComponent } from './inventory-search/inventory-search.component';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';
import { NewProductComponent } from './new-product/new-product.component';
import { DeleteProductComponent } from './delete-product/delete-product.component';
import { LoginComponent } from './login/login.component';
import { NavigationComponent } from './navigation/navigation.component';

import { ProductUserDetailComponent } from './product-user-detail/product-user-detail.component';
import { ProductUserInventoryComponent } from './product-user-inventory/product-user-inventory.component';
import { PurchaseHistoryComponent } from './purchase-history/purchase-history.component';
import { AdminPageComponent } from './admin-page/admin-page.component';
import { ShoppingcartComponent } from './shoppingcart/shoppingcart.component';
import { DeleteDiscountComponent } from './delete-discount/delete-discount.component';
import { CreateDiscountComponent } from './create-discount/create-discount.component';


@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    ProductDetailComponent,
    ProductUserDetailComponent,
    ProductUserInventoryComponent,
    InventorySearchComponent,
    AdminButtonsComponent,
    NewProductComponent,
    DeleteProductComponent,
    LoginComponent,
    NavigationComponent,
    PurchaseHistoryComponent,
    AdminPageComponent,
    ShoppingcartComponent,
    DeleteDiscountComponent,
    CreateDiscountComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [LoginComponent,NavigationComponent],
  bootstrap: [AppComponent]
})
export class AppModule { }
