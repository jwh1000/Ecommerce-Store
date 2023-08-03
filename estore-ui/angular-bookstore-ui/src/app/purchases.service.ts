import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from './product'
import {Observable, of} from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';
import { LoginStateService } from './login-state.service';

@Injectable({
  providedIn: 'root'
})
export class PurchasesService {

  private productURL = 'http://localhost:8080/estore';
  private user = ""

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private loginStateService: LoginStateService) { 
  }

  /** GET: gets all the products from the server */
  getPurchases(): Observable<Product[]>{
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/purchases/${this.user}`;
    return this.http.get<Product[]>(url).pipe(tap(),
      catchError(this.handleError<Product[]>('getProducts', []))
    )
  }

  /** GET: gets a specific product with a specified id from the server */
  getPurchase(id: number) : Observable<Product> {
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/purchases/${this.user}/{id}`;
    return this.http.get<Product>(url).pipe(tap(),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /*
  *Uses get mapping to search for specified product
  */
  searchProduct(term: string): Observable<Product[]> {
    this.user = this.loginStateService.getUsername();
    if (!term.trim()) {
      return of([]);
    }
    const url = `${this.productURL}/purchases/${this.user}`
    return this.http.get<Product[]>(url).pipe(
      tap(),
      catchError(this.handleError<Product[]>('searchProducts', []))
    );
  }

  /** POST: add a new product to the server */
  addProduct(product: Product): Observable<Product> {
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/purchases/${this.user}`;
    return this.http.post<Product>(url, product, this.httpOptions).pipe(tap(),
      catchError(this.handleError<Product>('addProduct'))
    );
  }

  /** DELETE: delete the product from the server */
  deleteProduct(product: Product): Observable<Product> {
    this.user = this.loginStateService.getUsername();
    const url = `${this.productURL}/purchases/${this.user}/{id}`;

    return this.http.delete<Product>(url, this.httpOptions).pipe(tap(),
      catchError(this.handleError<Product>('deleteProduct'))
    );
  }


  /*
  *Handels errors
  */
private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error);
    return of(result as T);
  };
}

}