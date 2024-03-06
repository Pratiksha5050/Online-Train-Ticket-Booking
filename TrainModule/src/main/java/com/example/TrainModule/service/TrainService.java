package com.example.TrainModule.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.TrainModule.entity.Train;
import com.example.TrainModule.exception.TrainNotFoundException;
import com.example.TrainModule.repository.TrainInterface;

@Service
public class TrainService {

	@Autowired
	private TrainInterface trainInterface;
	
	public Train createTrain(Train train)
	{
		return trainInterface.save(train);
	}
	
	public List<Train> getAllTrains()
	{
		return trainInterface.findAll();
	}
	
	public Train getTrainByIds(String trainNo)
	{
		return trainInterface.getTrainOfBooking(trainNo);
	}
	
	public List<Train> searchTrain(String sourceStation,String destinationStation,Date dates)
	{
		return trainInterface.findBys(sourceStation, destinationStation,dates);
	}
	
	public Train updateTrain(String trainNo,Train updatedTrain)
	{
		Train existingTrain = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));	    
		
		existingTrain.setTrainName(updatedTrain.getTrainName());
		existingTrain.setTotalCoach(updatedTrain.getTotalCoach());
		existingTrain.setSourceStation(updatedTrain.getSourceStation());
		existingTrain.setDestinationStation(updatedTrain.getDestinationStation());
		existingTrain.setDepartureTimes(updatedTrain.getDepartureTimes());
		existingTrain.setArrivalTimes(updatedTrain.getArrivalTimes());
		existingTrain.setDates(updatedTrain.getDates());
		existingTrain.setSeatsInFirstClassClass(updatedTrain.getSeatsInFirstClassClass());
		existingTrain.setSeatsInSecondClassClass(updatedTrain.getSeatsInSecondClassClass());
		existingTrain.setSeatsInSleeperClass(updatedTrain.getSeatsInSleeperClass());
		existingTrain.setSeatsInThirdClassClass(updatedTrain.getSeatsInThirdClassClass());
		existingTrain.setFirstClassPrice(updatedTrain.getFirstClassPrice());
		existingTrain.setSecondClassPrice(updatedTrain.getSecondClassPrice());
		existingTrain.setThirdClassPrice(updatedTrain.getThirdClassPrice());
		existingTrain.setSleeperCoachPrice(updatedTrain.getSleeperCoachPrice());
		
		trainInterface.save(existingTrain);
		return existingTrain;
	}
	
	public void deleteTrain(String trainNo)
	{
		trainInterface.deleteById(trainNo);
	}
	
	public List<String> getUniqueSourceStations() {
        return trainInterface.findAll()
                .stream()
                .map(Train::getSourceStation)
                .distinct()
                .collect(Collectors.toList());
    }
	
	public List<String> getUniqueDestinationStations() {
        return trainInterface.findAll()
                .stream()
                .map(Train::getDestinationStation)
                .distinct()
                .collect(Collectors.toList());
    }
}
