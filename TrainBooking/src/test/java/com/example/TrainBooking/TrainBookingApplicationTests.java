package com.example.TrainBooking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

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
import com.example.TrainBooking.service.TrainClient;

@SpringBootTest
class TrainBookingApplicationTests {

	@Test
	void contextLoads() {
	}
	@Mock
	private TrainBookingRepo trainBookingRepo;

	@InjectMocks
	private TrainBookingController trainBookingController;
	
	@Test
    void testGetAllTrains() {
       
        // Sample train bookings
        List<TrainBooking> trainBookings = Arrays.asList();
        when(trainBookingRepo.findAll()).thenReturn(trainBookings);

        // Invoke the controller method
        ResponseEntity<List<TrainBooking>> responseEntity = trainBookingController.getAllTrains();

        // Verify that trainBookingRepo.findAll() was called
        verify(trainBookingRepo).findAll();

        // Verify the ResponseEntity content and status code
        assertEquals(trainBookings, responseEntity.getBody());
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
	
	@Test
    void testCreateTrain() {
        // Sample train booking
        TrainBooking trainBooking = new TrainBooking(1,2,"123",4,"FirstClass",null);
        when(trainBookingRepo.save(trainBooking)).thenReturn(trainBooking);

        // Invoke the controller method
        ResponseEntity<TrainBooking> responseEntity = trainBookingController.createTrainBooking(trainBooking);

        // Verify that trainBookingRepo.save() was called
        verify(trainBookingRepo).save(trainBooking);

        // Verify the ResponseEntity content and status code
        assertEquals(trainBooking, responseEntity.getBody());
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }
	
	@Test
    void testGetTrainBooking() {
      
        // Sample PNR
        long pnr = 123;

        TrainClient trainClient = mock(TrainClient.class);
       
        TrainBooking mockTrainBooking = new TrainBooking();
//        mockTrainBooking.setPnr(pnr);
        mockTrainBooking.setTrainNo("789");  // Assuming a train number

        // Mock the behavior of trainBookingRepo.getTrainBooking
        when(trainBookingRepo.getTrainBooking(pnr)).thenReturn(mockTrainBooking);

        // Mock the behavior of trainClient.getTrainOfBooking
        when(trainClient.getTrainOfBooking(mockTrainBooking.getTrainNo())).thenReturn(new Train());

        // Call the method
        TrainBooking result = trainBookingController.getTrainBooking(pnr);

        // Verify the results
        assertEquals(mockTrainBooking, result);

        // Verify that the setTrain method was called with the correct Train object
        Mockito.verify(trainClient).getTrainOfBooking(mockTrainBooking.getTrainNo());
    }
	
	@Test
    public void testGetTrainNo() {
        // Mock data
        long pnr = 123456;
        TrainBooking mockTrainBooking = new TrainBooking();
//        mockTrainBooking.setPnr(pnr);
        mockTrainBooking.setTrainNo("789");  // Assuming a train number

        // Mock the behavior of trainBookingRepo.getTrainBooking
        when(trainBookingRepo.getTrainBooking(pnr)).thenReturn(mockTrainBooking);

        // Call the method
        TrainBooking result = trainBookingController.getTrainNo(pnr);

        // Verify the results
        assertEquals(mockTrainBooking, result);
    }

}
