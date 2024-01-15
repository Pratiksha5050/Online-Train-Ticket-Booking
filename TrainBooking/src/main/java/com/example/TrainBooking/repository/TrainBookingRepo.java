package com.example.TrainBooking.repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.TrainBooking.entity.TrainBooking;
import java.util.*;
public interface TrainBookingRepo extends MongoRepository<TrainBooking, String>
{
	@Query("{'pnr':?0}")
	TrainBooking getTrainBooking(@Param ("pnr")long pnr );
}
