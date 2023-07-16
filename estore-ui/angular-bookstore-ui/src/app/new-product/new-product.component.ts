import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
export class NewProductComponent {
  constructor(
    private productService: ProductService
  ) {}

  add(name: string, priceString: string): void {
    name = name.trim();
    priceString = name.trim();
    if (!name) { return; }
    if (!priceString) { return; }
    var price = parseFloat(priceString);
    
    this.productService.addProduct({ name, price } as Product)
      .subscribe();
  }
}
