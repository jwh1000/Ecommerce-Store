import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { CartService } from '../cart.service'
import { Location } from '@angular/common';
import { DiscountCodeService } from '../discount-code.service';

@Component({
  selector: 'app-product-user-inventory',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent {
    products: Product[] = [];
    /*
    *Constructor initializes product and cart service
    */
    constructor(
      private productService: ProductService,
      private cartService: CartService,
      private discountService: DiscountCodeService,
      private location: Location
    ){}

    goBack(): void {
      this.location.back();
    }
}

