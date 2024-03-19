import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SignupComponent } from './signup/signup.component';
import { SearchformComponent } from './searchform/searchform.component';
import { SearchedlistComponent } from './searchform/searchedlist/searchedlist.component';
import { TrainBookingComponent } from './train-booking/train-booking.component';
import { LoginComponent } from './login/login.component';
import { AlltrainsComponent } from './alltrains/alltrains.component';
import { BookingComponent } from './booking/booking.component';
import { DoBookingComponent } from './do-booking/do-booking.component';
import { AuthGuard } from './services/auth.guard';
import { UpdateUserComponent } from './update-user/update-user.component';
import { BookinghistoryComponent } from './bookinghistory/bookinghistory.component';
import { AllBookingDataComponent } from './all-booking-data/all-booking-data.component';
import { PnrstatusComponent } from './pnrstatus/pnrstatus.component';

const routes: Routes = [
  { path: '', redirectTo:"/search" ,pathMatch:"full" },
  { path: 'search', component: SearchformComponent },
  { path: 'signup', component: SignupComponent },
  {path:'search-list' ,component:SearchedlistComponent},
  { path: 'train-booking', component: TrainBookingComponent , canActivate:[AuthGuard]},
  {path:'login',component:LoginComponent},
  {path:'alltrains',component:AlltrainsComponent ,canActivate:[AuthGuard] },
  {path:'booking',component:DoBookingComponent,canActivate:[AuthGuard] },
  { path: 'updateuser', component: UpdateUserComponent , canActivate:[AuthGuard] },
  {path:'bookinghistory',component:BookinghistoryComponent, canActivate:[AuthGuard] }
  ,{path:'allBookingData',component:AllBookingDataComponent, canActivate:[AuthGuard] },
  {path:'pnrstatus',component:PnrstatusComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  declarations: [],
  exports: [RouterModule]
})
export class AppRoutingModule { }
