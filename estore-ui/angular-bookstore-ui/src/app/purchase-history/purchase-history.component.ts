import { Component } from '@angular/core';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { PurchasesService } from '../purchases.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-purchase-history',
  templateUrl: './purchase-history.component.html',
  styleUrls: ['./purchase-history.component.css']
})
export class PurchaseHistoryComponent {
  selectedProduct?: Product;

  onSelect(product: Product): void {
    this.selectedProduct = product;
  }

  products: Product[] = [];

  constructor(private productService: ProductService, 
    private purchaseService: PurchasesService,
    private location: Location) { }

  ngOnInit(): void {
    this.getProducts();
  }

  getProducts(): void {
    this.purchaseService.getPurchases()
    .subscribe(products => this.products = products);
  }

  add(product: Product):void {
    this.purchaseService.addProduct(product).subscribe();
  }
  
  delete(product: Product):void {
    this.purchaseService.deleteProduct(product).subscribe();
  }

  goBack():void {
    this.location.back();
  }


}
