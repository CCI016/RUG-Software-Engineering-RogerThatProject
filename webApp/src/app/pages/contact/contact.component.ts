import { Component, OnInit } from '@angular/core';
import { FormBuilder} from '@angular/forms';


@Component({
  selector: 'app-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.css']
})

export class ContactComponent implements OnInit {

  contactForm = this.formBuilder.group({
    name: '',
    emailAddress: '',
    message : ''
  });

  constructor(private formBuilder: FormBuilder) { }

  onSubmit(): void {
    // Process checkout data here
    //this.items = this.contact.clearAll();
    console.warn('Your message has been sent', this.contactForm.value);
    this.contactForm.reset();
  }
  ngOnInit():void  {
  }
}
