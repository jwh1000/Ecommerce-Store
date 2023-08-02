import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Product } from './product'
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

  /** GET: gets all the products from the server */
  getProducts(): Observable<Product[]>{
    const url = `${this.productURL}/inventory`;
    return this.http.get<Product[]>(url).pipe(tap(),
      catchError(this.handleError<Product[]>('getProducts', []))
    )
  }

  /** GET: gets a specific product with a specified id from the server */
  getProduct(id: number) : Observable<Product> {
    const url = `${this.productURL}/inventory/product/${id}`;
    return this.http.get<Product>(url).pipe(tap(),
      catchError(this.handleError<Product>(`getProduct id=${id}`))
    );
  }

  /*
  *Uses get mapping to search for specified product
  */
  searchProduct(term: string): Observable<Product[]> {
    if (!term.trim()) {
      return of([]);
    }
    const url = `${this.productURL}/inventory/?name=${term}`
    return this.http.get<Product[]>(url).pipe(
      tap(),
      catchError(this.handleError<Product[]>('searchProducts', []))
    );
  }
  /** PUT: update the product on the server */
  updateProduct(product: Product): Observable<any> {
    const url = `${this.productURL}`;
    console.log("we here");
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