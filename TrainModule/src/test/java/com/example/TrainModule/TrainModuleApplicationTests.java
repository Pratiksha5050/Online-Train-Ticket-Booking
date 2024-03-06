package com.example.TrainModule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
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
import com.example.TrainModule.service.TrainService;

@SpringBootTest
class TrainModuleApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	private TrainInterface trainInterface;

	@InjectMocks
	private TrainController trainController;

	@Mock
	private TrainService trainService;
	
	@Test
	public void getAllTrains_ReturnsListOfTrains() {
		
		
		// Mocking repository behavior
		Train train1 = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

		Train train2 = new Train("234", "ExpressA", "StationX", "StationY",
				"11:30", "12:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

//        SupplierInventoryEntity inventory2 = new SupplierInventoryEntity("2", "Item2", "Supplier2", 20, 0, null);
		Mockito.when(trainService.getAllTrains()).thenReturn((List<Train>) Arrays.asList(train1));

//        // Calling the service method
		ResponseEntity<List<Train>> response = trainController.getAllTrains();

		// Assert
		assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertEquals(mockTrains, response.getBody());
	}

	@Test
    public void testGetAllTrains_Exception() {
        // Mock the trainService to throw an exception
        when(trainService.getAllTrains()).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<List<Train>> response = trainController.getAllTrains();

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
	
	@Test
    public void testGetTrainOfBooking_Success() {
        // Mock data
        String trainNo = "12345";
        Train train = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

        // Mock behavior of trainService to return the train
        when(trainService.getTrainByIds(trainNo)).thenReturn(train);

        // Call the controller method
        ResponseEntity<Train> response = trainController.getTrainOfBooking(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the returned train
        assertEquals(train, response.getBody());
    }
	
	@Test
    public void testGetTrainOfBooking_NotFound() {
        // Mock data
        String trainNo = "12345";

        // Mock behavior of trainService to return null (train not found)
        when(trainService.getTrainByIds(trainNo)).thenReturn(null);

        // Call the controller method
        ResponseEntity<Train> response = trainController.getTrainOfBooking(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

	@Test
    public void testGetTrainOfBooking_InternalServerError() {
        // Mock data
        String trainNo = "12345";

        // Mock behavior of trainService to throw an exception
        when(trainService.getTrainByIds(trainNo)).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<Train> response = trainController.getTrainOfBooking(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
	
	@Test
    public void testGetTrainOfBooking_TrainNotFoundException() {
        // Mock data
        String trainNo = "123";
        doThrow(new TrainNotFoundException(trainNo)).when(trainService).getTrainByIds(trainNo);

        // Call the controller method
        ResponseEntity<Train> response = trainController.getTrainOfBooking(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    } 
	
	@Test
    public void testSearchTrains_Success() {
        // Mock data
        String sourceStation = "Source";
        String destinationStation = "Destination";
        Date date = new Date();
        List<Train> trains = new ArrayList<>();
        trains.add(new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300));

        // Mock behavior of trainService to return list of trains
        when(trainService.searchTrain(sourceStation, destinationStation, date)).thenReturn(trains);

        // Call the controller method
        ResponseEntity<List<Train>> response = trainController.searchTrains(sourceStation, destinationStation, date);

        // Verify the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the returned list of trains
        assertEquals(trains, response.getBody());
    }

    @Test
    public void testSearchTrains_NoTrainsFound() {
        // Mock data
        String sourceStation = "Source";
        String destinationStation = "Destination";
        Date date = new Date();

        // Mock behavior of trainService to return empty list of trains
        when(trainService.searchTrain(sourceStation, destinationStation, date)).thenReturn(new ArrayList<>());

        // Call the controller method
        ResponseEntity<List<Train>> response = trainController.searchTrains(sourceStation, destinationStation, date);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testSearchTrains_InternalServerError() {
        // Mock data
        String sourceStation = "Source";
        String destinationStation = "Destination";
        Date date = new Date();

        // Mock behavior of trainService to throw an exception
        when(trainService.searchTrain(sourceStation, destinationStation, date)).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<List<Train>> response = trainController.searchTrains(sourceStation, destinationStation, date);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testCreateTrain_Success() {
        // Mock data
        Train train = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

        // Mock behavior of trainInterface to return false (train doesn't exist)
        when(trainInterface.existsById(train.getTrainNo())).thenReturn(false);

        // Mock behavior of trainService to return created train
        when(trainService.createTrain(train)).thenReturn(train);

        // Call the controller method
        ResponseEntity<String> response = trainController.createTrain(train);

        // Verify the response status
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // Verify the response body
        assertEquals("Train added successfully with trainNo: 123", response.getBody());
    }

    @Test
    public void testCreateTrain_Conflict() {
        // Mock data
        Train train = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

        // Mock behavior of trainInterface to return true (train already exists)
        when(trainInterface.existsById(train.getTrainNo())).thenReturn(true);

        // Call the controller method
        ResponseEntity<String> response = trainController.createTrain(train);

        // Verify the response status
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());

        // Verify the response body
        assertEquals("Train already exists with trainNo: 123", response.getBody());
    }

    @Test
    public void testCreateTrain_InternalServerError() {
        // Mock data
        Train train = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

        // Mock behavior of trainInterface to throw an exception
        when(trainInterface.existsById(train.getTrainNo())).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<String> response = trainController.createTrain(train);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verify the response body
        assertEquals("An error occurred while processing the request.", response.getBody());
    }
	
	
	
    @Test
    public void testUpdateTrain_Success() {
        // Mock data
        String trainNo = "123";
        Train updatedTrain =new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

        // Mock behavior of trainService to return updated train
        when(trainService.updateTrain(trainNo, updatedTrain)).thenReturn(updatedTrain);

        // Call the controller method
        ResponseEntity<?> response = trainController.updateTrain(trainNo, updatedTrain);

        // Verify the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the response body
        assertEquals(updatedTrain, response.getBody());
    }

    @Test
    public void testUpdateTrain_InternalServerError() {
        // Mock data
        String trainNo = "123";
        Train updatedTrain = new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300);

        // Mock behavior of trainService to throw an exception
        when(trainService.updateTrain(trainNo, updatedTrain)).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<?> response = trainController.updateTrain(trainNo, updatedTrain);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        // Verify the response body
        assertEquals("An error occurred while processing the request.", response.getBody());
    }
    
    @Test
    public void testDeleteTrain_Success() {
        // Mock data
        String trainNo = "123";
        Optional<Train> existingTrain = Optional.of(new Train("123", "Express", "StationA", "StationB",
                "8:30", "9:30",
                new Date(), 10, 50, 20, 30, 40,200.5,150.2,100,300));

        // Mock behavior of trainInterface to return existing train
        when(trainInterface.findById(trainNo)).thenReturn(existingTrain);

        // Call the controller method
        ResponseEntity<Void> response = trainController.deleteTrain(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testDeleteTrain_NotFound() {
        // Mock data
        String trainNo = "123";
        Optional<Train> existingTrain = Optional.empty();

        // Mock behavior of trainInterface to return empty optional
        when(trainInterface.findById(trainNo)).thenReturn(existingTrain);

        // Call the controller method
        ResponseEntity<Void> response = trainController.deleteTrain(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testDeleteTrain_InternalServerError() {
        // Mock data
        String trainNo = "123";

        // Mock behavior of trainInterface to throw an exception
        when(trainInterface.findById(trainNo)).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<Void> response = trainController.deleteTrain(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testGetSeatsByID_Success() {
        // Mock data
        String trainNo = "123";
        Train train = new Train();
        train.setSeatsInFirstClassClass(10);
        train.setSeatsInSecondClassClass(20);
        train.setSeatsInSleeperClass(30);
        train.setSeatsInThirdClassClass(40);
        when(trainInterface.findById(trainNo)).thenReturn(Optional.of(train));

        // Call the controller method
        ResponseEntity<String> response = trainController.getSeatsByID(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the returned total seats message
        assertTrue(response.getBody().contains("Total Seats are: "));
    }

    @Test
    public void testGetSeatsByID_TrainNotFoundException() {
        // Mock data
        String trainNo = "123";
        when(trainInterface.findById(trainNo)).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<String> response = trainController.getSeatsByID(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetSeatsByID_InternalServerError() {
        // Mock behavior of trainInterface to throw an exception
        String trainNo = "123";
        when(trainInterface.findById(trainNo)).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<String> response = trainController.getSeatsByID(trainNo);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testGetUniqueSourceStations_Success() {
        // Mock data
        List<String> sourceStations = List.of("Station A", "Station B", "Station C");
        
        // Mock behavior of trainService to return source stations
        when(trainService.getUniqueSourceStations()).thenReturn(sourceStations);

        // Call the controller method
        ResponseEntity<List<String>> response = trainController.getUniqueSourceStations();

        // Verify the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the returned source stations
        assertEquals(sourceStations, response.getBody());
    }

    @Test
    public void testGetUniqueSourceStations_InternalServerError() {
        // Mock behavior of trainService to throw an exception
        when(trainService.getUniqueSourceStations()).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<List<String>> response = trainController.getUniqueSourceStations();

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
    
    @Test
    public void testGetUniqueDestinationStations_Success() {
        // Mock data
        List<String> destinationStations = List.of("Station X", "Station Y", "Station Z");
        
        // Mock behavior of trainService to return destination stations
        when(trainService.getUniqueDestinationStations()).thenReturn(destinationStations);

        // Call the controller method
        ResponseEntity<List<String>> response = trainController.getUniqueDestinationStations();

        // Verify the response status
        assertEquals(HttpStatus.OK, response.getStatusCode());

        // Verify the returned destination stations
        assertEquals(destinationStations, response.getBody());
    }

    @Test
    public void testGetUniqueDestinationStations_InternalServerError() {
        // Mock behavior of trainService to throw an exception
        when(trainService.getUniqueDestinationStations()).thenThrow(new RuntimeException("Internal Server Error"));

        // Call the controller method
        ResponseEntity<List<String>> response = trainController.getUniqueDestinationStations();

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

}
