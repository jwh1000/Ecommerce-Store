import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { DiscountCode } from './discountcode';
import { Observable, catchError, of, tap } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class DiscountCodeService {
  private discountURL = 'http://localhost:8080/estore/discount-code';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { 
  }

  /** GET: gets all discounts from the server */
  getDiscountCodes(): Observable<DiscountCode[]>{
    const url = `${this.discountURL}/codes`;
    return this.http.get<DiscountCode[]>(url).pipe(tap(),
      catchError(this.handleError<DiscountCode[]>('getDiscountCodes', []))
    )
  }

  /** GET: gets one discounts from the server */
  getDiscountCode(code: String): Observable<DiscountCode>{
    const url = `${this.discountURL}/?code=${code}`;
    return this.http.get<DiscountCode>(url).pipe(tap(),
      catchError(this.handleError<DiscountCode>('getDiscountCodes'))
    )
  }

  /** GET: gets one discounts from the server */
  deleteDiscountCode(code: String): Observable<DiscountCode[]>{
    const url = `${this.discountURL}/?code=${code}`;
    return this.http.delete<DiscountCode[]>(url).pipe(tap(),
      catchError(this.handleError<DiscountCode[]>('getDiscountCodes', []))
    )
  }

  /** POST: add a new discount to the server */
  createDiscountCode(discountcode: DiscountCode): Observable<DiscountCode> {
    const url = `${this.discountURL}/codes`;
    return this.http.post<DiscountCode>(url, discountcode, this.httpOptions).pipe(tap(),
      catchError(this.handleError<DiscountCode>('addProduct'))
    );
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
