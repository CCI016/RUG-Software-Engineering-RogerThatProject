import { Component, OnInit } from '@angular/core';
import { UserIDService } from '@app/services/user-id.service';
import { WebRequestService } from '@app/services/web.service';
import { Transaction } from '@app/shared/models/transactions';
import { NzModalService } from 'ng-zorro-antd/modal';



@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {

  userId: string;
  transactions : Transaction[];
  isVisible=false;
  value : [];
  selectedValue = [
  { label: '', value: ''},
  { label: '', value: ''},
  { label: '', value: ''},
  { label: '', value: ''}
];
  optionList = [
    { label: 'Unknown', value: 'UNKNOWN'},
    { label: 'Entertainment', value: 'ENTERTAINMENT'},
    { label: 'Housing', value: 'HOUSING'},
    { label: 'Groceries', value: 'GROCERIES'},
    { label: 'Eating', value: 'EATING_OUT'},
    { label: 'Clothes', value: 'CLOTHES'},
    { label: 'Other', value: 'OTHER'},
  ];

  constructor(
    private webService : WebRequestService,
    private modal: NzModalService,
    private messageService : UserIDService,
  ) { }

  ngOnInit(): void {
    this.value = [];
    this.messageService.currentMessage.subscribe(message => this.userId = message);
  }

  downloadOverview() {
    console.log("BB");
    this.webService.getData("rest/transaction/downloadOverview?id=" + this.userId).subscribe(
      data => {
        console.log("AA");
      }
    )
  }

  categorizeTransactions() {
    this.webService.getData("rest/transaction/getById?id=" + this.userId).subscribe(
      data => {
        this.transactions = data as Transaction[];
        if (this.transactions.length > 0) {
          this.isVisible = true;
        }
      }
    )
  }

  handleCancel() {
    this.isVisible = false;
  }

  handleOk() {
    this.webService.postData("rest/transaction/updateCategorization?", this.transactions).subscribe();
    this.isVisible = false;
  }


  log(value: any, id : number): void {
    this.transactions[id].spendingCategory = value.target.value;
  }

}


