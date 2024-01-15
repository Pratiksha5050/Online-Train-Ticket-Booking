package com.example.TrainModule.repository;

import java.util.Date;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import com.example.TrainModule.entity.Train;

@Service
public interface TrainInterface extends MongoRepository<Train, String> {

//	@Query("{'sourceStation' : :#{#sourceStation}, 'destinationStation' : :#{#destinationStation},'dates': :#{#dates}")
//	List<Train> findBy(@Param("sourceStation") String sourceStation,
//			@Param("destinationStation") String destinationStation, @Param("dates") Date dates);
	
	@Query("{'sourceStation' : :#{#sourceStation}, 'destinationStation' : :#{#destinationStation},'dates' : :#{#dates}}")
	List<Train> findBys(@Param("sourceStation") String sourceStation,
			@Param("destinationStation") String destinationStation,
			@Param("dates") Date dates);
	
	@Query("{'trainNo' : :#{#trainNo}}")
	Train getTrainOfBooking(@Param("trainNo") String trainNo);

}
