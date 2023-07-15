import { Component, Input } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-product-detail',
  templateUrl: './product-detail.component.html',
  styleUrls: ['./product-detail.component.css']
})
export class ProductDetailComponent {
  @Input() product?: Product;

  constructor(
    private productService: ProductService
  ) {}

  save(): void {
    if (this.product) {
      this.productService.updateProduct(this.product).subscribe();
    }
  }
}
