import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserIDService {

  private userId = new BehaviorSubject<string>("null");
  currentMessage = this.userId.asObservable();

  constructor() {}

  changeUserId(user : string) {
    this.userId.next(user);
  }
}
