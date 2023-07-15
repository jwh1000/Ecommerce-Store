import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from './product'

import {Observable, of} from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class CartService {

  private productURL = 'http://localhost:8080/estore';
  private user = 'admin';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { 
  }

  addToCart(product: Product): Observable<Product> {
    const url = `${this.productURL}/carts/${this.user}/product`
    return this.http.post<Product>(url,product,this.httpOptions).pipe(
      tap(),
      catchError(this.handleError<Product>('addToCart'))
    );
  }

  removeFromCart(product: Product): Observable<Product> {
    const url = `${this.productURL}/carts/${this.user}/product/${product.id}`
    return this.http.delete<Product>(url).pipe(
      tap(),
      catchError(this.handleError<Product>('addToCart'))
    );
  }

private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error);
    return of(result as T);
  };
}

}