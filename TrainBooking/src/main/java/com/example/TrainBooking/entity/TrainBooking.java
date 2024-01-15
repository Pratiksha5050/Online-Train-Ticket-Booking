package com.example.TrainBooking.entity;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "booking")
public class TrainBooking 
{
	@Id
	private long pnr;
	private int totalSeats;
	private String trainNo;
	private int userId;
	private String classType;
	private transient Train train;
	
	public TrainBooking() 
	{
		super();
	}

	


	public TrainBooking(long pnr, int totalSeats, String trainNo, int userId, String classType, Train train) {
		super();
		this.pnr = pnr;
		this.totalSeats = totalSeats;
		this.trainNo = trainNo;
		this.userId = userId;
		this.classType = classType;
		this.train = train;
	}




	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public long getPnr() {
		return pnr;
	}


	public void setPnr(long pnr) 
	{
		this.pnr = pnr;
	}


	public int getTotalSeats() {
		return totalSeats;
	}


	public void setTotalSeats(int totalSeats) {
		this.totalSeats = totalSeats;
	}


	public String getTrainNo() {
		return trainNo;
	}


	public void setTrainNo(String trainNo) {
		this.trainNo = trainNo;
	}


	public int getUserId() {
		return userId;
	}


	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getClassType() {
		return classType;
	}

	public void setClassType(String classType) {
		this.classType = classType;
	}

}
