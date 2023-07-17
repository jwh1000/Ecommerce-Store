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
  /*
  *Uses product service to get a product of specified id
  */
  getProduct(): void {
    const id = Number(this.route.snapshot.paramMap.get('id'));
    this.productService.getProduct(id)
    .subscribe(product => this.product = product);
  }
  /*
  *Returns user to previous page
  */
  goBack(): void{
    this.location.back();
  }
  /*
  *Uses cart service to add product to users cart
  */
  add(product: Product):void {
    this.cartService.addToCart(product).subscribe();
  }
  /*
  *Uses cart service to remove product from users cart
  */
  delete(product: Product):void {
    this.cartService.removeFromCart(product).subscribe();
  }

}
