import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { UserIDService } from '@app/services/user-id.service';
import { WebRequestService } from '@app/services/web.service';
import { UserModel } from '@app/shared/models/userModel';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  
  userId : string;
  user : UserModel;


  constructor(
    private webService: WebRequestService,
    private messageService : UserIDService,
    private router: Router,
    ) {
  }

  ngOnInit(): void {

    this.messageService.currentMessage.subscribe(message => this.userId = message);
    if (this.userId == "null") {
      const returnUrl = '/login';
      this.router.navigate([returnUrl]);
    } else {
      this.webService.getData("rest/profile/info?userId=" + this.userId).subscribe(
        data => {
          this.user = data as UserModel;
        }
      )
    }

  }


}
