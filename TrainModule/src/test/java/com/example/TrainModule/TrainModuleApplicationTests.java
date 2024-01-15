package com.example.TrainModule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.TrainModule.controller.TrainController;
import com.example.TrainModule.entity.Train;
import com.example.TrainModule.exception.TrainNotFoundException;
import com.example.TrainModule.repository.TrainInterface;

@SpringBootTest
class TrainModuleApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private TrainInterface trainInterface;

	@InjectMocks
	private TrainController trainController;

	@Test
	public void getAllTrains_ReturnsListOfTrains() {
		
		
		// Mocking repository behavior
		Train train1 = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40);

		Train train2 = new Train("234", "ExpressA", "StationX", "StationY",
				"11:30", "12:30",
                new Date(), 10, 50, 20, 30, 40);

//        SupplierInventoryEntity inventory2 = new SupplierInventoryEntity("2", "Item2", "Supplier2", 20, 0, null);
		Mockito.when(trainInterface.findAll()).thenReturn((List<Train>) Arrays.asList(train1));

//        // Calling the service method
		ResponseEntity<List<Train>> response = trainController.getAllTrains();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mockTrains, response.getBody());
	}

	@Test
	public void createTrain_ValidTrain_ReturnsCreatedTrain() {

		// Arrange
		Train mockTrain = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40);


		when(trainInterface.save(any(Train.class))).thenReturn(mockTrain);
		// Act
		ResponseEntity<Train> response = trainController.createTrain(mockTrain);
		// Assert
		verify(trainInterface, times(1)).save(any(Train.class));
		// Ensure save method is called once
		assertEquals(HttpStatus.CREATED, response.getStatusCode());
		// Verify the response status code
		assertEquals(mockTrain, response.getBody());
		// Verify the response body
	}

	@Test
	public void getTrainOfBooking_ExistingTrain_ReturnsTrain() {
		// Arrange
		String trainNo = "123";
		Train existingTrain = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40);

		
		when(trainInterface.getTrainOfBooking(trainNo)).thenReturn(existingTrain);

		// Act
		Train result = trainController.getTrainOfBooking(trainNo);

		// Assert
		assertEquals(trainNo, result.getTrainNo());
		
	}

//    @Test
//    public void getTrainOfBooking_NonExistingTrain_ThrowsNotFoundException() {
//        // Arrange
//        String trainNo = "456";
//        when(trainController.getTrainOfBooking(trainNo)).thenReturn(null);
//
//        // Act and Assert
//        assertThrows(TrainNotFoundException.class,() -> trainInterface.getTrainOfBooking(trainNo));
//    }

	@Test
	void testSearchTrains() {

		// Sample input values
		String sourceStation = "Source";
		String destinationStation = "Destination";
		Date date = new Date();

		// Sample list of trains to be returned by the mocked trainInterface
		List<Train> expectedTrains = Arrays.asList(new Train(), new Train());

		// Stub the behavior of trainInterface.findBys() to return the sample list of
		// trains
		when(trainInterface.findBys(sourceStation, destinationStation, date)).thenReturn(expectedTrains);

		// Invoke the controller method
		List<Train> result = trainController.searchTrains(sourceStation, destinationStation, date);

		// Verify that trainInterface.findBys() was called with the correct arguments
		verify(trainInterface).findBys(sourceStation, destinationStation, date);

		// Verify the result of the controller method
		assertEquals(expectedTrains, result);
	}

	@Test
	void testUpdateTrain() {
		// Sample train number and updated train details
		String trainNo = "123";
		Train train1 = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40);

		Train train2 = new Train("123", "Express", "StationA", "StationB",
                "11:30", "12:30",
                new Date(), 10, 50, 20, 30, 40);;

	

		// Mocking repository behavior
		 Mockito.when(trainInterface.findById("123")).thenReturn(Optional.of(train1));
	        Mockito.when(trainInterface.save(any(Train.class))).thenReturn(train2);

	        // Calling the controller method
	        Train result = trainController.updateTrain("123", train2);

	        // Assertions
	        assertEquals("Express", result.getTrainName());
	        assertEquals("StationA", result.getSourceStation());
	        assertEquals("StationB", result.getDestinationStation());
	        assertEquals("11:30", result.getDepartureTimes());
	        assertEquals("12:30", result.getArrivalTimes());
	        assertEquals(10, result.getTotalCoach());
	        assertEquals(50, result.getSeatsInSleeperClass());
	        assertEquals(20, result.getSeatsInFirstClassClass());
	        assertEquals(30, result.getSeatsInSecondClassClass());
	        assertEquals(40, result.getSeatsInThirdClassClass());
	}
	
	@Test
    void testDeleteTrain() {
        // Sample train number
        String trainNo = "123";

        // Mock the behavior of trainInterface.findById() to return an Optional with an existing Train
        Train existingTrain =new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40);

		
        when(trainInterface.findById(trainNo)).thenReturn(Optional.of(existingTrain));

        // Invoke the controller method
        ResponseEntity<Void> responseEntity = trainController.deleteTrain(trainNo);

        // Verify that trainInterface.findById() was called with the correct trainNo
        verify(trainInterface).findById(trainNo);

        // Verify that trainInterface.deleteById() was called with the correct trainNo
        verify(trainInterface).deleteById(trainNo);

        // Verify the ResponseEntity status code
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
    
        
    }

