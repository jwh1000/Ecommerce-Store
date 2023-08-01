import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user';
import { LoginStateService } from '../login-state.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  usernameInput?: string;
  success: boolean = false;

  constructor(
    private userService: UserService,
    private loginStateService: LoginStateService,
  ) {}

  login(): void {
    if (this.usernameInput) {
      this.userService.authenticateUser(this.usernameInput).subscribe();
      this.userService.createUser({ username:this.usernameInput } as User).subscribe(); //This line of code doesn't always need to run but idk how do make it only run when the above line gets a 404 in response
      this.loginStateService.setState();
      this.loginStateService.setUsername(this.usernameInput);

      this.success = this.loginStateService.success;
      this.loginStateService.loggedIn()
    }
  }

  logout(): void {
    this.loginStateService.resetState();
    this.loginStateService.setUsername('');

    this.success = this.loginStateService.success;
    this.loginStateService.loggedIn()
  }
}
