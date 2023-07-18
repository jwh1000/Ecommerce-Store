import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProductUserDetailComponent } from './product-user-detail.component';

describe('ProductUserDetailComponent', () => {
  let component: ProductUserDetailComponent;
  let fixture: ComponentFixture<ProductUserDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [ProductUserDetailComponent]
    });
    fixture = TestBed.createComponent(ProductUserDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});