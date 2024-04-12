import { Component, OnInit, ViewChild } from '@angular/core';
import { TrainSearchService } from '../services/trainModule.service';
import { Train } from '../models/train.model';
import { MatTableDataSource } from '@angular/material/table';
import { MatPaginator } from '@angular/material/paginator';
import { Subject } from 'rxjs';
import { MatDialog } from '@angular/material/dialog';
import { UpdateTrainComponent } from './update-train/update-train.component';
import * as XLSX from 'xlsx';

@Component({
  selector: 'app-alltrains',
  templateUrl: './alltrains.component.html',
  styleUrl: './alltrains.component.css'
})
export class AlltrainsComponent implements OnInit {

  trains: any[] = [];
  
  currentDate: Date = new Date();
  
  dataSource = new MatTableDataSource<Train>([]);

  displayedColumns: string[] = ['trainNo', 'trainName','sourceStation',
  'destinationStation','departureTimes','arrivalTimes','dates',
  'seatsInSleeperClass','seatsInFirstClassClass','seatsInSecondClassClass',
  'seatsInThirdClassClass','actions'];
  constructor(private trainService: TrainSearchService , private dialog: MatDialog) {}
  @ViewChild(MatPaginator, { static: true }) paginator!: MatPaginator;

  
  

  ngOnInit(): void {
    this.trainService.getAllTrains().subscribe(trains => {
      this.trains = trains; // Set the trains array
      this.dataSource.data = this.trains.slice(0, 3); // Display the first 5 trains
  console.log(trains)
      // Set the total number of pages based on the total number of trains
      this.paginator.length = this.trains.length;
      this.paginator.pageSize = 3;
      // Assuming your date is a string
      // const originalDate = ;

    });
  }

  applyFilter(event: Event) {
    const filterValue = (event.target as HTMLInputElement).value.trim().toLowerCase();
    this.dataSource.filter = filterValue;
  
    // Reset paginator to first page after applying filter
    if (this.dataSource.paginator) {
      this.dataSource.paginator.firstPage();
    }
  }
  
  
  exportToExcel(): void {
    const ws: XLSX.WorkSheet = XLSX.utils.json_to_sheet(this.trains);
    const wb: XLSX.WorkBook = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(wb, ws, 'Sheet1');
    XLSX.writeFile(wb, 'train_data.xlsx');
  }

  
  onPageChange(event: any): void {
    const startIndex = event.pageIndex * event.pageSize;
    const endIndex = startIndex + event.pageSize;
  
    // Ensure endIndex does not exceed the total number of trains
    const actualEndIndex = endIndex > this.trains.length ? this.trains.length : endIndex;
  
    this.dataSource.data = this.trains.slice(startIndex, actualEndIndex);
  }
  
  confirmDelete(trainNo: string): void {
    const isConfirmed = window.confirm(`Are you sure to delete Train No ${trainNo}?`);

    if (isConfirmed) {
      this.trainService.deleteTrain(trainNo).subscribe(() => {
        // Reload the train list after deletion
        this.trainService.getAllTrains().subscribe(trains => {
          this.trains = trains; // Set the trains array
          this.dataSource.data = this.trains.slice(0, 3); // Display the first 5 trains
      
          // Set the total number of pages based on the total number of trains
          this.paginator.length = this.trains.length;
          this.paginator.pageSize = 3;
        });
      });
    }
    location.reload();
  }
 
  
  openUpdateDialog(train: Train): void {
    const dialogRef = this.dialog.open(UpdateTrainComponent, {
      data: { train },
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result === 'updated') {
        this.trainService.getAllTrains();
      }
    });
  }

  

  
}
function openUpdateDialog(train: any, Train: any) {
  throw new Error('Function not implemented.');
}

