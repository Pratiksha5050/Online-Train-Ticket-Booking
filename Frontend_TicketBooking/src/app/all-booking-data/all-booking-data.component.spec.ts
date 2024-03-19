import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllBookingDataComponent } from './all-booking-data.component';

describe('AllBookingDataComponent', () => {
  let component: AllBookingDataComponent;
  let fixture: ComponentFixture<AllBookingDataComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AllBookingDataComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(AllBookingDataComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