//    @Test
//    void testDeleteTrainNotFound() {
//      
//    	// Sample train number
//        String trainNo = "123";
//
//        // Mock the behavior of trainInterface.findById() to return an empty Optional
//        when(trainInterface.findById(trainNo)).thenReturn(Optional.empty());
//
//        // Invoke the controller method
//        ResponseEntity<Void> responseEntity = trainController.deleteTrain(trainNo);
//
//        // Verify that trainInterface.findById() was called with the correct trainNo
//        verify(trainInterface).findById(trainNo);
//
//        // Verify that trainInterface.deleteById() was not called
//        verify(trainInterface, never()).deleteById(trainNo);
//
//        // Verify the ResponseEntity status code
//        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
//    }

//    @Test
//    void testAddSeatsForTrain() {
//      
//    	String trainNo = "123";
//        int additionalSeats = 10;
//        Train mockTrain = new Train();
//        mockTrain.setTrainNo(trainNo);
//        mockTrain.setSeatInEachCoach(20); // Assuming initial seats in each coach is 20
//
//        // Mock the behavior of trainInterface.findById
//        when(trainInterface.findById(trainNo)).thenReturn(Optional.of(mockTrain));
//
//        // Call the method
//        ResponseEntity<String> responseEntity = trainController.addSeatsForTrain(trainNo, additionalSeats);
//
//        // Verify the results
//        assertEquals("Seats added successfully.", responseEntity.getBody());
//        assertEquals(30, mockTrain.getSeatInEachCoach()); // Assuming the initial seats + additionalSeats
//
//        // Verify that the save method was called with the updated Train object
//        Mockito.verify(trainInterface).save(any(Train.class));
//    }
    
    @Test
    void testGetSeatsByID() {
      
        // Sample train number
        String trainNo = "123";

        // Mock the behavior of trainInterface.findById() to return an existing Train
        Train existingTrain = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40);

        
        when(trainInterface.findById(trainNo)).thenReturn(Optional.of(existingTrain));

        // Invoke the controller method
        ResponseEntity<String> responseEntity = trainController.getSeatsByID(trainNo);

        // Verify that trainInterface.findById() was called with the correct trainNo
        verify(trainInterface).findById(trainNo);

        // Verify the ResponseEntity content and status code
        int a=existingTrain.getSeatsInFirstClassClass()+existingTrain.getSeatsInSecondClassClass()+existingTrain.getSeatsInSleeperClass()+existingTrain.getSeatsInThirdClassClass();
        assertEquals("Total Seats are :" + a, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
    
    
    
}
