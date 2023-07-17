import { Component } from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'Bookstore';

  //INCREDIBLY JANKY
  static username?: string;

  public static setUsername(username?: string) {
    this.username = username;
    console.log('set username:' + this.username)
  }

  public static getUsername() {
    console.log('get username:' + this.username)
    return this.username;
  }
}
