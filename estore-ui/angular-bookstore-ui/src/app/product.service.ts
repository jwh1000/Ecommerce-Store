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
  /*
  *Uses get mapping to get all products in inventory
  */
  getProducts(): Observable<Product[]>{
    const url = `${this.productURL}/inventory`;
    return this.http.get<Product[]>(url).pipe(tap(),
      catchError(this.handleError<Product[]>('getProducts', []))
    )
  }
  /*
  *Uses get mappint to get specific product in inventory
  */
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