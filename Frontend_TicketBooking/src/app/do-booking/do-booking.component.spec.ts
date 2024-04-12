import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DoBookingComponent } from './do-booking.component';

describe('DoBookingComponent', () => {
  let component: DoBookingComponent;
  let fixture: ComponentFixture<DoBookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DoBookingComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DoBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
