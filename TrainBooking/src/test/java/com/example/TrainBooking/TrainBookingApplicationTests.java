package com.example.TrainBooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.TrainBooking.controller.TrainBookingController;
import com.example.TrainBooking.entity.Train;
import com.example.TrainBooking.entity.TrainBooking;
import com.example.TrainBooking.repository.TrainBookingRepo;
import com.example.TrainBooking.service.TrainBookingService;
import com.example.TrainBooking.service.TrainClient;

@SpringBootTest
class TrainBookingApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	private TrainBookingRepo trainBookingRepo;
	
	 @Mock
	 private TrainBookingService trainBookingService;

	@InjectMocks
	private TrainBookingController trainBookingController;
	

    @Mock
    private TrainClient trainClient;

    @Test
    public void testCancelSeats_Success() {
        // Mock data
        long pnr = 12345L;
        int canceledSeatsIndex = 2;
        TrainBooking canceledBooking = new TrainBooking();

        // Mock behavior of service method to return canceled booking
        when(trainBookingService.cancelSeats(pnr, canceledSeatsIndex)).thenReturn(canceledBooking);

        // Call the controller method
        ResponseEntity<?> responseEntity = trainBookingController.cancelSeats(pnr, canceledSeatsIndex);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(canceledBooking, responseEntity.getBody());
    }
    
    @Test
    public void testCancelSeats_Exception() {
        // Mock data
        long pnr = 12345L;
        int canceledSeatsIndex = 2;

        // Mock behavior of service method to throw an exception
        when(trainBookingService.cancelSeats(pnr, canceledSeatsIndex)).thenThrow(new RuntimeException());

        // Call the controller method
        ResponseEntity<?> responseEntity = trainBookingController.cancelSeats(pnr, canceledSeatsIndex);

        // Verify the response status and error message
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }
	
    @Test
    public void testGetAllTrainBookings_Success() {
        // Mock data
        List<TrainBooking> trainBookings = new ArrayList<>();
        trainBookings.add(new TrainBooking());
        trainBookings.add(new TrainBooking());

        // Mock behavior of service method to return train bookings
        when(trainBookingService.getAllTrainBookingInfo()).thenReturn(trainBookings);

        // Call the controller method
        ResponseEntity<List<TrainBooking>> responseEntity = trainBookingController.getAllTrainBookings();

        // Verify the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainBookings, responseEntity.getBody());
    }

    @Test
    public void testGetAllTrainBookings_Exception() {
        // Mock behavior of service method to throw an exception
        when(trainBookingService.getAllTrainBookingInfo()).thenThrow(new RuntimeException());

        // Call the controller method
        ResponseEntity<List<TrainBooking>> responseEntity = trainBookingController.getAllTrainBookings();

        // Verify the response status and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    
    @Test
    public void testCreateTrainBooking_Success() {
        // Mock data
        TrainBooking trainBooking = new TrainBooking();
        trainBooking.setPnr(1L);

        // Mock behavior of service method to return the created train booking
        when(trainBookingService.createTrainBooking(trainBooking)).thenReturn(trainBooking);

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.createTrainBooking(trainBooking);

        // Verify the response status and body
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(trainBooking, responseEntity.getBody());
    }

    @Test
    public void testCreateTrainBooking_Exception() {
        // Mock data
        TrainBooking trainBooking = new TrainBooking();

        // Mock behavior of service method to throw an exception
        when(trainBookingService.createTrainBooking(trainBooking)).thenThrow(new RuntimeException());

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.createTrainBooking(trainBooking);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNull(responseEntity.getBody());
    }
    
    @Test
    public void testDeleteTrain_Success() {
        // Mock data
        long pnr = 123456;

        // Mock behavior of train booking repository
        when(trainBookingRepo.findById(pnr)).thenReturn(Optional.of(new TrainBooking()));

        // Call the controller method
        ResponseEntity<String> responseEntity = trainBookingController.deleteTrain(pnr);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Train with PNR " + pnr + " deleted successfully", responseEntity.getBody());
    }
    
    @Test
    public void testDeleteTrain_IncorrectPNR() {
        // Mock data
        long pnr = 123456;
        
        // Mock behavior of train booking repository
        when(trainBookingRepo.findById(pnr)).thenReturn(java.util.Optional.empty());

        // Call the controller method
        ResponseEntity<String> responseEntity = trainBookingController.deleteTrain(pnr);

        // Verify the response status and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Incorrect PNR: " + pnr, responseEntity.getBody());
    }

    @Test
    public void testDeleteTrain_Exception() {
        // Mock data
        long pnr = 123456;
        
        // Mock behavior of train booking repository to throw an exception
        when(trainBookingRepo.findById(pnr)).thenThrow(new RuntimeException());

        // Call the controller method
        ResponseEntity<String> responseEntity = trainBookingController.deleteTrain(pnr);

        // Verify the response status and body
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    

    
    
    @Test
    public void testGetTrainBooking_Success() {
        // Mock data
        long pnr = 12345L;
        TrainBooking trainBooking = new TrainBooking();
        trainBooking.setPnr(pnr);

        // Mock behavior of repository method to return optional containing train booking
        when(trainBookingRepo.findById(pnr)).thenReturn(Optional.of(trainBooking));

        // Mock behavior of train client method to return train information
        Train train = new Train();
        when(trainClient.getTrainOfBooking(trainBooking.getTrainNo())).thenReturn(train);

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.getTrainBooking(pnr);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainBooking, responseEntity.getBody());
    }

    @Test
    public void testGetTrainBooking_NotFound() {
        // Mock data
        long pnr = 12345L;

        // Mock behavior of repository method to return optional empty
        when(trainBookingRepo.findById(pnr)).thenReturn(Optional.empty());

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.getTrainBooking(pnr);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetTrainBooking_Exception() {
        // Mock data
        long pnr = 12345L;

        // Mock behavior of repository method to throw an exception
        when(trainBookingRepo.findById(pnr)).thenThrow(RuntimeException.class);

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.getTrainBooking(pnr);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
    
    @Test
    public void testGetTrainNo_Success() {
        // Mock data
        long pnr = 12345L;
        TrainBooking trainBooking = new TrainBooking();
        trainBooking.setPnr(pnr);

        // Mock behavior of service method to return train booking
        when(trainBookingService.getByBookingInfoByPNR(pnr)).thenReturn(trainBooking);

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.getTrainNo(pnr);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainBooking, responseEntity.getBody());
    }

    @Test
    public void testGetTrainNo_NotFound() {
        // Mock data
        long pnr = 12345L;

        // Mock behavior of service method to return null
        when(trainBookingService.getByBookingInfoByPNR(pnr)).thenReturn(null);

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.getTrainNo(pnr);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetTrainNo_Exception() {
        // Mock data
        long pnr = 12345L;

        // Mock behavior of service method to throw an exception
        when(trainBookingService.getByBookingInfoByPNR(pnr)).thenThrow(RuntimeException.class);

        // Call the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.getTrainNo(pnr);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
    
    @Test
    public void testGetByName_Success() {
        // Mock data
        String userName = "JohnDoe";
        List<TrainBooking> trainBookings = new ArrayList<>();
        trainBookings.add(new TrainBooking());
        trainBookings.add(new TrainBooking());

        // Mock behavior of service method to return train bookings
        when(trainBookingService.findBookingByUserName(userName)).thenReturn(trainBookings);

        // Mock behavior of train client to return train info
        when(trainClient.getTrainOfBooking(any())).thenReturn(new Train());

        // Call the controller method
        ResponseEntity<List<TrainBooking>> responseEntity = trainBookingController.getByName(userName);

        // Verify the response status and body
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(trainBookings, responseEntity.getBody());
    }

    @Test
    public void testGetByName_NotFound() {
        // Mock data
        String userName = "JohnDoe";

        // Mock behavior of service method to return empty list
        when(trainBookingService.findBookingByUserName(userName)).thenReturn(new ArrayList<>());

        // Call the controller method
        ResponseEntity<List<TrainBooking>> responseEntity = trainBookingController.getByName(userName);

        // Verify the response status
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void testGetByName_Exception() {
        // Mock data
        String userName = "JohnDoe";

        // Mock behavior of service method to throw an exception
        when(trainBookingService.findBookingByUserName(userName)).thenThrow(RuntimeException.class);

        // Call the controller method
        ResponseEntity<List<TrainBooking>> responseEntity = trainBookingController.getByName(userName);

        // Verify the response status
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
    }
}
