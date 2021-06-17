import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class WebRequestService {
  readonly ROOT_URL =  'http://localhost:8080'; //We can change the port of our back in application prop

  constructor(private http: HttpClient) {
  }

  getData(uri : string) : Observable<any>{ 
    return this.http.get(`${this.ROOT_URL}/${uri}`);
  }

  postData(uri : string, t : any) : Observable<any> {
    return this.http.post(`${this.ROOT_URL}/${uri}`, t);
  }

  getOctetStream(URL: string) {
    const httpOptions = {
        headers: new HttpHeaders({
            Accept: 'application/octet-stream',
        }),
        responseType: 'blob' as 'blob',
        // withCredentials: true,
    };

    return this.http.get(`${this.ROOT_URL}/${URL}`, httpOptions).toPromise();
}

}