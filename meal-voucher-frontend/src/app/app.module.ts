import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import {QrCodesModule} from "./qr-codes/qr-codes.module";
import {HttpClientModule} from "@angular/common/http";
import { QrCodeCreateComponent } from './qr-codes/qr-code-create/qr-code-create.component';
import {FormsModule} from "@angular/forms";

@NgModule({
  declarations: [
    AppComponent,
    QrCodeCreateComponent
  ],
    imports: [
        BrowserModule,
        AppRoutingModule,
        QrCodesModule,
        HttpClientModule,
        FormsModule
    ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
