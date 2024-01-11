package com.example.TrainBooking.service;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.example.TrainBooking.entity.Train;


//@FeignClient(url = "http://localhost:8082", value = "Question-Client")
@FeignClient(name = "TRAIN-SERVICE")
public interface TrainClient 
{	
	@GetMapping("train/trainBooking/{trainNo}")
	List<Train> getTrainBooking(@PathVariable String trainNo);
}
