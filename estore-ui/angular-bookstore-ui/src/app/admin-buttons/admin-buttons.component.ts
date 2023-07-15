import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-buttons',
  templateUrl: './admin-buttons.component.html',
  styleUrls: ['./admin-buttons.component.css']
})
export class AdminButtonsComponent {
  displayProducts: boolean = false;
  displayCreate: boolean = false;

  createProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = true;
  }

  editProductAction(): void {
    this.displayProducts = true;
    this.displayCreate = false;
  }

  deleteProductAction(): void {
    this.displayProducts = false;
    this.displayCreate = false;
  }
}
