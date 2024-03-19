import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Observable,forkJoin, Subject, catchError, map, of } from "rxjs";
import { Train, User } from "../models/train.model";

interface AuthResponse {
  token: string; // Adjust the type based on your actual response
}

@Injectable({
    providedIn: 'root'
  })

  export class TrainSearchService {
    
    
    trains: Train[] = [];
    users:User[]=[];
    isFetching=false;
    private apiUrl = 'http://localhost:8080/train';
 
    constructor(private http: HttpClient) { }
  
    getTrainsByData( source:string, destination:string ,date:Date): Observable<Train[]> {
      // console.log("http");
      console.log("console",source,destination,date);
       return this.http.get<Train[]>(`${this.apiUrl}/search/${source}/${destination}/${date}`);
    }


    createTrain(trainData: Train): Observable<Train> {
      console.log(trainData);
      const requestOptions:Object={responseType:'text'}
      return this.http.post<Train>(`${this.apiUrl}/save`, trainData,requestOptions);
    }

    isTrainNoUnique(trainNo: string): Observable<{ notUnique: boolean }> {
      console.log("hello");
      return this.http.get<{ notUnique: boolean }>(`${this.apiUrl}/isUnique/${trainNo}`);
      
    }
    

    postBookingData(bookingData: any) {
      console.log("Booking data from service",bookingData)
      const endpoint = `http://localhost:8081/trainBooking/addBooking`; // Adjust the endpoint accordingly
      return this.http.post<String>(endpoint, bookingData);
    }


    checkUsernameUniqueness(username: string): Observable<boolean> {
      const url = `${this.apiUrl}/checkUsernameUniqueness?username=${username}`;
  
      return this.http.get<boolean>(url).pipe(
        catchError(() => of(true)) // Assume it's unique in case of an error
      );
    }

    getAllTrains(): Observable<Train[]> {
      const url = `${this.apiUrl}/getAllTrains`;
      return this.http.get<Train[]>(url);
    }

    deleteTrain(trainNo: string): Observable<void> {
      const url = `${this.apiUrl}/delete/${trainNo}`;
      return this.http.delete<void>(url);
    }

    updateTrain(train: Train): Observable<void> {
      
      return this.http.put<void>(`${this.apiUrl}/update/${train.trainNo}`, train);
    }

    private apiUrl1 = 'http://localhost:8082/auth';

    updateUser(userName: string, userData: any): Observable<any> {
      const requestOptions:Object={responseType:'text'}
      return this.http.put(`${this.apiUrl1}/update/${userName}`, userData,requestOptions);
    }
  

    getUniqueSourceStations(): Observable<string[]> {
      return this.http.get<string[]>('http://localhost:8080/train/source');
    }

    getUniqueDestinationStations(): Observable<string[]> {
      return this.http.get<string[]>('http://localhost:8080/train/destination');
    }

    // private userRole: string | null = null;
    private username!: string;

login(credentials: any):Observable<any>
{
  console.log("Server");
  const requestOptions:Object={responseType:'text'}
  return this.http.post<String>(`${this.apiUrl1}/login`,credentials,requestOptions);
}

getUserInfo() {
  // Assuming you have an endpoint like /userinfo that returns user information
  const username=this.getUsername();
  console.log("userName",username);
  return this.http.get<User>(`http://localhost:8082/auth/getUsers/${username}`);
}

getUserInfoByName(username:any)
{
  console.log("by name",username)
  return this.http.get<User>(`http://localhost:8082/auth/getUsers/${username}`);
}

getBookingHistory(userName:any)
{
  console.log("history",userName);
  // const url=`http://localhost:8081/trainBooking/getByName/${userName}`
  return this.http.get<any>(`http://localhost:8081/trainBooking/getByName/${userName}`);
}

cancelBooking(pnr: string, seatIndex: number){
  const url = `http://localhost:8081/trainBooking/cancelSeats/${pnr}/${seatIndex}`;
    return this.http.delete(url);
  // return this.http.post(url);
}

registerUser(userData: User) {
  const endpoint = `http://localhost:8082/auth/addUser`;
  const requestOptions:Object={responseType:'text'}
  return this.http.post(endpoint, userData,requestOptions);
}

getBookingHistoryForAllUsers(): Observable<any[]> {
  return this.http.get<any[]>(`http://localhost:8081/trainBooking/getAllTrainBooking`);
} 

getBookingInfoByPnr(pnr:string)
{
  const url = `http://localhost:8081/trainBooking/${pnr}`;
  return this.http.get<any[]>(url);
}

loginUser(token: any,user:any) {
  localStorage.setItem('token', token);
  localStorage.setItem('Username', user);
  return true;
}

isLoggedIn() {
  let token = localStorage.getItem('token');
  if (token == undefined || token === '' || token == null) {
    return false;
  } else {
    return true;
  }
}

onLogOut() {
  localStorage.removeItem('token');
  localStorage.removeItem('Username');
  return true;
}

getToken() {
  return localStorage.getItem('token');
  
}

getUsername()
{
  return localStorage.getItem('Username');
}

getUser()
{
  const url = `http://localhost:8082/auth/getUsers`;
  return this.http.get<User[]>(url);
}
}



