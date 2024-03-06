package com.example.TrainBooking.entity;

import java.util.Date;
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
	private String userName;
	private String classType;
	private double Price;
	private List<UserInfo> userInfo;
	private transient Train train;
	private Date dates;
	
	
	public TrainBooking() {
		super();
	}
	public TrainBooking(long pnr, int totalSeats, String trainNo, String userName, String classType, double Price,
			List<UserInfo> userInfo, Train train, Date dates) {
		super();
		this.pnr = pnr;
		this.totalSeats = totalSeats;
		this.trainNo = trainNo;
		this.userName = userName;
		this.classType = classType;
		this.Price = Price;
		this.userInfo = userInfo;
		this.train = train;
		this.dates=dates;
	}
	public long getPnr() {
		return pnr;
	}
	public Date getDates() {
		return dates;
	}
	public void setDates(Date dates) {
		this.dates = dates;
	}
	public void setPnr(long pnr) {
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double Price) {
		this.Price = Price;
	}
	
	public List<UserInfo> getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(List<UserInfo> userInfo) {
		this.userInfo = userInfo;
	}
	public Train getTrain() {
		return train;
	}
	public void setTrain(Train train) {
		this.train = train;
	}
}
