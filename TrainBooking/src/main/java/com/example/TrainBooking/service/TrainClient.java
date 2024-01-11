package com.example.TrainBooking.service;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.TrainBooking.entity.Train;


//@FeignClient(name = "TRAIN-SERVICE")

@FeignClient(url = "http://localhost:8080", value = "TRAIN-SERVICE")
public interface TrainClient 
{	
	@GetMapping("/train/{trainNo}")
	Train getTrainOfBooking(@PathVariable("trainNo") String trainNo);
}
