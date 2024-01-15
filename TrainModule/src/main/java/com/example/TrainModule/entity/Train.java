package com.example.TrainModule.entity;


import java.time.LocalTime;
import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "train")
public class Train{
	@Id
	private String trainNo;
	private String trainName;
	private String sourceStation;
	private String destinationStation;
	private String departureTimes; // Represented as "HH:mm:ss"
    private String arrivalTimes;   // Represented as "HH:mm:ss"  
    private Date dates;
    private int totalCoach = 4;
    private int seatsInSleeperClass = 30;
    private int seatsInFirstClassClass = 30;
    private int seatsInSecondClassClass = 30;
    private int seatsInThirdClassClass = 30;
  
    
	public Train() {
		super();
	}

	


	public Train(String trainNo, String trainName, String sourceStation, String destinationStation,
			String departureTimes, String arrivalTimes, Date dates, int totalCoach, int seatsInSleeperClass,
			int seatsInFirstClassClass, int seatsInSecondClassClass, int seatsInThirdClassClass) {
		super();
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.sourceStation = sourceStation;
		this.destinationStation = destinationStation;
		this.departureTimes = departureTimes;
		this.arrivalTimes = arrivalTimes;
		this.dates = dates;
		this.totalCoach = totalCoach;
		this.seatsInSleeperClass = seatsInSleeperClass;
		this.seatsInFirstClassClass = seatsInFirstClassClass;
		this.seatsInSecondClassClass = seatsInSecondClassClass;
		this.seatsInThirdClassClass = seatsInThirdClassClass;
	}




	public String getTrainNo() {
		return trainNo;
	}


	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}


	public String getTrainName() {
		return trainName;
	}


	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}


	public String getSourceStation() {
		return sourceStation;
	}


	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}


	public String getDestinationStation() {
		return destinationStation;
	}


	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}


	


	
	public int getTotalCoach() {
		return totalCoach;
	}


	public void setTotalCoach(int totalCoach) {
		this.totalCoach = totalCoach;
	}


	public int getSeatsInSleeperClass() {
		return seatsInSleeperClass;
	}


	public String getDepartureTimes() {
		return departureTimes;
	}

	public void setDepartureTimes(String departureTimes) {
		this.departureTimes = departureTimes;
	}

	public String getArrivalTimes() {
		return arrivalTimes;
	}

	public void setArrivalTimes(String arrivalTimes) {
		this.arrivalTimes = arrivalTimes;
	}

	public Date getDates() {
		return dates;
	}

	public void setDates(Date dates) {
		this.dates = dates;
	}

	public void setSeatsInSleeperClass(int seatsInSleeperClass) {
		this.seatsInSleeperClass = seatsInSleeperClass;
	}


	public int getSeatsInFirstClassClass() {
		return seatsInFirstClassClass;
	}


	public void setSeatsInFirstClassClass(int seatsInFirstClassClass) {
		this.seatsInFirstClassClass = seatsInFirstClassClass;
	}


	public int getSeatsInSecondClassClass() {
		return seatsInSecondClassClass;
	}


	public void setSeatsInSecondClassClass(int seatsInSecondClassClass) {
		this.seatsInSecondClassClass = seatsInSecondClassClass;
	}


	public int getSeatsInThirdClassClass() {
		return seatsInThirdClassClass;
	}


	public void setSeatsInThirdClassClass(int seatsInThirdClassClass) {
		this.seatsInThirdClassClass = seatsInThirdClassClass;
	}
    
	
}
