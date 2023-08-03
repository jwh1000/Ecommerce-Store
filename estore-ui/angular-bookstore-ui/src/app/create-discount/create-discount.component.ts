import { Component } from '@angular/core';
import { DiscountCode } from '../discountcode';
import { DiscountCodeService } from '../discount-code.service';

@Component({
  selector: 'app-create-discount',
  templateUrl: './create-discount.component.html',
  styleUrls: ['./create-discount.component.css']
})
export class CreateDiscountComponent {
  constructor(
    private discountCodeService: DiscountCodeService
  ) {}

  /**
   * Adds a new discount code by calling the discount code service.
   * @param code The code of the discount code to be added.
   * @param percentage The string of the percentage discount the product should have. 
   */
  add(code: string, percentage: string): void {
    code = code.trim();
    percentage = percentage.trim();
    if (!code) { return; }
    if (!percentage) { return; }
    var discountPercentage = parseFloat(percentage);
    
    this.discountCodeService.createDiscountCode({ code, discountPercentage } as DiscountCode)
      .subscribe();
  }
}
