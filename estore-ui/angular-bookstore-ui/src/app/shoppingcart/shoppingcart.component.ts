import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-shoppingcart',
  template: `
    <p>
      shoppingcart works!
    </p>
  `,
  styleUrls: ['./shoppingcart.component.css']
})
export class ShoppingcartComponent {

  /*
    *Constructor initializes product and cart service
    */
    constructor(
      private location: Location
    ){}
  
}
