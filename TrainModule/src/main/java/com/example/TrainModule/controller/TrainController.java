package com.example.TrainModule.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.example.TrainModule.service.TrainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@CrossOrigin(origins="http://localhost:4200")
@RestController
@RequestMapping("/train")
public class TrainController {

	Logger logger = LoggerFactory.getLogger(TrainController.class); 
	
	@Autowired
	private TrainInterface trainInterface;
	
	@Autowired 
	private TrainService trainService;

	@GetMapping("/getAllTrains")
	public ResponseEntity<List<Train>> getAllTrains() 
	{
		try 
		{
			List<Train> gettrain = trainService.getAllTrains();
			return new ResponseEntity<>(gettrain, HttpStatus.OK);
		}
		catch (Exception e) {  
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
	
	 @GetMapping("/{trainNo}")
	    public ResponseEntity<Train> getTrainOfBooking(@PathVariable String trainNo) {
	        try {
	            Train train = trainService.getTrainByIds(trainNo);
	            if (train != null) 
	            {
	                return ResponseEntity.ok(train);
	            } 
	            else 
	            {
	            	  logger.error("Error fetching Train Information");
	            	// Train not found, return 404 Not Found status
	                return ResponseEntity.notFound().build();
	            }
	        } catch (TrainNotFoundException e) {
	            // Train not found exception, return 404 Not Found status
	        	// Log the exception or handle it as needed
	            logger.error("Error fetching unique destination stations: {}", e.getMessage());
	            return ResponseEntity.notFound().build();
	        } catch (Exception e) {
	            // Other exceptions, return 500 Internal Server Error status
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	

	 @GetMapping("/search/{sourceStation}/{destinationStation}/{dates}")
	    public ResponseEntity<List<Train>> searchTrains(
	            @PathVariable("sourceStation") String sourceStation,
	            @PathVariable("destinationStation") String destinationStation,
	            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dates) {
	        try 
	        {
	            logger.info("Searching trains from {} to {} on date {}", sourceStation, destinationStation, dates);
	            List<Train> trains = trainService.searchTrain(sourceStation, destinationStation, dates);
	            if (!trains.isEmpty()) 
	            {
	                logger.info("Trains found: {}", trains);
	                return ResponseEntity.ok(trains);
	            } else 
	            {
	                logger.warn("No trains found for the given criteria");
	                // No trains found for the given criteria, return 404 Not Found status
	                return ResponseEntity.notFound().build();
	            }
	        } catch (Exception e) 
	        {
	            logger.error("Error occurred while searching trains: {}", e.getMessage());
	            // Handle any other exceptions and return 500 Internal Server Error status
	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	        }
	    }

	
	@PostMapping("/save")
	public ResponseEntity<String> createTrain(@RequestBody Train train) {
	    try 
	    {
	        if (trainInterface.existsById(train.getTrainNo())) 
	        {
	            return ResponseEntity.status(HttpStatus.CONFLICT).body("Train already exists with trainNo: " + train.getTrainNo());
	        } else 
	        {
	            Train createdTrain = trainService.createTrain(train);
	            return ResponseEntity.status(HttpStatus.CREATED).body("Train added successfully with trainNo: " + train.getTrainNo());
	        }
	    } 
	    catch (Exception e) 
	    {
	        // Return an internal server error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
	    }
	}

	
	@PutMapping("/update/{trainNo}")
	public ResponseEntity<?> updateTrain(@PathVariable String trainNo, @RequestBody Train updatedTrain) {
	    try {
	        Train updated = trainService.updateTrain(trainNo, updatedTrain);
	        return ResponseEntity.ok(updated);
	    } catch (Exception e) {
	        // Return an internal server error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.");
	    }
	}


	
	@DeleteMapping("/delete/{trainNo}")
	public ResponseEntity<Void> deleteTrain(@PathVariable String trainNo) {
	    try {
	        Optional<Train> existingTrain = trainInterface.findById(trainNo);
	        if (existingTrain.isPresent()) 
	        {
	            trainService.deleteTrain(trainNo);
	            return ResponseEntity.noContent().build();
	        } 
	        else 
	        {
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        // Return an internal server error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}


	
	@GetMapping("/getSeats/{trainNo}")
	public ResponseEntity<String> getSeatsByID(@PathVariable String trainNo) {
	    try {
	        Train train = trainInterface.findById(trainNo).orElseThrow(() -> new TrainNotFoundException(trainNo));
	        int totalSeats = train.getSeatsInFirstClassClass() + train.getSeatsInSecondClassClass() +
	                         train.getSeatsInSleeperClass() + train.getSeatsInThirdClassClass();
	        return ResponseEntity.ok("Total Seats are: " + totalSeats);
	    } catch (TrainNotFoundException e) {
	        // Return a 404 Not Found response
	        return ResponseEntity.notFound().build();
	    } catch (Exception e) {
	        
	        // Return an internal server error response
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	

	@GetMapping("/source")
    public ResponseEntity<List<String>> getUniqueSourceStations() {
        try 
        {
            List<String> sourceStations = trainService.getUniqueSourceStations();
            return ResponseEntity.ok(sourceStations);
        } catch (Exception e) 
        {
            // Return an error response with status code 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	@GetMapping("/destination")
    public ResponseEntity<List<String>> getUniqueDestinationStations() {
        try {
            List<String> destinationStations = trainService.getUniqueDestinationStations();
            return ResponseEntity.ok(destinationStations);
        } catch (Exception e) {
            // Return an error response with status code 500
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
