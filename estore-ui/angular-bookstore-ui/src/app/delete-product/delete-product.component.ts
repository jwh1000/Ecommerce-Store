import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-delete-product',
  templateUrl: './delete-product.component.html',
  styleUrls: ['./delete-product.component.css']
})
/**
 * Class that controls the view of deleting a product as an admin.
 * @author Rylan Arbour
 */
export class DeleteProductComponent {
  products: Product[] = [];

  constructor(private productService: ProductService) { }

  //Get list of all products on initalization.
  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  /**
   * Deletes a product by calling the product service.
   * @param product The product to be deleted.
   */
  delete(product: Product): void {
    this.productService.deleteProduct(product.id).subscribe(
      (data) =>{
        this.ngOnInit(); //Reload this component after deleting.
      }
    );
  }
}
