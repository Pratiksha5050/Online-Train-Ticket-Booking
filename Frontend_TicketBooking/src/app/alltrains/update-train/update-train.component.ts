import { Component,Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MAT_DIALOG_DATA} from '@angular/material/dialog';
import { Train } from '../../models/train.model';
import { TrainSearchService } from '../../services/trainModule.service';
import { MatDialogRef } from '@angular/material/dialog';
import { SnackbarService } from '../../services/snackbar.service';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';

@Component({
  selector: 'app-update-train',
  templateUrl: './update-train.component.html',
  styleUrl: './update-train.component.css'
})
export class UpdateTrainComponent implements OnInit{
  updateForm!: FormGroup ;
  isRequired: boolean = true;
  minDate: string = '';
  train: Train | null; // Add type null
  constructor(
    private fb: FormBuilder,
    public dialogRef: MatDialogRef<UpdateTrainComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { train: Train },
    private trainService: TrainSearchService,
    private snackbarService: SnackbarService
  ) { this.train = data ? data.train : null;
    this.minDate = this.getCurrentDate()}

    getCurrentDate(): string {
      const today = new Date();
      const month = (today.getMonth() + 1).toString().padStart(2, '0');
      const day = today.getDate().toString().padStart(2, '0');
      return `${today.getFullYear()}-${month}-${day}`;
    }
  
  ngOnInit(): void {
    if (this.train) {
    this.updateForm = this.fb.group({
      trainNo: [{ value: this.data.train.trainNo, disabled: true }, Validators.required],
      trainName: [this.data.train.trainName, Validators.required],
      sourceStation: [this.data.train.sourceStation, Validators.required],
      destinationStation: [this.data.train.destinationStation, Validators.required],
      departureTimes: [this.data.train.departureTimes, Validators.required],
      arrivalTimes: [this.data.train.arrivalTimes, Validators.required],
      dates:[this.data.train.dates],
      totalCoach:[{ value: this.data.train.totalCoach, disabled: true },Validators.required],
      seatsInSleeperClass:[this.data.train.seatsInSleeperClass,],
      seatsInFirstClassClass:[this.data.train.seatsInFirstClassClass],
      seatsInSecondClassClass:[this.data.train.seatsInSecondClassClass],
      seatsInThirdClassClass:[this.data.train.seatsInThirdClassClass],
      firstClassPrice:[this.data.train.firstClassPrice],
      secondClassPrice:[this.data.train.secondClassPrice],
      thirdClassPrice:[this.data.train.thirdClassPrice],
      sleeperCoachPrice:[this.data.train.sleeperCoachPrice]
    });
  }
}



  onSubmit(): void {
    if (this.updateForm.valid && this.train) {
      const updatedTrain: Train = {
        trainNo: this.trainNo?.value,
        trainName: this.trainName?.value,
        sourceStation: this.sourceStation?.value,
        destinationStation: this.destinationStation?.value,
        departureTimes: this.departureTimes?.value,
        arrivalTimes: this.arrivalTimes?.value,
        dates: this.dates?.value,
        totalCoach: this.totalCoach?.value,
        seatsInSleeperClass: this.seatsInSleeperClass?.value,
        seatsInFirstClassClass: this.seatsInFirstClassClass?.value,
        seatsInSecondClassClass: this.seatsInSecondClassClass?.value,
        seatsInThirdClassClass: this.seatsInThirdClassClass?.value,
        firstClassPrice: this.firstClassPrice?.value,
        secondClassPrice: this.secondClassPrice?.value,
        thirdClassPrice: this.thirdClassPrice?.value,
        sleeperCoachPrice: this.sleeperCoachPrice?.value
      };

      this.trainService.updateTrain(updatedTrain).subscribe(() => {
        // alert('Train Updated Successfully.')
        this.snackbarService.showSuccess('Train Updated Successfully.');
        this.dialogRef.close('updated');
        location.reload();
      });
    }
  }

 

  onClose(): void {
    this.dialogRef.close();
  }

  get trainNo() {
    return this.updateForm.get('trainNo');
  }

  get trainName() {
    return this.updateForm.get('trainName');
  }
  get sourceStation()
  {
    return this.updateForm.get('sourceStation');
  }
  
  get destinationStation() {
    return this.updateForm.get('destinationStation');
  }

  get departureTimes() {
    return this.updateForm.get('departureTimes');
  }

  get arrivalTimes() {
    return this.updateForm.get('arrivalTimes');
  }

  get dates()
  {
    return this.updateForm.get('dates');
  }

  get totalCoach()
  {
    return this.updateForm.get('totalCoach');
  }

  get seatsInSleeperClass()
  {
    return this.updateForm.get('seatsInSleeperClass');
  }

  get seatsInFirstClassClass()
  {
    return this.updateForm.get('seatsInFirstClassClass');
  }

  get seatsInSecondClassClass()
  {
    return this.updateForm.get('seatsInThirdClassClass');
  }

  get seatsInThirdClassClass()
  {
    return this.updateForm.get('seatsInThirdClassClass');
  }
  get firstClassPrice()
  {
    return this.updateForm.get('firstClassPrice');
  }
  get secondClassPrice()
  {
    return this.updateForm.get('secondClassPrice');
  }
get thirdClassPrice()
{
  return this.updateForm.get('thirdClassPrice');
}

get sleeperCoachPrice()
{
  return this.updateForm.get('sleeperCoachPrice');
}

}
