import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from "@angular/common/http";
import {catchError, map, Observable, throwError} from "rxjs";
import {Qrcode} from "./qrcode";
import {error} from "@angular/compiler-cli/src/transformers/util";

@Injectable({
  providedIn: 'root'
})
export class QrCodeStoreService {

  private apiUrl = "http://localhost:8080/api"

  constructor(private http: HttpClient) {
  }

  getAll(): Observable<Qrcode[]> {
    console.log(this.http.get<Qrcode[]>(`${this.apiUrl}/qrcodes`))
    return this.http.get<Qrcode[]>(`${this.apiUrl}/qrcodes`)
  }


  //http://localhost:8080/api/qrcodes?value=3&no=15
  addVoucher(value: number, count: number): Observable<any> {
    const headers = new HttpHeaders().set('Accept', 'text/plain');
    return this.http.post(`${this.apiUrl}/qrcodes?value=${value}&no=${count}`, {}, { headers, responseType: 'text' })
      .pipe(
        catchError(this.handleError)
      );
  }

  private handleError(error: HttpErrorResponse) {
    console.error('Error processing action', error);
    return throwError(error);
  }
}
