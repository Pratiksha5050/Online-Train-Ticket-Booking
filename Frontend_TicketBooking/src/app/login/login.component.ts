import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { TrainSearchService } from '../services/trainModule.service';

import { HttpClient } from '@angular/common/http';
import { Route, Router } from '@angular/router';
import { SnackbarService } from '../services/snackbar.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  loginForm!: FormGroup;
// loginObj!:Login;
  constructor(private snackbarService: SnackbarService,private fb: FormBuilder,private router:Router,private trainService:TrainSearchService,private http:HttpClient) 
  { }

  ngOnInit() {
    this.initLoginForm();
  }

  initLoginForm() {
    this.loginForm = this.fb.group({
      userName: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  login() 
  {
  
    if (this.loginForm.valid) {
      const credentials = this.loginForm.value;
      console.log(this.loginForm.value.userName);
      this.trainService.login(credentials).subscribe(
        (result) => {
          const user=this.loginForm.value.userName;
          console.log(user);
          console.log(result);
          // Handle successful login response
          console.log('Login successful:', result);
          this.trainService.loginUser(result,user);
          this.snackbarService.showSuccess('Login Successfully!');
          window.location.href='/search';
           this.fetchUserInfo(user);
        },
        (error) => {
          // Log the specific error details
        console.error('Login error:', error);

        // Handle 403 Forbidden error
        if (error.status === 403) {
          console.error('Access Forbidden. Check authorization rules.');
          this.snackbarService.showSuccess('Username or Password is Incorrect');
        } else {
          // Handle other types of errors
          console.error('Unexpected error occurred');
        }
        }
      );
      }
    // Implement your login logic here
   
    }

    fetchUserInfo(username: string): void {
      this.trainService.getUserInfo().subscribe(
        (userInfo: any) => {
          // Handle user information response
          console.log(userInfo);
        },
        (error: any) => {
          // Handle error while fetching user information
        }
      );
    }
}
// export class Login{
//   userName:string;
//   password:string;
//   constructor()
//   {
//     this.userName='',
//     this.password=''
//   }
  
// }