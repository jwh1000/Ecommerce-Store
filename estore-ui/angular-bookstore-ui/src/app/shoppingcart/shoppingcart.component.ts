import { Component, OnInit } from '@angular/core';
import { LoginStateService } from '../login-state.service';

@Component({
  selector: 'app-shoppingcart',
  templateUrl: './shoppingcart.component.html',
  styleUrls: ['./shoppingcart.component.css']
})
export class ShoppingcartComponent {
  constructor(
    private loginStateService: LoginStateService
  ){ }
  username?: String;

  ngOnInit():  void {
    this.username = this.loginStateService.getUsername();
  }
  
}
