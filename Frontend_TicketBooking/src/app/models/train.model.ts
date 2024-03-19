export interface Train{
    trainNo:string;
	trainName:string;
	sourceStation:string;
	destinationStation:string;
	departureTimes:string; // Represented as "HH:mm:ss"
    arrivalTimes:string;   // Represented as "HH:mm:ss"  
    dates:Date;
    totalCoach:number ;
    seatsInSleeperClass :number;
    seatsInFirstClassClass :number ;
    seatsInSecondClassClass :number ;
    seatsInThirdClassClass :number ;
    firstClassPrice:number,
    secondClassPrice:number,
    thirdClassPrice:number,
    sleeperCoachPrice:number
}

export interface Passenger {
    passenger: any;
    name: string;
    age: number;
    gender: string;
  }
  export interface User {
    userName: string;
    password: string;
    // confirmPassword: string;
    firstName: string;
    middleName: string;
    lastName: string;
    dateOfBirth: Date;
    gender: string;
    mobileNumber: string;
    email: string;
    nationality: string;
    role:string
  }
  
  