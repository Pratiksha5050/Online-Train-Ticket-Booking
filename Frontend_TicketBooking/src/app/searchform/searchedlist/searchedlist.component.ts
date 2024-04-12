import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TrainSearchService } from '../../services/trainModule.service';
import { Train } from '../../models/train.model';
@Component({
  selector: 'app-searchedlist',
  templateUrl: './searchedlist.component.html',
  styleUrl: './searchedlist.component.css'
})
export class SearchedlistComponent {


  searchResults: any;
  trainNo!: number;
  sourceStation: string = '';
  destinationStation: string = '';
  dates!: Date;
  trains: Train[] = [];
  constructor(private route: ActivatedRoute, private router: Router, private trainService: TrainSearchService) { }

  ngOnInit(): void {
    // Retrieve parameters from the URL
    this.route.queryParams.subscribe(params => {
      this.sourceStation = params['sourceStation'];
      this.destinationStation = params['destinationStation'];
      this.dates = params['dates'];
      console.log(this.sourceStation, this.destinationStation, this.dates);
      // Call your microservice to fetch data based on parameters
      // and update the component properties accordingly
      this.fetchData();
    });
  }

  fetchData(): void {
    // Implement logic to fetch data from your microservice
    // using the sourceStation, destinationStation, and date
    // properties as parameters.
    this.trainService.getTrainsByData(this.sourceStation, this.destinationStation, this.dates).subscribe(data => {
      this.searchResults = data;
    });
    // console.log('Fetching data with:', this.sourceStation, this.destinationStation, this.date);
  }


  bookTrain(train: Train): void {
    const navigationExtras = {
      state: {
        trainData: train
      },
      queryParams: {
        trainNo: train.trainNo,
        sourceStation: train.sourceStation,
        destinationStation: train.destinationStation,
        trainName:train.trainName,
        dates:train.dates,
        firstClassPrice:train.firstClassPrice,
        secondClassPrice:train.secondClassPrice,
        thirdClassPrice:train.thirdClassPrice,
        sleeperCoachPrice:train.sleeperCoachPrice,
        departureTimes:train.departureTimes,
        arrivalTimes:train.arrivalTimes
        
      }
    };
  
    this.router.navigate(['/booking'], navigationExtras);
  }

}
