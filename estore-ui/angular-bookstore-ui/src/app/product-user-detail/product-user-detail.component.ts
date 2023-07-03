import { Component, OnInit } from '@angular/core';
import { Product } from '../product';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';
import { ProductService } from '../product.service';

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
    private location: Location
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
}
