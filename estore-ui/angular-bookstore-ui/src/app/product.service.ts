import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from './product'
import { PRODUCTS } from './mock-products';
import {Observable, of} from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  private productURL = 'http://localhost:8080/estore';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { 
  }

  getProducts(): Observable<Product[]>{
    const url = `${this.productURL}/inventory`;
    return this.http.get<Product[]>(url).pipe(tap(),
      catchError(this.handleError<Product[]>('getProducts', []))
    )
  }

  getProduct(id: number) : Observable<Product> {
    const url = `${this.productURL}/inventory/product/${id}`;
    return this.http.get<Product>(url).pipe(tap(),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /** PUT: update the product on the server */
  updateProduct(product: Product): Observable<any> {
    const url = `${this.productURL}`;
    return this.http.put(url, product, this.httpOptions).pipe(tap(),
      catchError(this.handleError<any>('updateProduct'))
    );
  }

  /** POST: add a new product to the server */
  addProduct(product: Product): Observable<Product> {
    const url = `${this.productURL}/inventory/product`;
    return this.http.post<Product>(url, product, this.httpOptions).pipe(tap(),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /** DELETE: delete the product from the server */
  deleteProduct(id: number): Observable<Product> {
    const url = `${this.productURL}/inventory/product/${id}`;

    return this.http.delete<Product>(url, this.httpOptions).pipe(tap(),
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }


private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error);
    return of(result as T);
  };
}

}