import { Component } from '@angular/core';
import { Product } from '../product';
import { PRODUCTS } from '../mock-products';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {
  selectedProduct?: Product;

  onSelect(product: Product): void {
    this.selectedProduct = product;
  }

  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getHeroes();
  }

  getHeroes(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

}
