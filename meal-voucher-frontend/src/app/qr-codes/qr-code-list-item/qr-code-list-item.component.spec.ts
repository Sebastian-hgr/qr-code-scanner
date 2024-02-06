import { ComponentFixture, TestBed } from '@angular/core/testing';

import { QrCodeListItemComponent } from './qr-code-list-item.component';

describe('QrCodeListItemComponent', () => {
  let component: QrCodeListItemComponent;
  let fixture: ComponentFixture<QrCodeListItemComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [QrCodeListItemComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(QrCodeListItemComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
