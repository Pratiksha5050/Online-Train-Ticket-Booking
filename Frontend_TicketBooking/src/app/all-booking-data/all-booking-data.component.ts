import { Component, OnInit, ViewChild } from '@angular/core';
import { TrainSearchService } from '../services/trainModule.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';

@Component({
  selector: 'app-all-booking-data',
  templateUrl: './all-booking-data.component.html',
  styleUrl: './all-booking-data.component.css'
})
export class AllBookingDataComponent implements OnInit {
  // bookings: any[] = [];
  displayedColumns: string[] = ['pnr', 'totalSeats', 'trainNo', 'userName', 'classType', 'price', 'dates', 'actions'];

  constructor(private bookingService: TrainSearchService) {}


  // ngOnInit(): void {
  //   this.bookingService.getBookingHistoryForAllUsers().subscribe(bookings => {
  //     // this.bookings = bookings;
  //     this.bookings = bookings.map(booking => {
  //       // Assuming your date is a string
  //       const originalDate = booking.dates;
        
  //       // Convert the date using a custom format function
  //       const formattedDate = this.formatDate(originalDate);

  //       // Return the updated booking object
  //       return { ...booking, dates: formattedDate };
  //   });
  // }

  bookings: any[] = [];

  ngOnInit(): void {
    this.bookingService.getBookingHistoryForAllUsers().subscribe(bookings => {
      this.bookings = bookings.map(booking => {
        // Assuming your date is a string
        const originalDate = booking.dates;
        
        // Convert the date using a custom format function
        const formattedDate = this.formatDate(originalDate);

        // Return the updated booking object
        return { ...booking, dates: formattedDate };
      });
    });
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
