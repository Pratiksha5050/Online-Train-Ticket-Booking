package com.example.TrainBooking.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.TrainBooking.entity.Train;
import com.example.TrainBooking.entity.TrainBooking;
import com.example.TrainBooking.repository.TrainBookingRepo;

@Service
public class TrainBookingService {

	@Autowired
    private TrainBookingRepo trainBookingRepo;

    public List<TrainBooking> getAllTrainBookingInfo() {
        return trainBookingRepo.findAll();
    }

	public void deleteTrainByPnr(long pnr) {
		trainBookingRepo.deleteById(pnr);
	}

	public TrainBooking getByBookingInfoByPNR(long pnr) {
		return trainBookingRepo.getTrainBooking(pnr);
	}

	public List<TrainBooking> findBookingByUserName(String userName) {
		return trainBookingRepo.FindByuserName(userName);
	}
	
	@Autowired
    private TrainClient trainClient;

    public TrainBooking createTrainBooking(TrainBooking trainBooking) 
    {
        long randomBookingId = (long) (Math.random() * Long.MAX_VALUE / 1000);
        trainBooking.setPnr(randomBookingId);

        TrainBooking createdTrain = trainBookingRepo.save(trainBooking);
        Train trainData = trainClient.getTrainOfBooking(createdTrain.getTrainNo());

        if (trainBooking.getClassType().equalsIgnoreCase("First") &&
                trainData.getSeatsInFirstClassClass() > trainBooking.getTotalSeats()) 
        {
            int b = trainData.getSeatsInFirstClassClass() - trainBooking.getTotalSeats();
            trainData.setSeatsInFirstClassClass(b);
            trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getFirstClassPrice());
            trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
        } 
        else if (trainBooking.getClassType().equalsIgnoreCase("Second") &&
                trainData.getSeatsInSecondClassClass() > trainBooking.getTotalSeats()) 
        {
            int b = trainData.getSeatsInSecondClassClass() - trainBooking.getTotalSeats();
            trainData.setSeatsInSecondClassClass(b);
            trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getSecondClassPrice());
            trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
        }
        else if (trainBooking.getClassType().equalsIgnoreCase("Sleep") &&
                trainData.getSeatsInSleeperClass() > trainBooking.getTotalSeats()) 
        {
            int b = trainData.getSeatsInSleeperClass() - trainBooking.getTotalSeats();
            trainData.setSeatsInSleeperClass(b);
            trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getSleeperCoachPrice());
            trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
        } 
        else if (trainBooking.getClassType().equalsIgnoreCase("Third") &&
                trainData.getSeatsInThirdClassClass() > trainBooking.getTotalSeats()) 
        {
            int b = trainData.getSeatsInThirdClassClass() - trainBooking.getTotalSeats();
            trainData.setSeatsInThirdClassClass(b);
            trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getThirdClassPrice());
            trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
        }
        trainBookingRepo.save(trainBooking);
        return createdTrain;
    }
    
    public TrainBooking cancelSeats(long pnr, int canceledSeatsIndex) {
        TrainBooking tb = trainBookingRepo.getTrainBooking(pnr);
        if (tb.getTotalSeats() > 0) {
            Train trainData = trainClient.getTrainOfBooking(tb.getTrainNo());
            if (tb.getClassType().equalsIgnoreCase("First")) {
                int b = trainData.getSeatsInFirstClassClass() + 1;
                trainData.setSeatsInFirstClassClass(b);
                tb.setPrice(tb.getPrice() - trainData.getFirstClassPrice());
                trainClient.updateTrain(tb.getTrainNo(), trainData);
            } else if (tb.getClassType().equalsIgnoreCase("Second")) {
                int b = trainData.getSeatsInSecondClassClass() + 1;
                trainData.setSeatsInSecondClassClass(b);
                tb.setPrice(tb.getPrice() - trainData.getSecondClassPrice());
                trainClient.updateTrain(tb.getTrainNo(), trainData);
            } else if (tb.getClassType().equalsIgnoreCase("Third")) {
                int b = trainData.getSeatsInThirdClassClass() + 1;
                trainData.setSeatsInThirdClassClass(b);
                tb.setPrice(tb.getPrice() - trainData.getThirdClassPrice());
                trainClient.updateTrain(tb.getTrainNo(), trainData);
            } else if (tb.getClassType().equalsIgnoreCase("Sleep")) {
                int b = trainData.getSeatsInSleeperClass() + 1;
                trainData.setSeatsInSleeperClass(b);
                tb.setPrice(tb.getPrice() - trainData.getSleeperCoachPrice());
                trainClient.updateTrain(tb.getTrainNo(), trainData);
            }
            tb.setTotalSeats(tb.getTotalSeats() - 1);
            tb.getUserInfo().remove(canceledSeatsIndex - 1);
            trainBookingRepo.save(tb);
        }
        if (tb.getTotalSeats() == 0) {
            trainBookingRepo.deleteById(pnr);
        }
        return tb;
    }
}
