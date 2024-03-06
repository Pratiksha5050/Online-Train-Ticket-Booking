package com.example.TrainBooking.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.TrainBooking.entity.Train;
import com.example.TrainBooking.entity.TrainBooking;
import com.example.TrainBooking.repository.TrainBookingRepo;
import com.example.TrainBooking.service.TrainBookingService;
import com.example.TrainBooking.service.TrainClient;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/trainBooking")
public class TrainBookingController {
	
	@Autowired
	private TrainBookingService trainBookingService;
	
	@Autowired
	private TrainBookingRepo trainBookingRepo;

	@Autowired
	private TrainClient trainClient;
	
	Logger logger = LoggerFactory.getLogger(TrainBookingController.class);

	public ResponseEntity<List<TrainBooking>> getAllTrainBookings() {
        try {
            List<TrainBooking> trainBookings = trainBookingService.getAllTrainBookingInfo();
            return ResponseEntity.ok(trainBookings);
        } 
        catch (Exception e) {
            // Log the error
            logger.error("An error occurred while fetching all train bookings: {}", e.getMessage());
            // Return error response with 500 Internal Server Error status
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(null); // or provide an error message
        }
    }

//	@PostMapping("/addBooking")
//	public ResponseEntity<TrainBooking> createTrainBooking(@RequestBody TrainBooking trainBooking) {
//		long randomBookingId = (long) (Math.random() * Long.MAX_VALUE / 1000);
//		trainBooking.setPnr(randomBookingId);
////		trainBookingRepo.save(trainBooking);
//		TrainBooking createdTrain = trainBookingRepo.save(trainBooking);
//		Train trainData = trainClient.getTrainOfBooking(createdTrain.getTrainNo());
////int a=trainData.getSeatsInFirstClassClass()+trainData.getSeatsInSecondClassClass()+trainData.getSeatsInSleeperClass()+trainData.getSeatsInSleeperClass();
//		if (trainBooking.getClassType().equalsIgnoreCase("First")
//				&& trainData.getSeatsInFirstClassClass() > trainBooking.getTotalSeats()) {
//			int b = trainData.getSeatsInFirstClassClass() - trainBooking.getTotalSeats();
//			trainData.setSeatsInFirstClassClass(b);
//			trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getFirstClassPrice());
//			trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
//		} else if (trainBooking.getClassType().equalsIgnoreCase("Second")
//				&& trainData.getSeatsInSecondClassClass() > trainBooking.getTotalSeats()) {
//			int b = trainData.getSeatsInSecondClassClass() - trainBooking.getTotalSeats();
//			trainData.setSeatsInSecondClassClass(b);
//			trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getSecondClassPrice());
//			trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
//		} else if (trainBooking.getClassType().equalsIgnoreCase("Sleep")
//				&& trainData.getSeatsInSleeperClass() > trainBooking.getTotalSeats()) {
//			int b = trainData.getSeatsInSleeperClass() - trainBooking.getTotalSeats();
//			trainData.setSeatsInSleeperClass(b);
//			trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getSleeperCoachPrice());
//			trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
//		} else if (trainBooking.getClassType().equalsIgnoreCase("Third")
//				&& trainData.getSeatsInThirdClassClass() > trainBooking.getTotalSeats()) {
//			int b = trainData.getSeatsInThirdClassClass() - trainBooking.getTotalSeats();
//			trainData.setSeatsInThirdClassClass(b);
//			trainBooking.setPrice(trainBooking.getTotalSeats() * trainData.getThirdClassPrice());
//			trainClient.updateTrain(createdTrain.getTrainNo(), trainData);
//		}
//		trainBookingRepo.save(trainBooking);
//		return new ResponseEntity<>(createdTrain, HttpStatus.CREATED);
//	}

	
	@PostMapping("/addBooking")
    public ResponseEntity<TrainBooking> createTrainBooking(@RequestBody TrainBooking trainBooking) {
        try 
        {
            TrainBooking createdTrainBooking = trainBookingService.createTrainBooking(trainBooking);
            return new ResponseEntity<>(createdTrainBooking, HttpStatus.CREATED);
        } 
        catch (Exception e) 
        {
            logger.error("Error occurred while creating train booking: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	
//	@DeleteMapping("/cancelSeats/{pnr}/{canceledSeatsIndex}")
//	public TrainBooking cancelSeats(@PathVariable long pnr, @PathVariable int canceledSeatsIndex) {
//
//		TrainBooking tb = trainBookingRepo.getTrainBooking(pnr);
//		if (tb.getTotalSeats() > 0) {
//			Train trainData = trainClient.getTrainOfBooking(tb.getTrainNo());
//			if (tb.getClassType().equalsIgnoreCase("First")) {
//				int b = trainData.getSeatsInFirstClassClass() + 1;
//				trainData.setSeatsInFirstClassClass(b);
//				tb.setPrice(tb.getPrice() - trainData.getFirstClassPrice());
//				trainClient.updateTrain(tb.getTrainNo(), trainData);
//				tb.setTotalSeats(tb.getTotalSeats() - 1);
//				tb.getUserInfo().remove(canceledSeatsIndex - 1);
//			} else if (tb.getClassType().equalsIgnoreCase("Second")) {
//				int b = trainData.getSeatsInSecondClassClass() + 1;
//				trainData.setSeatsInSecondClassClass(b);
//				tb.setPrice(tb.getPrice() - trainData.getSecondClassPrice());
//				trainClient.updateTrain(tb.getTrainNo(), trainData);
//				tb.setTotalSeats(tb.getTotalSeats() - 1);
//				tb.getUserInfo().remove(canceledSeatsIndex - 1);
//			} else if (tb.getClassType().equalsIgnoreCase("Third")) {
//				int b = trainData.getSeatsInThirdClassClass() + 1;
//				trainData.setSeatsInThirdClassClass(b);
//				tb.setPrice(tb.getPrice() - trainData.getThirdClassPrice());
//				trainClient.updateTrain(tb.getTrainNo(), trainData);
//				tb.setTotalSeats(tb.getTotalSeats() - 1);
//				tb.getUserInfo().remove(canceledSeatsIndex - 1);
//			} else if (tb.getClassType().equalsIgnoreCase("Sleep")) {
//				int b = trainData.getSeatsInSleeperClass() + 1;
//				trainData.setSeatsInSleeperClass(b);
//				tb.setPrice(tb.getPrice() - trainData.getSleeperCoachPrice());
//				trainClient.updateTrain(tb.getTrainNo(), trainData);
//				tb.setTotalSeats(tb.getTotalSeats() - 1);
//				tb.getUserInfo().remove(canceledSeatsIndex - 1);
//			}
//			trainBookingRepo.save(tb);
//		}
//		if (tb.getTotalSeats()==0) {
//			trainBookingRepo.deleteById(pnr);
//		}
//		return tb;
//
//	}
	
	@DeleteMapping("/cancelSeats/{pnr}/{canceledSeatsIndex}")
    public ResponseEntity<?> cancelSeats(@PathVariable long pnr, @PathVariable int canceledSeatsIndex) {
        try {
            TrainBooking canceledBooking = trainBookingService.cancelSeats(pnr, canceledSeatsIndex);
            return ResponseEntity.ok(canceledBooking);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while canceling seats.");
        }
    }
	
	

	@DeleteMapping("/delete/{pnr}")
	public ResponseEntity<String> deleteTrain(@PathVariable long pnr) {
	    try {
	        if (trainBookingRepo.findById(pnr).isPresent()) 
	        {
	        	trainBookingService.deleteTrainByPnr(pnr);
	        	return ResponseEntity.ok("Train with PNR " + pnr + " deleted successfully");
	        }
	        else 
	        {
		        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Incorrect PNR: " + pnr);
	        }
	    } catch (Exception e) {
	        logger.error("Error deleting train Booking with PNR: {}. Error: {}", pnr, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error deleting train with PNR: " + pnr);
	    }
	}
	
	@GetMapping("/{pnr}")
	public ResponseEntity<TrainBooking> getTrainBooking(@PathVariable long pnr) {
	    try {
	        Optional<TrainBooking> optionalTrainBooking = trainBookingRepo.findById(pnr);
	        if (optionalTrainBooking.isPresent()) 
	        {
	            TrainBooking tb = optionalTrainBooking.get();
	            tb.setTrain(trainClient.getTrainOfBooking(tb.getTrainNo()));
	            return ResponseEntity.ok(tb);
	        } 
	        else {
	        	logger.error("PNR Is Incorrect");
	            return ResponseEntity.notFound().build();
	        }
	    } 
	    catch (Exception e) 
	    { 
	        logger.error("Error occurred while fetching train booking: {}", e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}

	
	@GetMapping("/get/{pnr}")
	public ResponseEntity<TrainBooking> getTrainNo(@PathVariable long pnr) {
	    try {
	        TrainBooking trainBooking = trainBookingService.getByBookingInfoByPNR(pnr);
	        if (trainBooking != null) {
	            return ResponseEntity.ok(trainBooking);
	        } 
	        else 
	        {
	        	 logger.error("Incorrect PNR: {}", pnr);    
	            return ResponseEntity.notFound().build();
	        }
	    } catch (Exception e) {
	        logger.error("Error fetching train booking with PNR: {}. Error: {}", pnr, e.getMessage());
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    }
	}
	
	@GetMapping("/getByName/{userName}")
    public ResponseEntity<List<TrainBooking>> getByName(@PathVariable String userName) {
        try 
        {
            List<TrainBooking> getByNameData = trainBookingService.findBookingByUserName(userName);
            if (getByNameData.isEmpty()) 
            {
            	logger.error("Incorrect userName or {} not Present",userName);
                return ResponseEntity.notFound().build();
            }
            for (TrainBooking trainBooking : getByNameData) 
            {
                trainBooking.setTrain(trainClient.getTrainOfBooking(trainBooking.getTrainNo()));
            }
            return ResponseEntity.ok(getByNameData);
        } 
        catch (Exception e) 
        {
            logger.error("Error occurred while fetching train bookings by user name: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
	
	
}