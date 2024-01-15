package com.example.TrainBooking.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TrainBooking.entity.Train;
import com.example.TrainBooking.entity.TrainBooking;
import com.example.TrainBooking.repository.TrainBookingRepo;
import com.example.TrainBooking.service.TrainClient;


@RestController
@RequestMapping("/trainBooking")
public class TrainBookingController {
	@Autowired
	private TrainBookingRepo trainBookingRepo;

	@Autowired
	private TrainClient trainClient;
	
	
	@GetMapping("/getAllTrainBooking")
	public ResponseEntity<List<TrainBooking>> getAllTrains() 
	{
		List<TrainBooking> gettrainbooking =  trainBookingRepo.findAll();
		return new ResponseEntity<>(gettrainbooking, HttpStatus.OK);
	}
	
	@PostMapping("/addBooking")
    public ResponseEntity<TrainBooking> createTrainBooking(@RequestBody TrainBooking trainBooking) 
	{
		long randomUserId =(long) (Math.random() * 2 * Long.MAX_VALUE - Long.MIN_VALUE);
		trainBooking.setPnr(randomUserId);
		TrainBooking createdTrain = trainBookingRepo.save(trainBooking);
		Train trainData=trainClient.getTrainOfBooking(createdTrain.getTrainNo());
//		int a=trainData.getSeatsInFirstClassClass()+trainData.getSeatsInSecondClassClass()+trainData.getSeatsInSleeperClass()+trainData.getSeatsInSleeperClass();
		if(trainBooking.getClassType().equalsIgnoreCase("First") && trainData.getSeatsInFirstClassClass()>trainBooking.getTotalSeats())
		{
			int b=trainData.getSeatsInFirstClassClass()-trainBooking.getTotalSeats();
			trainData.setSeatsInFirstClassClass(b);
			trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
		}
		else if(trainBooking.getClassType().equalsIgnoreCase("Second") && trainData.getSeatsInSecondClassClass()>trainBooking.getTotalSeats())
		{
			int b=trainData.getSeatsInSecondClassClass()-trainBooking.getTotalSeats();
			trainData.setSeatsInSecondClassClass(b);
			trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
		}
		else if(trainBooking.getClassType().equalsIgnoreCase("Sleep") && trainData.getSeatsInSleeperClass()>trainBooking.getTotalSeats())
		{
				int b=trainData.getSeatsInSleeperClass()-trainBooking.getTotalSeats();
				trainData.setSeatsInSleeperClass(b);
				trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
		}
		else if(trainBooking.getClassType().equalsIgnoreCase("Third") && trainData.getSeatsInThirdClassClass()>trainBooking.getTotalSeats())
		{
				int b=trainData.getSeatsInThirdClassClass()-trainBooking.getTotalSeats();
				trainData.setSeatsInThirdClassClass(b);
				trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
		}
        return new ResponseEntity<>(createdTrain, HttpStatus.CREATED);
	}
	
	@PostMapping("/cancelSeats/{pnr}/{canceledSeats}")
	public TrainBooking cancelSeats(@PathVariable long pnr,@PathVariable int canceledSeats)
	{	
		TrainBooking tb= trainBookingRepo.getTrainBooking(pnr);
		Train trainData=trainClient.getTrainOfBooking(tb.getTrainNo());
		if(tb.getClassType().equalsIgnoreCase("First"))
		{
			int b=trainData.getSeatsInFirstClassClass()+canceledSeats;
			trainData.setSeatsInFirstClassClass(b);
			trainClient.updateTrain(tb.getTrainNo(), trainData);
		}
		else if(tb.getClassType().equalsIgnoreCase("Second"))
		{
			int b=trainData.getSeatsInSecondClassClass()+canceledSeats;
			trainData.setSeatsInSecondClassClass(b);
			trainClient.updateTrain(tb.getTrainNo(), trainData);
		}
		else if(tb.getClassType().equalsIgnoreCase("Third"))
		{
			int b=trainData.getSeatsInThirdClassClass()+canceledSeats;
			trainData.setSeatsInThirdClassClass(b);
			trainClient.updateTrain(tb.getTrainNo(), trainData);
		}
		else if(tb.getClassType().equalsIgnoreCase("Sleep"))
		{
			int b=trainData.getSeatsInSleeperClass()+canceledSeats;
			trainData.setSeatsInSleeperClass(b);
			trainClient.updateTrain(tb.getTrainNo(), trainData);
		}
		
		return tb;
		
	}
	
	@GetMapping("/{pnr}")
	public TrainBooking getTrainBooking(@PathVariable long pnr)
	{
		TrainBooking tb= trainBookingRepo.getTrainBooking(pnr);
		tb.setTrain(trainClient.getTrainOfBooking(tb.getTrainNo()));
		return tb;
	}

	@GetMapping("/get/{pnr}")
	public TrainBooking getTrainNo(@PathVariable long pnr)
	{
		return trainBookingRepo.getTrainBooking(pnr);
	}
//	@GetMapping("/{id}")
//	public TrainBooking getOne(@PathVariable String trainNo)
//	{
//		return quizService.get(id);
//	}
	
}