import { Component } from '@angular/core';
import { ProductService } from '../product.service';
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
    private productService: ProductService,
    private cartService: CartService,
    private location: Location
  ) {}

    /*
    *on initialize gets products
    */
    ngOnInit(): void {
      this.getContents()
    }
    /*
    *Uses product service to retrieve products
    */
    getContents(): void {
      this.cartService.getCartContents()
      .subscribe(products => this.products = products)
    }

    delete(product: Product):void {
      this.cartService.removeFromCart(product).subscribe();
    }

    goBack(): void {
      this.location.back();
    }
}
