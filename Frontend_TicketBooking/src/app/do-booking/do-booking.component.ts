import { DatePipe } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AbstractControl, FormArray, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators } from '@angular/forms';
import { MatStepper } from '@angular/material/stepper';
import { ActivatedRoute, Router } from '@angular/router';
import { TrainSearchService } from '../services/trainModule.service';
import { MatDialog } from '@angular/material/dialog';
import { PnrDialogComponentComponent } from '../pnr-dialog-component/pnr-dialog-component.component';
@Component({
  selector: 'app-do-booking',
  templateUrl: './do-booking.component.html',
  styleUrl: './do-booking.component.css'
})
export class DoBookingComponent implements OnInit {
  trainInfoForm: any;
  passengerForm!: FormGroup;
  trainNo!: string;
  sourceStation!: string;
  destinationStation!: string;
  classOptions = [
    { value: 'First', label: 'First Class' },
    { value: 'Second', label: 'Second Class' },
    { value: 'Third', label: 'Third Class' },
    { value: 'Sleep', label: 'Sleeper Class' }
  ];

  genderOptions = [
    { value: 'male', label: 'Male' },
    { value: 'female', label: 'Female' },
    { value: 'other', label: 'Other' }
  ];
  trainName!: string;
  dates!: string;
  totalSeats: any;
  userName: any;
  classType: any;
  firstClassPrice: any;
  secondClassPrice: any;
  thirdClassPrice: any;
  sleeperCoachPrice: any;
  price: any
  arrivalTime: any;
  departureTimes: any;
  arrivalTimes: any;
  newArray:any
  constructor(private router: Router, private dialog: MatDialog, private cdr: ChangeDetectorRef, private fb: FormBuilder, private route: ActivatedRoute, private trainService: TrainSearchService) {

  }

  ngOnInit(): void {
    this.route.queryParams.subscribe((params) => {
      this.trainNo = params['trainNo'];
      this.sourceStation = params['sourceStation'];
      this.destinationStation = params['destinationStation'];
      this.trainName = params['trainName'],
        this.dates = params['dates'],
        this.firstClassPrice = params['firstClassPrice'],
        this.secondClassPrice = params['secondClassPrice'],
        this.thirdClassPrice = params['thirdClassPrice'],
        this.sleeperCoachPrice = params['sleeperCoachPrice']
        this.arrivalTimes = params['arrivalTimes'],
        this.departureTimes = params['departureTimes']
    });


    // const formattedDate = this.datePipe.transform(this.dates, 'MM-dd-yyyy');
    // console.log(formattedDate)
    this.trainInfoForm = this.fb.group({
      trainNo: [{ value: this.trainNo, disabled: true }, Validators.required],
      sourceStation: [{ value: this.sourceStation, disabled: true }, Validators.required],
      destinationStation: [{ value: this.destinationStation, disabled: true }, Validators.required],
      trainName: [{ value: this.trainName, disabled: true }, Validators.required],
      dates: [{ value: this.dates, disabled: true }, Validators.required],
      classOfTravel: [null, Validators.required]
    });



    this.passengerForm = this.fb.group({
      passengers: this.fb.array([])
    });




    this.addPassenger()
    this.passengerForm.valueChanges.subscribe(() => {
     console.log("class", this.trainInfoForm.value.classOfTravel);
     console.log("seats", this.passengers.length);
      this.calculateTotalCost();    
      this.cdr.detectChanges();
    //  console.log(this.passengers.length)
    });
  }

  // Create a form group for a passenger
  createPassenger(): FormGroup {
    return this.fb.group({
      name: ['', Validators.required],
      age: ['', this.ageValidator(5, 99)],
      gender: ['', Validators.required]
    });
  }

  ageValidator(minAge: number, maxAge: number): ValidatorFn {
    return (control: AbstractControl): ValidationErrors | null => {
      const age = control.value;

      if (isNaN(age) || age < minAge || age > maxAge) {
        return { invalidAge: true };
      }
      return null;
    };
  }
  // Add a passenger to the form array
  addPassenger(): void {
    const passengers = this.passengerForm.get('passengers') as FormArray;
    passengers.push(this.createPassenger());
  }

  deletePassenger(index: number) {
    console.log(index);
    this.passengers.removeAt(index);
  }

  // Remove a passenger from the form array
  removePassenger(index: number): void {
    const passengers = this.passengerForm.get('passengers') as FormArray;
    passengers.removeAt(index);
  }

  // Submit the form data
  submitForm(): void {
    const bookingData = {
      totalSeats: this.passengers.length,
      trainNo: this.trainNo,
      userName: this.trainService.getUsername(),
      classType: this.trainInfoForm.value.classOfTravel,
      userInfo: this.passengerForm.value.passengers,
      dates:this.dates
    };
    console.log(bookingData)
   
    this.trainService.postBookingData(bookingData)
      .subscribe((response) => {

         this.newArray= response;

        // Now 'newArray' contains the same data as 'response'
        console.log('New Array:',  this.newArray)
        console.log('New pnr:',  this.newArray.pnr)
        const dialogRef = this.dialog.open(PnrDialogComponentComponent, {
          data: this.newArray.pnr // Assuming your backend returns the PNR number in the response
        });

        // After the user clicks "OK," navigate to the search page
        dialogRef.afterClosed().subscribe(() => {
          this.router.navigate(['/search']); // Adjust the route according to your app
        });
        console.log('Backend Response:', response);
      }, error => {
        console.error('Error:', error);
      });
    // console.log('Train Information:', this.trainInfoForm.value);
    // console.log('Passenger Information:', this.passengerForm.value);
  }


  // Accessor for the form array of passengers
  get passengers(): FormArray {
    return this.passengerForm.get('passengers') as FormArray;

  }

  calculateTotalCost() {
    if (this.trainInfoForm.value.classOfTravel == 'First') {
      this.price = this.passengers.length * this.firstClassPrice;
    }
    else if (this.trainInfoForm.value.classOfTravel == 'Second') {
      this.price = this.passengers.length* this.secondClassPrice;
    }
    else if (this.trainInfoForm.value.classOfTravel == 'Third') {
      this.price = this.passengers.length * this.thirdClassPrice;
    }
    else if (this.trainInfoForm.value.classOfTravel == 'Sleep') {
      this.price = this.passengers.length * this.sleeperCoachPrice;
    }
    console.log("cost",Math.ceil(this.price).toFixed(1));
  }
}