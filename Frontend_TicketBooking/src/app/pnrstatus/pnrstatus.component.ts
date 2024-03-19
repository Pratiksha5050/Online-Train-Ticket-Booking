import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TrainSearchService } from '../services/trainModule.service';

@Component({
  selector: 'app-pnrstatus',
  templateUrl: './pnrstatus.component.html',
  styleUrl: './pnrstatus.component.css'
})
export class PnrstatusComponent implements OnInit {
  pnrForm!: FormGroup;
  bookingInfo: any; // Update the type based on your actual booking info structure
formattedDate: any;

  constructor(private fb: FormBuilder, private trainService: TrainSearchService) {}

  ngOnInit(): void {
    this.pnrForm = this.fb.group({
      pnrNumber: ['', Validators.required],
    });
  }
  errorMessage: string | undefined;
  pricemanage:any;
  booking:any;
  getBookingInfo() {
    const pnrNumber = this.pnrForm.value.pnrNumber;
    if (pnrNumber) {
      // Call your service to get booking information based on the PNR number
      this.trainService.getBookingInfoByPnr(pnrNumber).subscribe(
        (data: any) => {
          this.bookingInfo = data;
          const dates: string = this.bookingInfo?.dates;
          this.pricemanage=Math.ceil(this.bookingInfo?.price);
        // Now you can use the 'dates' variable as needed
        console.log('Dates:', dates);
           this.formattedDate=this.formatDate(this.bookingInfo?.dates)
        },
        (error: any) => {
          if (error && error.status === 500) {
            console.error('Server error:', error);
            // Display a message indicating a server error
            this.errorMessage = 'Please Enter correct PNR Number';
          } else {
            console.error('Error fetching booking information:', error);
          }
        }
      );
    }
  }
  private formatDate(originalDate: string | null): string {
    if (!originalDate) {
      return '';
    }

    const dateObject = new Date(originalDate);
    const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'short', day: 'numeric' };

    return dateObject.toLocaleDateString('en-US', options);
  }
}
