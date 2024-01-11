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
	public ResponseEntity<List<TrainBooking>> getAllTrains() {
		List<TrainBooking> gettrainbooking =  trainBookingRepo.findAll();
		return new ResponseEntity<>(gettrainbooking, HttpStatus.OK);
	}
	
	@PostMapping("/addBooking")
    public ResponseEntity<TrainBooking> createTrain(@RequestBody TrainBooking trainBooking) 
	{
		TrainBooking createdTrain = trainBookingRepo.save(trainBooking);
        return new ResponseEntity<>(createdTrain, HttpStatus.CREATED);
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