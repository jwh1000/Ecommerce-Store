import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginStateService {
  success: boolean = false;
  username: string = '';

  constructor(
    private router: Router
  ) {}

  setUsername(name: string): void {
    this.username =  name;
  }

  getUsername(): string {
    return this.username;
  }

  setState(): void {
    this.success = true;
  }

  resetState(): void {
    this.success = false;
  }

  loggedIn(): void {
    this.router.navigate(["/main"]);
  }

  loggedOut(): void {
    this.router.navigate(["/login"]);
  }
}
