import { Component } from '@angular/core';
import { AbstractControl, AsyncValidatorFn, FormBuilder, FormGroup, ValidationErrors, Validators } from '@angular/forms';
import { UserInfo } from '../models/user-info.model';
import { Observable, catchError, debounceTime, map, of } from 'rxjs';
import { TrainSearchService } from '../services/trainModule.service';
import { SnackbarService } from '../services/snackbar.service';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrl: './signup.component.css'
})
export class SignupComponent {
selected: any;
genderOptions = ['Male', 'Female', 'Other'];
minDate: any;
  userInfoForm:any;
  constructor(private fb: FormBuilder,private trainService:TrainSearchService,private snackbarService: SnackbarService) 
  {
    
  }

  ngOnInit() {
    this.initForm();
  }

  initForm() {
    this.userInfoForm = this.fb.group({
      password: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', [Validators.required]],   
      userName: ['', [Validators.required,Validators.minLength(5)]],
      firstName: ['', [Validators.required, Validators.minLength(2)]],
      middleName: [''],
      lastName: ['', [Validators.required, Validators.minLength(2)]],
      dateOfBirth: ['', [Validators.required]],
      gender: ['', [Validators.required]],
      mobileNumber: ['', [
        Validators.required,
        Validators.pattern('^[0-9]+$'), // Only digits
        Validators.minLength(10),
        Validators.maxLength(10)
      ]],
      email: ['', [Validators.required, Validators.email]],
      nationality: [{value:"Indian", disabled: true}, [Validators.required]],
      // role: ['', [Validators.required, Validators.pattern('^(user|admin)$')]]
    },
    { validator: this.passwordMatchValidator });
  }

  passwordMatchValidator(control: FormGroup): { [key: string]: any } | null {
    const password = control.get('password')?.value;
    const confirmPassword = control.get('confirmPassword')?.value;
 
    if (password != confirmPassword) {
      return { passwordMismatch: true };
    }
 
    return null;
  }
 
  


  onSubmit() {
    if (this.userInfoForm.valid) {
      const userInfo = this.userInfoForm.value;
      new UserInfo(
        this.userInfoForm.value.password,
        this.userInfoForm.value.userName,
        this.userInfoForm.value.firstName,
        this.userInfoForm.value.middleName,
        this.userInfoForm.value.lastName,
        this.userInfoForm.value.dateOfBirth,
        this.userInfoForm.value.gender,
        this.userInfoForm.value.mobileNumber,
        this.userInfoForm.value.email,
        this.userInfoForm.value.nationality="Indian",
        this.userInfoForm.value.role="USER"
      );

      
      this.trainService.registerUser(userInfo).subscribe(
        (response) => {
            this.snackbarService.showSuccess('User Added Successfully!');
            console.log('User registered successfully:', response);
            location.reload()
        },
        (error) => {
          console.error('Error registering user:', error);
          if (error.status === 409) {
            if (error.error.includes('userName')) 
            {
              this.snackbarService.showError('User with the same username already exists.');
            }
            else if (error.error.includes('mobile number')) 
            {
              this.snackbarService.showError('User with the same mobile number already exists.');
            }
            else if (error.error.includes('email address')) 
            {
              this.snackbarService.showError('User with the same email address already exists.');
            }
            else 
            {
              this.snackbarService.showError('An error occurred while registering the user. Please try again later.');
            }
          } 
          else {
            // Other errors, show generic error message
            this.snackbarService.showError('An error occurred while registering the user. Please try again later.');
          }
        }
      );
    } else {
      // Handle invalid form
      console.log('Form is invalid');
      // Log errors for each form control
      Object.keys(this.userInfoForm.controls).forEach(controlName => {
      const control = this.userInfoForm.get(controlName);

      if (control && control.invalid) {
        console.log(`Control '${controlName}' has the following errors:`, control.errors);
      }
    });
    }
  }


  validateUsernameUniqueness(): AsyncValidatorFn {
    return (control: AbstractControl): Observable<ValidationErrors | null> => {
      const username = control.value;

      return this.trainService.checkUsernameUniqueness(username).pipe(
        debounceTime(300),
        map(isUnique => (isUnique ? null : { notUnique: true })),
        catchError(() => of(null))
      );
    };
  }
  clearForm() {
    this.userInfoForm.reset();
  }
  // passwordMatchValidator(formGroup: FormGroup): { [key: string]: boolean } | null {
  //   const passwordControl = formGroup.get('password');
  //   const confirmPasswordControl = formGroup.get('confirmPassword');

  //   if (passwordControl && confirmPasswordControl && passwordControl.value !== confirmPasswordControl.value) {
  //     return { passwordMismatch: true };
  //   }
  //   return null;
  // }
}
