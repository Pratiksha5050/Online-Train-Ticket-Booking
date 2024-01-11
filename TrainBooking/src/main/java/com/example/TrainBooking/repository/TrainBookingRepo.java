package com.example.TrainBooking.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.example.TrainBooking.entity.TrainBooking;
import java.util.*;
public interface TrainBookingRepo extends MongoRepository<TrainBooking, String>{
	
}
