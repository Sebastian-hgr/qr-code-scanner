import {Component, EventEmitter, Output} from '@angular/core';
import {Qrcode} from "../../shared/qrcode";
import {QrCodeStoreService} from "../../shared/qr-code-store.service";

@Component({
  selector: 'mvf-qr-code-list',
  templateUrl: './qr-code-list.component.html',
  styleUrl: './qr-code-list.component.css'
})
export class QrCodeListComponent {

  qrcodes: Qrcode[] = []
  @Output() selectQrCode = new EventEmitter<Qrcode>()

  constructor(private service: QrCodeStoreService) {
    console.log("hello world from qrcodelist")
    this.service.getAll().subscribe(qrcodes => {
      console.log(this.qrcodes = qrcodes)
      this.qrcodes = qrcodes
    })
  }



}
