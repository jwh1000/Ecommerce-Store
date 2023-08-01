import { Component } from '@angular/core';
import { AppComponent } from '../app.component';
import { Router } from '@angular/router';
import { LoginStateService } from '../login-state.service';

@Component({
  selector: 'app-navigation',
  templateUrl: './navigation.component.html',
  styleUrls: ['./navigation.component.css']
})
export class NavigationComponent {
  constructor(
    private router: Router,
    private loginStateService: LoginStateService
  ){ }

  username?: String;
  success?: boolean;

  ngOnInit(): void {
    this.username = this.loginStateService.getUsername();
  }

  logOut(): void {
    this.loginStateService.resetState();
    this.router.navigate(['/login']);
 }
}
