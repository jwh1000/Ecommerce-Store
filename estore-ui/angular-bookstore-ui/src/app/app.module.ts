import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import { FormsModule } from '@angular/forms';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductUserDetailComponent } from './product-user-detail/product-user-detail.component';
import { ProductUserInventoryComponent } from './product-user-inventory/product-user-inventory.component';
import { AppRoutingModule } from './app-routing.module';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';
import { NewProductComponent } from './new-product/new-product.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    ProductDetailComponent,
    ProductUserDetailComponent,
    ProductUserInventoryComponent,
    AdminButtonsComponent,
    NewProductComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
