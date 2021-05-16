import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { AuthenticationService } from 'src/app/_services';
import { User } from 'src/app/_models';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  currentUser: User;
  userName = 'Andreyka Sorocovici';
  email = 'andreyka.sorocovicika@gmail.com';
  phoneNr = '+31648679065';
  employed = 'yes';
  married = 'no';
  children = '1';

  constructor() {
  }

  ngOnInit(): void {
  }

}
