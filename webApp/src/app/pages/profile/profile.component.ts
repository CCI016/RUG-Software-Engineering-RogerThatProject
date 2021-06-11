import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  firstName = 'John';
  lastName = 'Sorocovici';
  userName = this.firstName + ' ' + this.lastName;
  email = 'john.sorocovicika@gmail.com';
  phoneNr = '+31648679065';
  employed = 'Yes';
  married = 'No';
  children = '1';
  genderMale = 'Male';
  genderFemale = 'Female';
  address = 'Oude Ebbigenstraat 10C';

  constructor() { }

  ngOnInit(): void {
  }

}
