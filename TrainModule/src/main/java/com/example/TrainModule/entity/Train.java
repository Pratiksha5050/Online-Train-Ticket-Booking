package com.example.TrainModule.entity;

import java.util.Date;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "train")
public class Train{
	
	private String trainNo;
	private String trainName;
	private int totalCoach;
	private String sourceStation;
	private String destinationStation;
	private int seatInEachCoach;
	private List<String> departureTimes; 
    private List<String> arrivalTimes;   
    private List<Date> dates;
    private double price;
    
	public Train() {
		super();
	}

	public Train(String trainNo, String trainName, int totalCoach, String sourceStation, String destinationStation,
			int seatInEachCoach, List<String> departureTimes, List<String> arrivalTimes, List<Date> dates,double price) {
		super();
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.totalCoach = totalCoach;
		this.sourceStation = sourceStation;
		this.destinationStation = destinationStation;
		this.seatInEachCoach = seatInEachCoach;
		this.departureTimes = departureTimes;
		this.arrivalTimes = arrivalTimes;
		this.dates = dates;
		this.price=price;
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

	public int getTotalCoach() {
		return totalCoach;
	}

	public void setTotalCoach(int totalCoach) {
		this.totalCoach = totalCoach;
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

	public int getSeatInEachCoach() {
		return seatInEachCoach;
	}

	public void setSeatInEachCoach(int seatInEachCoach) {
		this.seatInEachCoach = seatInEachCoach;
	}

	public List<String> getDepartureTimes() {
		return departureTimes;
	}

	public void setDepartureTimes(List<String> departureTimes) {
		this.departureTimes = departureTimes;
	}

	public List<String> getArrivalTimes() {
		return arrivalTimes;
	}

	public void setArrivalTimes(List<String> arrivalTimes) {
		this.arrivalTimes = arrivalTimes;
	}

	public List<Date> getDates() {
		return dates;
	}

	public void setDates(List<Date> dates) {
		this.dates = dates;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	

	
	
    
	
}
