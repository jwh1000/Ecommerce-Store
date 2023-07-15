import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ProductService } from '../product.service';
import { CartService } from '../cart.service';

@Component({
  selector: 'app-product-user-detail',
  templateUrl: './product-user-detail.component.html',
  styleUrls: ['./product-user-detail.component.css']
})
export class ProductUserDetailComponent implements OnInit{
  product: Product | undefined

  constructor(
    private route: ActivatedRoute,
    private productService: ProductService,
    private location: Location,
    private cartService: CartService
  ) {}

  ngOnInit(): void {
    this.getProduct();
  }
  
  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
    .subscribe(product => this.product = product);
  }
  goBack(): void{
    this.location.back();
  }

  add(product: Product):void {
    this.cartService.addToCart(product).subscribe();
  }

  delete(product: Product):void {
    this.cartService.removeFromCart(product).subscribe();
  }

}
