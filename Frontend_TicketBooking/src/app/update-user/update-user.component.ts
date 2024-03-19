import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { TrainSearchService } from '../services/trainModule.service';
import { SnackbarService } from '../services/snackbar.service';

@Component({
  selector: 'app-update-user',
  templateUrl: './update-user.component.html',
  styleUrl: './update-user.component.css'
})
export class UpdateUserComponent implements OnInit {
  userForm: any;
 
  genderOptions = ['Male', 'Female', 'Other'];
  userName: string="" ;
  searchResults: any;
  datePipe: any;
  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private userService: TrainSearchService,
    private snackbarService: SnackbarService
  ) {}
  ngOnInit(): void {
 
    //this.userName!= this.route.snapshot.paramMap.get('userName'); // Assuming you are passing the user ID in the route
    this.route.queryParams.subscribe(params => {
      this.userName = params['userName'];
      // Call your microservice to fetch data based on parameters
      // and update the component properties accordingly
      this.userService.getUserInfoByName(this.userName).subscribe(data => {
        this.searchResults = data;
        console.log("From update",this.searchResults)
        this.userForm.setValue({
          userName: this.searchResults.userName,
          password: this.searchResults.password,
          firstName: this.searchResults.firstName,
          middleName: this.searchResults.middleName,
          lastName: this.searchResults.lastName,
          dateOfBirth: this.searchResults.dateOfBirth,
          gender: this.searchResults.gender,
          mobileNumber: this.searchResults.mobileNumber,
          email: this.searchResults.email,
          nationality: this.searchResults.nationality,
          role: this.searchResults.role,
          // Add other form controls as needed
        });
      });
    });
    console.log("update user",this.userName);
    // this.userService.getUserInfo();
  this.userForm = this.fb.group({
    userName: [{ value: '', disabled: true }, Validators.required],
    password: ['', [Validators.required, Validators.minLength(8)]],
    firstName: ['', [Validators.required, Validators.minLength(2)]],
    middleName: [''],
    lastName: ['', [Validators.required, Validators.minLength(2)]],
    dateOfBirth: [{value:''}, Validators.required],
    gender: ['', Validators.required],
    mobileNumber: ['', [
      Validators.required,
      Validators.pattern('^[0-9]+$'), // Only digits
      Validators.minLength(10),
      Validators.maxLength(10)
    ]],
    email: ['', [Validators.required, Validators.email]],
    nationality: [{ value: '', disabled: true }, [Validators.required]],
    role: ['', Validators.required]
    // Add other form controls as needed
  });
}


// Handle form submission
onSubmit(): void {
  if (this.userForm.valid) {
    // Implement logic to update the user using your microservice
    const userData = this.userForm.value;
    this.userService.updateUser(this.userName, userData).subscribe(
      (response: any) => {
        console.log('User updated successfully:', response);
        this.snackbarService.showSuccess('User updated successfully!');
        this.router.navigate(['/search']); // Redirect to user profile or another page
      },
      (error: any) => {
        console.error('Error updating user:', error);
      }
    );
  } else {
    // Handle invalid form
    console.log('Form is invalid');
  }
}
close()
{
  this.router.navigate(['/search'])
}
getFormattedDate(travelDate: string): string {
  const formattedDate = this.datePipe.transform(travelDate, 'dd-MM-yyyy');
  return formattedDate || '';
}
}
