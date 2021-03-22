import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  userName = 'Andreyka Sorocovici';
  email = 'andreyka.sorocovicika@gmail.com';
  phoneNr = '+31648679065';
  employed = 'yes';
  married = 'no';
  children = '1';
  constructor() { }

  ngOnInit(): void {
  }

}
