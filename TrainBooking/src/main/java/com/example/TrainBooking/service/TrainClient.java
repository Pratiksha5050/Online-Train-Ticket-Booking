package com.example.TrainBooking.service;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.TrainBooking.entity.Train;


//@FeignClient(name = "TRAIN-SERVICE")

@FeignClient(url = "http://localhost:8080", value = "TRAIN-SERVICE")
public interface TrainClient 
{	
	@GetMapping("/train/{trainNo}")
	Train getTrainOfBooking(@PathVariable("trainNo") String trainNo);
	
	
	@PutMapping("/train/update/{trainNo}")
    Train updateTrain(@PathVariable String trainNo, @RequestBody Train updatedTrain);	
	
}
