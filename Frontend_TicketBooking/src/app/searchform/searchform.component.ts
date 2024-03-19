import { Component, OnInit } from '@angular/core';
import { TrainSearchService } from '../services/trainModule.service';
import { Train } from '../models/train.model';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-searchform',
  templateUrl: './searchform.component.html',
  styleUrl: './searchform.component.css'
})
export class SearchformComponent implements OnInit  {

  sourceStation: string = '';
  destinationStation: string = '';
  dates!: Date;
  trains: Train[] = [];
  searchForm!: FormGroup;
  // cities: string[] = ['Thane','Pune', 'Mumbai', 'Delhi', 'Nagpur', 'Sangli', 'Nashik', 'Gujrat','Kolkata','Hyderabad'];
  minDate: any;
  sourceStations: string[]=[];
  destinationStations: string[]=[];
  constructor(private fb: FormBuilder,private router:Router,private trainService: TrainSearchService) {
  this.minDate = this.getCurrentDate()
 };
  ngOnInit(): void {
    this.searchForm = this.fb.group({
      sourceStation: ['', Validators.required],
      destinationStation: ['', Validators.required],
      dates: ['', Validators.required]
    });
    
    this.trainService.getUniqueSourceStations().subscribe(
      data => {
        this.sourceStations = data;
        console.log(data)
      },
      error => {
        console.log('Error fetching source stations:', error);
      }
    );

    this.trainService.getUniqueDestinationStations().subscribe(
      data => {
        this.destinationStations = data;
        console.log(data)
      },
      error => {
        console.log('Error fetching source stations:', error);
      }
    );
  
  }
  

   getCurrentDate(): string {
    const today = new Date();
    const month = (today.getMonth() + 1).toString().padStart(2, '0');
    const day = today.getDate().toString().padStart(2, '0');
    return `${today.getFullYear()}-${month}-${day}`;
  }

  
  search() {
    if (this.searchForm.valid) {
      // Extract form values
      const sourceStationValue = this.searchForm.value.sourceStation;
      const destinationStationValue = this.searchForm.value.destinationStation;
      const datesValue = this.searchForm.value.dates;
      console.log(sourceStationValue, destinationStationValue, datesValue);
      // Perform your search logic here

      // Navigate to the search page with the form values as query parameters
      this.router.navigate(['/search-list'], {
        queryParams: {
          sourceStation: sourceStationValue,
          destinationStation: destinationStationValue,
          dates: datesValue
        }
      });
    } else {
      // Handle form validation errors if needed
    }

  

  }
}

