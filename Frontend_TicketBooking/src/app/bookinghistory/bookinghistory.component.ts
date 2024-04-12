import { Component, OnInit } from '@angular/core';
import { TrainSearchService } from '../services/trainModule.service';
import jsPDF from 'jspdf';

@Component({
  selector: 'app-bookinghistory',
  templateUrl: './bookinghistory.component.html',
  styleUrl: './bookinghistory.component.css'
})
export class BookinghistoryComponent implements OnInit {
  bookingHistory: any[] = [];
  userName!:string;
  constructor(private bookingService: TrainSearchService) {
    
  }

  ngOnInit(){
    this.viewData();
  }
  bookings:any
  Todaydates:any;
  viewData() {
    // Fetch booking history data for the logged-in user
    this.bookingService.getBookingHistory(this.bookingService.getUsername()).subscribe(
      (data) => {
        this.bookingHistory = data;
        // this.Todaydates=data.value.dates;
        const priceArray = this.bookingHistory.map(bookings => bookings.price=Math.ceil(bookings.price).toFixed(1));
        const dateArray = this.bookingHistory.map(bookings => bookings.dates=this.formatDate(bookings.dates));
       
        console.log(priceArray)
       console.log(data)
        console.log('Booking History:', this.bookingHistory);
      },
      (error) => {
        console.error('Error fetching booking history:', error);
      }
    );
  }

  private formatDate(originalDate: string | null): string {
    if (!originalDate) {
      return '';
    }

    const dateObject = new Date(originalDate);
    const options: Intl.DateTimeFormatOptions = { year: 'numeric', month: 'short', day: 'numeric' };

    return dateObject.toLocaleDateString('en-US', options);
  }

  isDateGreaterThanToday(bookingDate: string): boolean {
    const today = new Date();
    const bookingDateTime = new Date(bookingDate);

    return bookingDateTime > today;
  }
  downloadBookingAsPDF(booking: any): void {
    const doc = new jsPDF(); // Create new jsPDF instance
    let y = 10; // Initial y position for text

    const pageWidth = doc.internal.pageSize.getWidth();
  const pageHeight = doc.internal.pageSize.getHeight();

  // Draw a rectangle around the entire page
  doc.rect(5, 5, pageWidth - 10, pageHeight - 10);
    // Calculate the width of the PNR text
const pnrText = `PNR: ${booking.pnr}`;
const pnrWidth = doc.getTextWidth(pnrText);

// Calculate the starting position for center alignment
const startXP = (doc.internal.pageSize.getWidth() - pnrWidth) / 2;

// Draw the PNR text
y+=20
doc.text(pnrText, startXP, y);
y+=10;
  doc.text(`User Name: ${booking.userName}`, 10, y);
  y += 10; // Increase y position for next line
  doc.text(`Total Seats: ${booking.totalSeats}`, 10, y);
  y += 10; // Increase y position for next line
  doc.text(`Class Type: ${booking.classType} Class`, 10, y);
  y += 10; // Increase y position for next line
  doc.text(`Price: Rs.${booking.price} `, 10, y);
  y += 10; // Increase y position for next line
  doc.text(`Date: ${booking.dates}`, 10, y);
  y += 20; // Increase y position for next line

  const trainInfoText = "Train Information:";
    const trainInfoWidth = doc.getTextWidth(trainInfoText);
    doc.text(trainInfoText, 10, y);
    doc.line(10, y + 2, 10 + trainInfoWidth, y + 2); // Draw line below text

  y+=10;
  if (Array.isArray(booking.train)) {
    booking.train.forEach((trainInfo: any) => {
      doc.text(`Train Name: ${trainInfo.trainName }`, 10, y);
      y += 10; // Increase y position for next line
      doc.text(`Source Station: ${trainInfo.sourceStation}`, 10, y);
      y += 10; // Increase y position for next line
      doc.text(`Destination Station: ${trainInfo.destinationStation}`, 10, y);
      y += 10; // Increase y position for next line
      doc.text(`Departure Time: ${trainInfo.departureTimes}`, 10, y);
      y += 10; // Increase y position for next line
    });
  } else {
    // Handle the case when booking.train is not an array
    // Assuming trainInfo is an object with properties
    doc.text(`Train Name: ${booking.train.trainName}`, 10, y);
    y += 10; // Increase y position for next line
    doc.text(`Source Station: ${booking.train.sourceStation}`, 10, y);
    y += 10; // Increase y position for next line
    doc.text(`Destination Station: ${booking.train.destinationStation}`, 10, y);
    y += 10; // Increase y position for next line
    doc.text(`Departure Time: ${booking.train.departureTimes}`, 10, y);
    y += 10; // Increase y position for next line
  }
  y+=20;
  const userInfoText = "User Information:";
  const userInfoWidth = doc.getTextWidth(userInfoText);
  doc.text(userInfoText, 10, y);
  doc.line(10, y + 2, 10 + userInfoWidth, y + 2); // Draw line below text
 y+=10;

  
  // Calculate the width of each cell
const cellWidth = 40; // Adjust according to your requirement

// Define the position of the table
const startX = 10; // Adjust according to your requirement
let currentY = y;

// Draw table headers
const headers = ['No.', 'Name', 'Age', 'Gender'];
headers.forEach((header, index) => {
  doc.text(header, startX + index * cellWidth, currentY);
});

currentY += 10; // Increase y position for next line

// Draw table rows
booking.userInfo.forEach((user: any, index: number) => {
  doc.text((index + 1).toString(), startX, currentY);
  doc.text(user.name, startX + cellWidth, currentY);
  doc.text(user.age.toString(), startX + 2 * cellWidth, currentY);
  doc.text(user.gender, startX + 3 * cellWidth, currentY);

  currentY += 10; // Increase y position for next line
});


// Add "Thank you for traveling with us" message at the end of the page
const thankYouText = "Thank you for traveling with us";
const thankYouWidth = doc.getTextWidth(thankYouText);
const thankYouX = (pageWidth - thankYouWidth) / 2;
const thankYouY = pageHeight - 10; // Adjust position as needed
doc.text(thankYouText, thankYouX, thankYouY);
    // Save PDF
    doc.save(`booking_${booking.pnr}.pdf`);
  }

  cancelBooking(pnr: string, seatIndex: number): void {
    if (confirm('Are you sure you want to delete this user?'))
    {
    this.bookingService.cancelBooking(pnr, seatIndex).subscribe(
      (response) => {
        console.log(response)
        location.reload();
      },
      (error) => {
        console.error('Error canceling booking:', error);
      }
    );
    }
  }
  }
