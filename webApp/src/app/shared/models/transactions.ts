import { spendingCategory } from "./spendingCategory";

export class Transaction {

  id : number;
  dateTime : string;
  name : string;
  accountFrom : string;
  accountTo : string;
  code : string;
  inOrOut : string;
  amount : string;
  transactionType : string;
  transactionCategory : string;
  notes : string;
  word : string;
  spendingCategory : spendingCategory;

}
