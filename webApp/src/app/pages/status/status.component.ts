import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {
  }

  downloadOverview() {
    // Here the request to the server where the pdf file will be created and after that it will download
  }
}
