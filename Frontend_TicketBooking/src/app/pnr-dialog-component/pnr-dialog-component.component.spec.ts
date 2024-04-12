import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PnrDialogComponentComponent } from './pnr-dialog-component.component';

describe('PnrDialogComponentComponent', () => {
  let component: PnrDialogComponentComponent;
  let fixture: ComponentFixture<PnrDialogComponentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PnrDialogComponentComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(PnrDialogComponentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
