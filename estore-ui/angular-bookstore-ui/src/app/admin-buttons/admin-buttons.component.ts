import { Component } from '@angular/core';

@Component({
  selector: 'app-admin-buttons',
  templateUrl: './admin-buttons.component.html',
  styleUrls: ['./admin-buttons.component.css']
})
export class AdminButtonsComponent {
  displayProducts: boolean = false;

  createProductAction(): void {

  }

  editProductAction(): void {
    this.displayProducts = true;
  }

  deleteProductAction(): void {
    
  }
}
