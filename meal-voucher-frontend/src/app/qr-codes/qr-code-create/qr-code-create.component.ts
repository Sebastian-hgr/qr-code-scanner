import { Component } from '@angular/core';
import {Qrcode} from "../../shared/qrcode";

import {QrCodeStoreService} from "../../shared/qr-code-store.service";
import {catchError, count, Observable} from "rxjs";


@Component({
  selector: 'mvf-qr-code-create',
  templateUrl: './qr-code-create.component.html',
  styleUrl: './qr-code-create.component.css'
})
export class QrCodeCreateComponent {

  value: number = 0
  count: number = 0

  constructor(
    private service: QrCodeStoreService) { }


  addVoucher(value: number , count: number){
    this.service.addVoucher(value, count)
      .subscribe(
        response => console.log('Successful!', response),
        error => console.log('Error!', error)
      )
  }

}
