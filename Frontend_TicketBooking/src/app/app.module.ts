import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HTTP_INTERCEPTORS, HttpClientModule } from '@angular/common/http';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SignupComponent } from './signup/signup.component';
import { NavbarComponent } from './navbar/navbar.component';
import { SearchformComponent } from './searchform/searchform.component';
import { SearchedlistComponent } from './searchform/searchedlist/searchedlist.component';
import { TrainBookingComponent } from './train-booking/train-booking.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { MatDatepickerModule } from '@angular/material/datepicker';
import { MatNativeDateModule } from '@angular/material/core';
import { LoginComponent } from './login/login.component';
import { MatCardModule } from '@angular/material/card';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import {MatSelectModule} from '@angular/material/select';
import { AlltrainsComponent } from './alltrains/alltrains.component';
import { MatTableModule } from '@angular/material/table';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatIconModule } from '@angular/material/icon';
import { MatDialogModule } from '@angular/material/dialog';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { UpdateTrainComponent } from './alltrains/update-train/update-train.component';
import { BookingComponent } from './booking/booking.component';
import { DoBookingComponent } from './do-booking/do-booking.component';
import { MatStepperModule } from '@angular/material/stepper';
import { AuthInterceptor } from './services/auth.interceptor';
import { TrainSearchService } from './services/trainModule.service';
import { AuthGuard } from './services/auth.guard';
import { UpdateUserComponent } from './update-user/update-user.component';
import { PnrDialogComponentComponent } from './pnr-dialog-component/pnr-dialog-component.component';
import { BookinghistoryComponent } from './bookinghistory/bookinghistory.component';
import { AllBookingDataComponent } from './all-booking-data/all-booking-data.component';
import { DatePipe } from '@angular/common';
import { PnrstatusComponent } from './pnrstatus/pnrstatus.component';
import { MatSortModule } from '@angular/material/sort';
@NgModule({
  declarations: [
    AppComponent,
    SignupComponent,  
    NavbarComponent,
    SearchformComponent,
    SearchedlistComponent,
    TrainBookingComponent,
    LoginComponent,
    AlltrainsComponent,
    UpdateTrainComponent,
    BookingComponent,
    DoBookingComponent,
    UpdateUserComponent,
    PnrDialogComponentComponent,
    BookinghistoryComponent,
    AllBookingDataComponent,
    PnrstatusComponent,
   
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    MatButtonModule,
    MatDatepickerModule,
    MatNativeDateModule,
    MatCardModule,
    MatSnackBarModule,
    MatSelectModule,
    MatTableModule,
    MatPaginatorModule,
    MatIconModule,
    MatDialogModule,
    BrowserAnimationsModule,
    MatStepperModule,
    DatePipe,
    MatDatepickerModule,
    MatNativeDateModule,
    MatSortModule,
    
  ],
  providers: [  TrainSearchService,
     AuthGuard,[{provide:HTTP_INTERCEPTORS,useClass:AuthInterceptor,multi:true}]],
  bootstrap: [AppComponent]
})
export class AppModule { }
