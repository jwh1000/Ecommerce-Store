import { Component, Input } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
/**
 * Class to display the details of a product, that are editable.
 * Only meant to be viewed by an admin.
 * @author Rylan Arbour
 */
export class ProductDetailComponent {
  @Input() product?: Product;

  constructor(
    private productService: ProductService
  ) {}

  /**
   * Function to save the changes made to the details of the product, using the product service.
   */
  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product).subscribe();
    }
  }
}
