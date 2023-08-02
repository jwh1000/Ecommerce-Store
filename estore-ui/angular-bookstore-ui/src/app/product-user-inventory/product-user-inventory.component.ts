import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { CartService } from '../cart.service'
import { Location } from '@angular/common';

@Component({
  selector: 'app-product-user-inventory',
  templateUrl: './product-user-inventory.component.html',
  styleUrls: ['./product-user-inventory.component.css']
})
export class ProductUserInventoryComponent implements OnInit{
    products: Product[] = [];
    /*
    *Constructor initializes product and cart service
    */
    constructor(
      private productService: ProductService,
      private cartService: CartService,
      private location: Location
    ){}
    
    /*
    *on initialize gets products
    */
    ngOnInit(): void {
      this.getProducts()
    }
    /*
    *Uses product service to retrieve products
    */
    getProducts(): void {
      this.productService.getProducts()
      .subscribe(products => this.products = products)
    }
    /*
    *Uses cart service to add to users cart
    */
    add(product: Product):void {
      this.cartService.addToCart(product).subscribe();
    }
    /*
    *Uses cart service to remove from users cart
    */
    delete(product: Product):void {
      this.cartService.removeFromCart(product).subscribe();
    }

    goBack(): void {
      this.location.back();
    }
  }

