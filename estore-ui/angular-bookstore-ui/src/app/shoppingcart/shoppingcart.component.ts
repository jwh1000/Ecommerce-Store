import { Component, OnInit } from '@angular/core';
import { LoginStateService } from '../login-state.service';
import { CartService } from '../cart.service';
import { Product } from '../product';
import { ProductService } from '../product.service';

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
  ){}
  username?: String;

  ngOnInit():  void {
    this.username = this.loginStateService.getUsername();
    this.getCartContents()
  }

  getCartContents(): void {
    this.cartService.getCartContents()
    .subscribe(cartproducts => this.cartproducts = this.cartproducts)
  }
  

  
}
