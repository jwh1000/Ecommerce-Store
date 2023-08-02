import { Component } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
  selector: 'app-admin-buttons',
  templateUrl: './admin-buttons.component.html',
  styleUrls: ['./admin-buttons.component.css']
})
/**
 * Class to control the admin buttons within the admin page.
 * @author Rylan Arbour
 */
export class AdminButtonsComponent {
  /**
   * These three booleans control which view is visible.
   */
  displayProducts: boolean = false;
  displayCreate: boolean = false;
  displayDelete: boolean = false;
  displayDeleteDiscount: boolean = false;

  /**
   * When the create product button is hit, display the view for creating a product.
   */
  createProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = true;
    this.displayDelete = false;
    this.displayDeleteDiscount = false;
  }

  /**
   * When the edit product button is hit, display the view for editing a product.
   */
  editProductAction(): void {
    this.displayProducts = true;
    this.displayCreate = false;
    this.displayDelete = false;
    this.displayDeleteDiscount = false;
  }

  /**
   * When the delete product button is hit, display the view for deleting a product.
   */
  deleteProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = false;
    this.displayDelete = true;
    this.displayDeleteDiscount = false;
  }

  /**
   * When the delete discount button is hit, display the view for deleting a discount.
   */
  deleteDiscountAction(): void {
    this.displayProducts = false;
    this.displayCreate = false;
    this.displayDelete = false;
    this.displayDeleteDiscount = true;
  }
}
