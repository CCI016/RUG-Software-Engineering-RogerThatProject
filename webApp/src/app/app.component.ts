import { Component } from '@angular/core';
import { Router } from '@angular/router';

import { AuthenticationService } from './_services';
import { User } from './_models';
import { AuthGuard } from '@app/_helpers';
import { UserIDService } from './services/user-id.service';

@Component({ selector: 'app', templateUrl: 'app.component.html' ,styleUrls: ['./app.component.css'] })
export class AppComponent {
  isCollapsed = false;
  currentUser: User;

  constructor(
    public router: Router,
    private authenticationService: AuthenticationService,
    private messageService : UserIDService,
  ) {
    this.authenticationService.currentUser.subscribe(x => this.currentUser = x);
  }
  logout() {
    this.messageService.changeUserId("null");
    this.authenticationService.logout();
    this.router.navigate(['/login']);
  }

}
