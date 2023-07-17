import { ComponentFixture, TestBed } from '@angular/core/testing';

import { InventorySearchComponent } from './inventory-search.component';

describe('InventorySearchComponent', () => {
  let component: InventorySearchComponent;
  let fixture: ComponentFixture<InventorySearchComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [InventorySearchComponent]
    });
    fixture = TestBed.createComponent(InventorySearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
