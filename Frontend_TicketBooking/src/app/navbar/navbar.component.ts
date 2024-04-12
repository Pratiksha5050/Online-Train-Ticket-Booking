import { Component, OnInit } from '@angular/core';
import { TrainSearchService } from '../services/trainModule.service';
import { User } from '../models/train.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrl: './navbar.component.css'
})
export class NavbarComponent implements OnInit {

  public loggedIn = false;
  username!: string | null;
  user!: User | null;
  userRole!: string | null;
  userName!:string
  constructor(public loginService: TrainSearchService,private router:Router,) { }

  ngOnInit(): void {
    this.loggedIn = this.loginService.isLoggedIn();
    this.username = this.loginService.getUsername();
    if (this.loggedIn) {
      this.loginService.getUserInfo().subscribe(
        (response) => {
          this.user=response;
          this.userRole=this.user.role
          this.username=this.user.userName
        }, (error) => {
-         console.error(error);
        }
      )
    }
  }
  logoutUser() {
    this.loginService.onLogOut();
    location.reload();
  }

getAlldata() {

    console.log(this.loginService.getUserInfo())
}

update()
{
  console.log("from navbar",this.username)
  this.router.navigate(['/updateuser'], {
    queryParams: {
     userName:this.username
    }
  });
}


}
