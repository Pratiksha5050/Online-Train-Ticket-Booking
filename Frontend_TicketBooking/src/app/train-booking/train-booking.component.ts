import { Component, OnInit } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { TrainSearchService } from '../services/trainModule.service';
import { Train } from '../models/train.model';
import { HttpErrorResponse } from '@angular/common/http';
import { SnackbarService } from '../services/snackbar.service';
import { Observable, map } from 'rxjs';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-train-booking',
  templateUrl: './train-booking.component.html',
  styleUrl: './train-booking.component.css',

})
export class TrainBookingComponent implements OnInit {

  trainForm: any;
  trainService: any;
  trainData: Train = {
    trainNo: '',
    trainName: '',
    sourceStation: '',
    destinationStation: '',
    departureTimes: '',
    arrivalTimes: '',
    dates: new Date,
    totalCoach: 4,
    seatsInSleeperClass: 30,
    seatsInFirstClassClass: 30,
    seatsInSecondClassClass: 30,
    seatsInThirdClassClass: 30,
    firstClassPrice: 0,
    secondClassPrice: 0,
    thirdClassPrice: 0,
    sleeperCoachPrice: 0
  };
  minDate: string = '';


  constructor(private fb: FormBuilder, private service: TrainSearchService, private snackbarService: SnackbarService) {
    this.minDate = this.getCurrentDate()
  }

  getCurrentDate(): string {
    const today = new Date();
    const month = (today.getMonth() + 1).toString().padStart(2, '0');
    const day = today.getDate().toString().padStart(2, '0');
    return `${today.getFullYear()}-${month}-${day}`;
  }

  ngOnInit(): void {
    this.initForm();
  }

  initForm(): void {
    this.trainForm = this.fb.group({
      trainNo: ['', Validators.required],
      trainName: ['', Validators.required],
      sourceStation: ['', Validators.required],
      destinationStation: ['', Validators.required],
      departureTimes: ['', Validators.required],
      arrivalTimes: ['', Validators.required],
      dates: ['', Validators.required],
      firstClassPrice: ['', Validators.required],
      secondClassPrice: ['', Validators.required],
      thirdClassPrice: ['', Validators.required],
      sleeperCoachPrice: ['', Validators.required],
    });
  }

  clearForm(): void {
    this.trainForm.reset();
  }

  submitForm(): void {
    if (this.trainForm.valid) {
      this.service.createTrain(this.trainForm.value).subscribe(
        (response: Train) => {

          console.log('Train created successfully:', response);
          this.snackbarService.showSuccess('Train added successfully!');
          location.reload()

        }
        ,
        (error: any) => {
          if (error.status === 409) {
            // Handle 409 error here
            alert('Train Number is alredy Present');
            // You can also return a custom error message or handle the conflict in a specific way

          }
          console.error('Error creating train:', error);
          if (error instanceof HttpErrorResponse) {
            console.error('Status:', error.status);
            console.error('Error:', error.error);
          }
          // Handle error, if needed
        }
      );
    }
  }
}





