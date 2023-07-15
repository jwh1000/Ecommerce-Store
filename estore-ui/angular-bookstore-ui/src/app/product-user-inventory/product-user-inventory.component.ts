import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';
import { CartService } from '../cart.service'

@Component({
  selector: 'app-product-user-inventory',
  templateUrl: './product-user-inventory.component.html',
  styleUrls: ['./product-user-inventory.component.css']
})
export class ProductUserInventoryComponent implements OnInit{
    products: Product[] = [];
  
    constructor(private productService: ProductService,
      private cartService: CartService){}

    ngOnInit(): void {
      this.getProducts()
    }
  
    getProducts(): void {
      this.productService.getProducts()
      .subscribe(products => this.products = products)
    }

    add(product: Product):void {
      this.cartService.addToCart(product).subscribe();
    }

    delete(product: Product):void {
      this.cartService.removeFromCart(product).subscribe();
    }
  }

