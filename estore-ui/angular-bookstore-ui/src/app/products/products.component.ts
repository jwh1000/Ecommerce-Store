import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
/**
 * Class to represent a list of all the products that exist in the store.
 * @author Rylan Arbour
 */
export class ProductsComponent {
  selectedProduct?: Product;

  onSelect(product: Product): void {
    this.selectedProduct = product;
  }

  products: Product[] = [];

  constructor(private productService: ProductService) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

}
