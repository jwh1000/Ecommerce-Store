import { Component } from '@angular/core';
import { DiscountCode } from '../discountcode';
import { DiscountCodeService } from '../discount-code.service';

@Component({
  selector: 'app-delete-discount',
  templateUrl: './delete-discount.component.html',
  styleUrls: ['./delete-discount.component.css']
})
export class DeleteDiscountComponent {
  discountcodes: DiscountCode[] = [];

  constructor(private discountService: DiscountCodeService) { }

  //Get list of all discount codes on initalization.
  ngOnInit(): void {
    this.getDiscountCodes();
  }

  getDiscountCodes(): void {
    this.discountService.getDiscountCodes()
    .subscribe(discountcodes => this.discountcodes = discountcodes);
  }

  /**
   * Deletes a discount by calling the discount service.
   * @param discountcode The discount code to be deleted.
   */
  delete(discountcode: String): void {
    this.discountService.deleteDiscountCode(discountcode).subscribe(
      (data) =>{
        this.ngOnInit(); //Reload this component after deleting.
      }
    );
  }
}

