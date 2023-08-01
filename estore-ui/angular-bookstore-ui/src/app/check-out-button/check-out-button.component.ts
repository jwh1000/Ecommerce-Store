import { Component } from '@angular/core';
import { CartService } from '../cart.service';
import { Location } from '@angular/common';
import { Product } from '../product';

@Component({
  selector: 'app-check-out-button',
  templateUrl: './check-out-button.component.html',
  styleUrls: ['./check-out-button.component.css']
})
export class CheckOutButtonComponent {
  products: Product[] = [];

  constructor(
    private cartService: CartService,
    private location: Location
  ) {}

    /*
    *on initialize gets products
    */
    ngOnUpdate(): void {
      this.getContents()
    }

    /*
    *Uses product service to retrieve products
    */
    getContents(): void {
      this.cartService.getCartContents()
      .subscribe(products => this.products = products)
      console.log(this.products)
    }

    checkOut(): void {
      this.getContents()
      for(var product of this.products) {
        this.cartService.removeFromCart(product).subscribe();
        console.log("deleted something");
      }
    }

    goBack(): void {
      this.location.back();
    }
}
