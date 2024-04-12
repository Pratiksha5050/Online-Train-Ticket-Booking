import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchedlistComponent } from './searchedlist.component';

describe('SearchedlistComponent', () => {
  let component: SearchedlistComponent;
  let fixture: ComponentFixture<SearchedlistComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchedlistComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SearchedlistComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
