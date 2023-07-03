import { Component, OnInit } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
  selector: 'app-product-user-inventory',
  templateUrl: './product-user-inventory.component.html',
  styleUrls: ['./product-user-inventory.component.css']
})
export class ProductUserInventoryComponent implements OnInit{
    products: Product[] = [];
  
    constructor(private productService: ProductService){}

    ngOnInit(): void {
      this.getProducts()
    }
  
    getProducts(): void {
      this.productService.getProducts()
      .subscribe(products => this.products = products)
    }
  }

