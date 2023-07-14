import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { ProductsComponent } from './products/products.component';
import { FormsModule } from '@angular/forms';
import { ProductDetailComponent } from './product-detail/product-detail.component';
import { ProductUserDetailComponent } from './product-user-detail/product-user-detail.component';
import { ProductUserInventoryComponent } from './product-user-inventory/product-user-inventory.component';
import { AppRoutingModule } from './app-routing.module';
import { AdminButtonsComponent } from './admin-buttons/admin-buttons.component';

@NgModule({
  declarations: [
    AppComponent,
    ProductsComponent,
    ProductDetailComponent,
    ProductUserDetailComponent,
    ProductUserInventoryComponent,
    AdminButtonsComponent
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
