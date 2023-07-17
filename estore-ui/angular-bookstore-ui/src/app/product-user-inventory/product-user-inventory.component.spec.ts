import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductUserInventoryComponent } from './product-user-inventory.component';

describe('ProductUserInventoryComponent', () => {
  let component: ProductUserInventoryComponent;
  let fixture: ComponentFixture<ProductUserInventoryComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductUserInventoryComponent]
    });
    fixture = TestBed.createComponent(ProductUserInventoryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});