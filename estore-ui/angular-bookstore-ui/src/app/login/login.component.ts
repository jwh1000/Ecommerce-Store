import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { User } from '../user';
import { AppComponent } from '../app.component';
import { ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  usernameInput?: string;
  success: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private location: Location,
    private userService: UserService
  ) {}

  login(): void {
    if (this.usernameInput) {
      this.userService.authenticateUser(this.usernameInput).subscribe();
      this.userService.createUser({ username:this.usernameInput } as User).subscribe(); //This line of code doesn't always need to run but idk how do make it only run when the above line gets a 404 in response
      AppComponent.setUsername(this.usernameInput);
      this.location.go("main");
      this.success = true;
    }
  }

  logout(): void {
    AppComponent.setUsername(undefined);
    this.location.go("login");
    this.success = false;
  }
}
