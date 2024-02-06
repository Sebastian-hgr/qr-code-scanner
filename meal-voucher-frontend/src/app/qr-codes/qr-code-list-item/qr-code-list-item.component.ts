import {Component, Input} from '@angular/core';
import {Qrcode} from "../../shared/qrcode";

@Component({
  selector: 'mvf-qr-code-list-item',
  templateUrl: './qr-code-list-item.component.html',
  styleUrl: './qr-code-list-item.component.css'
})
export class QrCodeListItemComponent {
  @Input() qrcode?: Qrcode
}
