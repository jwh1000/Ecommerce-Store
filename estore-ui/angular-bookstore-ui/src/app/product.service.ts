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

private handleError<T>(operation = 'operation', result?: T) {
  return (error: any): Observable<T> => {
    console.error(error);
    return of(result as T);
  };
}

}