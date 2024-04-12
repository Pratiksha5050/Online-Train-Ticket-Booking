import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable, forkJoin, Subject, catchError, map, of } from "rxjs";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { TrainSearchService } from "./trainModule.service";



@Injectable({
    providedIn: 'root'
})


export class AuthGuard implements CanActivate {
    constructor(private service: TrainSearchService,private router:Router) { }
    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot):
        boolean | UrlTree | Observable<boolean | UrlTree> | Promise<boolean | UrlTree> {
        // throw new Error("Method not implemented.");
        if (this.service.isLoggedIn()) 
        {
            return true;
        }
        else {
            this.router.navigate(['login'])
            return false;
        }
    }
}