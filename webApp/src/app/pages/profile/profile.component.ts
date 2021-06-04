import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  firstName = 'Andreyka';
  lastName = 'Sorocovici';
  userName = this.firstName + ' ' + this.lastName;
  email = 'andreyka.sorocovicika@gmail.com';
  phoneNr = '+31648679065';
  employed = 'yes';
  married = 'no';
  children = '1';
  genderMale = 'Male';
  genderFemale = 'Female';
  address = 'Oude Ebbigenstraat 10C';

  constructor() { }

  ngOnInit(): void {
  }

}
