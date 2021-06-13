import { Component, OnInit } from '@angular/core';
import { WebRequestService } from '@app/services/web.service';
import { spendingCategory } from '@app/shared/models/spendingCategory';
import { Transaction } from '@app/shared/models/transactions';
import { NzModalService } from 'ng-zorro-antd/modal';



@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.css']
})
export class StatusComponent implements OnInit {

  userId = 1;
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
  ) { }

  ngOnInit(): void {
    this.value = [];
  }

  downloadOverview() {
    // Here the request to the server where the pdf file will be created and after that it will download
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
    console.log("Intra");
    this.webService.postData("/rest/transaction/updateCategorization?", this.transactions).subscribe();
    this.isVisible = false;
  }


  log(value: any, id : number): void {
    this.transactions[id].spendingCategory = value.target.value;
  }

}


