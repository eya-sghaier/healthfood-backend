import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SavedPlansComponent } from './saved-plans.component';

describe('SavedPlansComponent', () => {
  let component: SavedPlansComponent;
  let fixture: ComponentFixture<SavedPlansComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SavedPlansComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SavedPlansComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
