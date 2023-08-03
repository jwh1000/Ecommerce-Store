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
   * These five booleans control which view is visible.
   * TODO: This should probably be an enum instead.
   */
  displayProducts: boolean = false;
  displayCreate: boolean = false;
  displayDelete: boolean = false;
  displayCreateDiscount: boolean = false;
  displayDeleteDiscount: boolean = false;

  /**
   * When the create product button is hit, display the view for creating a product.
   */
  createProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = true;
    this.displayDelete = false;
    this.displayCreateDiscount = false;
    this.displayDeleteDiscount = false;
  }

  /**
   * When the edit product button is hit, display the view for editing a product.
   */
  editProductAction(): void {
    this.displayProducts = true;
    this.displayCreate = false;
    this.displayDelete = false;
    this.displayCreateDiscount = false;
    this.displayDeleteDiscount = false;
  }

  /**
   * When the delete product button is hit, display the view for deleting a product.
   */
  deleteProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = false;
    this.displayDelete = true;
    this.displayCreateDiscount = false;
    this.displayDeleteDiscount = false;
  }

  /**
   * When the create discount button is hit, display the view for creating a discount code.
   */
  createDiscountAction(): void {
    this.displayProducts = false;
    this.displayCreate = false;
    this.displayDelete = false;
    this.displayCreateDiscount = true;
    this.displayDeleteDiscount = false;
  }

  /**
   * When the delete discount button is hit, display the view for deleting a discount code.
   */
  deleteDiscountAction(): void {
    this.displayProducts = false;
    this.displayCreate = false;
    this.displayDelete = false;
    this.displayCreateDiscount = false;
    this.displayDeleteDiscount = true;
  }
}
