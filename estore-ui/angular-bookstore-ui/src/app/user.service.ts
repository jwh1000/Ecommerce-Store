import { Injectable } from '@angular/core';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Observable, of} from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private userURL = 'http://localhost:8080/estore';

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient) { 
  }

  getUsers(): Observable<User[]>{
    const url = `${this.userURL}/users`;
    return this.http.get<User[]>(url).pipe(tap(),
      catchError(this.handleError<User[]>('getUsers', []))
    );
  }

  getUser(id: number) : Observable<User> {
    const url = `${this.userURL}/users/user/${id}`;
    return this.http.get<User>(url).pipe(tap(),
      catchError(this.handleError<User>(`getUser id=${id}`))
    );
  }

  /** POST: add a new user to the server */
  createUser(user: User): Observable<User> {
    const url = `${this.userURL}/users/user`;
    return this.http.post<User>(url, user, this.httpOptions).pipe(tap(),
      catchError(this.handleError<User>('createUser'))
    );
  }

  authenticateUser(username: String): Observable<User> {
    const url = `${this.userURL}/users/auth/${username}`;
    return this.http.get<User>(url).pipe(tap(),
      catchError(this.handleError<User>('getUsers'))
    );
  }


  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {
      console.error(error);
      return of(result as T);
    };
  }
}
