import { Component } from '@angular/core';
import { Product } from '../product';
import { PRODUCTS } from '../mock-products';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {
  products = PRODUCTS;
  selectedProduct?: Product;

  onSelect(product: Product): void {
    this.selectedProduct = product;
  }
}
