import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-new-product',
  templateUrl: './new-product.component.html',
  styleUrls: ['./new-product.component.css']
})
/**
 * Class to control the the view of adding a new product.
 * @author Rylan Arbour
 */
export class NewProductComponent {
  constructor(
    private productService: ProductService
  ) {}

  /**
   * Adds a new product by calling the product service.
   * @param name The name of the product to be added.
   * @param priceString The string of the price the product should have. 
   */
  add(name: string, priceString: string): void {
    name = name.trim();
    priceString = priceString.trim();
    if (!name) { return; }
    if (!priceString) { return; }
    var price = parseFloat(priceString);
    
    this.productService.addProduct({ name, price } as Product)
      .subscribe();
  }
}
