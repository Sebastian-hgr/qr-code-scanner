import { TestBed } from '@angular/core/testing';

import { QrCodeStoreService } from './qr-code-store.service';

describe('QrCodeStoreService', () => {
  let service: QrCodeStoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(QrCodeStoreService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
