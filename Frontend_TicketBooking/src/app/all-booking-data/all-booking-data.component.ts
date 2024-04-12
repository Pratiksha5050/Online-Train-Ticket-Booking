import { Component, OnInit, ViewChild } from '@angular/core';
import { TrainSearchService } from '../services/trainModule.service';
import { MatPaginator } from '@angular/material/paginator';
import { MatTableDataSource } from '@angular/material/table';
import { MatSort } from '@angular/material/sort';
import * as XLSX from 'xlsx';


@Component({
  selector: 'app-all-booking-data',
  templateUrl: './all-booking-data.component.html',
  styleUrl: './all-booking-data.component.css'
})
export class AllBookingDataComponent implements OnInit {
  // bookings: any[] = [];
  // displayedColumns: string[] = ['pnr', 'totalSeats', 'trainNo', 'userName', 'classType', 'price', 'dates', 'actions'];

  // constructor(private bookingService: TrainSearchService) {}


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

  // bookings: any[] = [];
  // filteredBookings: any[] = [];
  // ngOnInit(): void {
  //   this.bookingService.getBookingHistoryForAllUsers().subscribe(bookings => {
  //     this.bookings = bookings.map(booking => {
  //       // Assuming your date is a string
  //       const originalDate = booking.dates;
        
  //       // Convert the date using a custom format function
  //       const formattedDate = this.formatDate(originalDate);

  //       // Return the updated booking object
  //       return { ...booking, dates: formattedDate };
  //     });
  //     // Initially, set filteredBookings to all bookings
  //     this.filteredBookings = this.bookings;
  //   });
  // }


  // exportToExcel(): void {
  //   const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.bookings);
  //   const wb: XLSX.WorkBook = XLSX.utils.book_new();
  //   XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
  //   XLSX.writeFile(wb, 'Booking_data.xlsx');
  // }
  // private formatDate(originalDate: string | null): string {
  //   if (!originalDate) {
  //     return '';
  //   }

  //   const dateObject = new Date(originalDate);
  //   const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'short', day: 'numeric' };

  //   return dateObject.toLocaleDateString('en-US', options);
  // }

 
  // applyFilter(event: Event) {
  //   const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
  //   this.filteredBookings = this.bookings.filter(train => {
  //     return (
  //       train.pnr.toLowerCase().includes(filterValue) ||
  //       train.trainNo.toLowerCase().includes(filterValue) ||
  //       train.dates.toLowerCase().includes(filterValue) ||
  //       train.userName.toLowerCase().includes(filterValue) ||
  //       train.classType.toLowerCase().includes(filterValue) ||
  //       train.price.toString().toLowerCase().includes(filterValue)
  //     );
  //   });
  // }
  displayedColumns: string[] = ['pnr', 'trainNo', 'dates', 'userName', 'totalSeats', 'classType', 'price'];
  dataSource: MatTableDataSource<any> = new MatTableDataSource<any>();
  filteredBookings: any[] = [];
  bookings: any[] = [];
  @ViewChild(MatSort) sort!: MatSort; // Initialize sort in the constructor
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;
  

  constructor(private bookingService: TrainSearchService) {}

  ngOnInit(): void {
    this.bookingService.getBookingHistoryForAllUsers().subscribe(bookings => {
      this.bookings = bookings.map(booking => {
        const originalDate = booking.dates;
        const formattedDate = this.formatDate(originalDate);
        return { ...booking, dates: formattedDate };
      });
      this.filteredBookings = this.bookings;
      this.dataSource = new MatTableDataSource<any>(this.filteredBookings);
      this.dataSource.paginator = this.paginator;
      this.dataSource.sort = this.sort;
    });
  }

  applyFilter(event: Event): void {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  


  exportToExcel(): void {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.filteredBookings);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, 'Booking_data.xlsx');
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
