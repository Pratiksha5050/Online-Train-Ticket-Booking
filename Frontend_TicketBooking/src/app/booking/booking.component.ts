import { Component } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Passenger, Train } from '../models/train.model';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrl: './booking.component.css'
})
export class BookingComponent {
  // trainData: Train; // Use a proper type based on your data model
  totalSeatsOptions: number[] = [1, 2, 3, 4, 5,6]; // Add more options based on your requirement
  selectedTotalSeats!: number;
  selectedClassType!: string;
  passengerForms: { [seat: number]: Passenger } = {};
  trainData: Train;
  selectedSeats: number[] = [];
  name: any;
  age: any;
  gender: any;
  // passengerForms: { [seat: number]: Passenger } = {};

  constructor(private route: ActivatedRoute) {
    // Access the train data from the navigation state
    const navigation = this.route.snapshot.paramMap;
    this.trainData = history.state.trainData;
  }

  // selectedSeats: number[] = [];
 
  openForms() {
  this.selectedSeats = [];
  this.passengerForms = [];

  for (let i = 1; i <= this.selectedTotalSeats; i++) {
    this.selectedSeats.push(i);
  }
}


  // Add a method to handle the booking logic
  bookSeats() {
    // Implement the logic to book the specified number of seats for the train
    console.log('Booking', this.selectedTotalSeats, 'seats for train:', this.trainData);
    
    // Access passenger information from passengerForms
    for (const seat of this.selectedSeats) {
      console.log(seat);
      console.log(this.passengerForms);

      const passenger = this.passengerForms[seat].name;
      console.log(`Passenger in Seat ${seat}:`, passenger);
    }
  }

}
