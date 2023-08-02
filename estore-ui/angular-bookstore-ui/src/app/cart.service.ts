import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from './product'

import {Observable, of} from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { AppComponent } from './app.component';
import { LoginStateService } from './login-state.service';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  /*
  *Url and user are defined here
  */
  private productURL = 'http://localhost:8080/estore';
  private user = this.loginStateService.getUsername();

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private loginStateService: LoginStateService) { 
  }

  /*
  *Add to cart takes in product and alters url so post mapping adds specified product to cart
  */
  addToCart(product: Product): Observable<Product> {
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/carts/${this.user}/product`
    return this.http.post<Product>(url,product,this.httpOptions).pipe(
      tap(),
      catchError(this.handleError<Product>('addToCart'))
    );
  }

  /*
  *Remove from cart takes in product and alters url so delete mapping removes 
  *specified product
  */
  removeFromCart(product: Product): Observable<Product> {
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/carts/${this.user}/product/${product.id}`
    return this.http.delete<Product>(url).pipe(
      tap(),
      catchError(this.handleError<Product>('addToCart'))
    );
  }

  /*
  * gets all contents of the cart
  */
  getCartContents(): Observable<Product[]> {
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/carts/${this.user}`;
    let products = this.http.get<Product[]>(url).pipe(
      tap(),
      catchError(this.handleError<Product[]>('getProducts'))
    );
    console.log(products);
    return products;
  }

  /*
  * clears all items in the cart
  */
  clearCart(): Observable<Product[]> {
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/carts/${this.user}`;
    let products = this.http.delete<Product[]>(url).pipe(tap(), 
      catchError(this.handleError<Product[]>('addToCart'))
    );
    return products;
  }

  /* 
  *Handles errors
  */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
    console.error(error);
    return of(result as T);
  };
  }
}