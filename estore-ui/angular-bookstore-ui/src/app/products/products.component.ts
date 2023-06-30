import { Component } from '@angular/core';
import { Product } from '../product';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent {
  product: Product = {
    id: 1,
    name: 'Book 1',
    description: "Test",
    quantity: 10,
    price: 4.99
  };
}
