import { Component } from '@angular/core';
import { ProductService } from '../product.service';
import { Product } from '../product';

@Component({
  selector: 'app-admin-buttons',
  templateUrl: './admin-buttons.component.html',
  styleUrls: ['./admin-buttons.component.css']
})
export class AdminButtonsComponent {
  displayProducts: boolean = false;
  displayCreate: boolean = false;
  displayDelete: boolean = false;

  createProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = true;
    this.displayDelete = false;
  }

  editProductAction(): void {
    this.displayProducts = true;
    this.displayCreate = false;
    this.displayDelete = false;
  }

  deleteProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = false;
    this.displayDelete = true;
  }
}
