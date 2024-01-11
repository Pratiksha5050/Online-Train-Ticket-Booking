package com.example.TrainModule.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.example.TrainModule.entity.Train;
import com.example.TrainModule.exception.TrainNotFoundException;
import com.example.TrainModule.repository.TrainInterface;

@RestController
@RequestMapping("/train")
public class TrainController {

	@Autowired
	private TrainInterface trainInterface;

	@GetMapping("/getAllTrains")
	public ResponseEntity<List<Train>> getAllTrains() 
	{
		List<Train> gettrain = trainInterface.findAll();
        return new ResponseEntity<>(gettrain, HttpStatus.OK);
	}

//	@GetMapping("/search")
//	public List<Train> searchTrains(@RequestParam List<String> sourceStation,
//			@RequestParam List<String> destinationStation, @RequestParam List<String> dates) {
//		return trainInterface.findBy(sourceStation, destinationStation, dates);
//	}
	
//	@GetMapping("/search/{sourceStation}/{destinationStation}")
//	public List<Train> searchTrains(@PathVariable String sourceStation, @PathVariable String destinationStation
//			) {
////		List<String> sourceStations = Arrays.asList(sourceStation);
////		List<String> destinationStations = Arrays.asList(destinationStation);
////		List<Date> datesList = Arrays.asList(dates);
//
////		return trainInterface.findBy(sourceStation, destinationStation, dates);
//		return trainInterface.findBy(sourceStation, destinationStation);
//	}
	
	@GetMapping("/search/{sourceStation}/{destinationStation}/{dates}")
    public List<Train> searchTrains(
            @PathVariable("sourceStation") String sourceStation,
            @PathVariable("destinationStation") String destinationStation,
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dates) 
	{

        return trainInterface.findBys(sourceStation, destinationStation,dates);
    }

	@PostMapping("/save")
    public ResponseEntity<Train> createTrain(@RequestBody Train train) 
	{
		Train createdTrain = trainInterface.save(train);
        return new ResponseEntity<>(createdTrain, HttpStatus.CREATED);
	}

	@PutMapping("/{trainNo}")
    public ResponseEntity<Train> updateTrain(@PathVariable String trainNo, @RequestBody Train updatedTrain) {
		Train existingTrain = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));

		existingTrain.setTrainName(updatedTrain.getTrainName());
		existingTrain.setTotalCoach(updatedTrain.getTotalCoach());
		existingTrain.setSourceStation(updatedTrain.getSourceStation());
		existingTrain.setDestinationStation(updatedTrain.getDestinationStation());
		existingTrain.setSeatInEachCoach(updatedTrain.getSeatInEachCoach());
		existingTrain.setPrice(updatedTrain.getPrice());
		existingTrain.setDepartureTimes(updatedTrain.getDepartureTimes());
		existingTrain.setArrivalTimes(updatedTrain.getArrivalTimes());
		existingTrain.setDates(updatedTrain.getDates());

		trainInterface.save(existingTrain);
		return existingTrain != null
                ? new ResponseEntity<>(existingTrain, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
	}

	@DeleteMapping("/delete/{trainNo}")
	public ResponseEntity<Void> deleteTrain(@PathVariable String trainNo) {
		
		Train existingTrain = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));

		if (existingTrain != null) {
			trainInterface.deleteById(trainNo);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } 
		else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
		
	}

	@PutMapping("/{trainNo}/addSeats/{additionalSeats}")
	public ResponseEntity<String> addSeatsForTrain(@PathVariable String trainNo, @PathVariable int additionalSeats) {

		Train train = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));
		train.setSeatInEachCoach(additionalSeats + train.getSeatInEachCoach());
		System.out.print(additionalSeats);
		trainInterface.save(train);
		return ResponseEntity.ok("Seats added successfully.");
	}

	@GetMapping("/getSeats/{trainNo}")
	public ResponseEntity<String> getSeatsByID(@PathVariable String trainNo) {
		Train train = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));
		int a = train.getSeatInEachCoach();
		return ResponseEntity.ok("Total Seats are :" + a);
	}

	@PostMapping("/{trainNo}/schedule/{departureTimes}/{arrivalTimes}/{dates}")
	public ResponseEntity<String> scheduleTrain(@PathVariable String trainNo,
		@PathVariable String departureTimes,@PathVariable String arrivalTimes,
		@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dates) {

		try {
			// Set the provided schedule information to the train entity
			Train train = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));

//			train.setDepartureTimes(Collections.singletonList(departureTimes));
			train.getDepartureTimes().add(departureTimes);
			train.getArrivalTimes().add(arrivalTimes);
			train.getDates().add(dates);
			trainInterface.save(train);
			return new ResponseEntity<>("Train scheduled successfully", HttpStatus.CREATED);
		} 
		catch (Exception e) 
		{
			return new ResponseEntity<>("Failed to schedule train: " + e.getMessage(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
//	@GetMapping("/{trainNo}")
//	public List<Train> getTrainBooking(@PathVariable String trainNo) {
//		Train train = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));
//		train.set .setQuestions(questionClient.getQuestionOfQuiz(quiz.getId()));
//		return quiz;
//	
//		return ;
//	}
	
	@GetMapping("/{trainNo}")
	public Train getTrainOfBooking(@PathVariable String trainNo) 
	{
		return trainInterface.getTrainOfBooking(trainNo);
	}
}
