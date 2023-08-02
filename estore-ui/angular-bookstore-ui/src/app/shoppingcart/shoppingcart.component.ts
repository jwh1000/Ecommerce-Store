import { Component, OnInit } from '@angular/core';
import { LoginStateService } from '../login-state.service';
import { CartService } from '../cart.service';
import { Product } from '../product';
import { Location } from '@angular/common';

@Component({
  selector: 'app-shoppingcart',
  templateUrl: './shoppingcart.component.html',
  styleUrls: ['./shoppingcart.component.css']
})
export class ShoppingcartComponent implements OnInit{
  cartproducts: Product[] = [];
  constructor(
    private loginStateService: LoginStateService,
    private cartService: CartService,
    private location: Location
  ){}
  username?: String;
  total?: number = 0.0;
  empty?: boolean = true;

  ngOnInit(): void {
    this.getCartContents()
    this.getTotal();
  }

  getCartContents(): void {
    this.username = this.loginStateService.getUsername();
    this.cartService.getCartContents().subscribe(cartproducts => {
      this.cartproducts = cartproducts;
      this.getTotal();
      if (this.cartproducts.length == 0) {
        this.empty = true;
      } else {
        this.empty = false;
      }
    });

    console.log(this.cartproducts);
  }

  /*
  *Uses product service to retrieve products
  */
  getContents(): void {
    this.cartService.getCartContents()
    .subscribe(products => this.cartproducts = products)
  }

  checkOut(): void {
    this.getContents();

    this.cartService.clearCart().subscribe(products => this.cartproducts = products);
    this.ngOnInit();
  }

  
  goBack(): void {
    this.location.back();
  }

  getTotal(): void {
    this.total = 0;

    for (var product of this.cartproducts) {
      this.total += product.price;
    }
  }
}
