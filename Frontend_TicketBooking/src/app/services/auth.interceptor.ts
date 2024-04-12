// token.interceptor.ts
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { TrainSearchService } from './trainModule.service';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  constructor(private loginService:TrainSearchService){}
  intercept(request: HttpRequest<any>,next: HttpHandler): Observable<HttpEvent<any>> 
  {
      let newReq=request;
      let token=this.loginService.getToken();
    
    console.log("Interceptor",token);
    if(token!=null)
    {
      newReq=newReq.clone({setHeaders:{Authorization:`Bearer ${token}`}})
    }
    
    return next.handle(newReq);
  }
}
