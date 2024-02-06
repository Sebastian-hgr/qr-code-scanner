import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QrCodeListComponent } from './qr-code-list.component';

describe('QrCodeListComponent', () => {
  let component: QrCodeListComponent;
  let fixture: ComponentFixture<QrCodeListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [QrCodeListComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(QrCodeListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
