package com.example.TrainModule.exception;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(org.springframework.http.HttpStatus.NOT_FOUND)
public class TrainNotFoundException extends RuntimeException {
    public TrainNotFoundException(String trainNo) {
        super("Train not found with id: " + trainNo);
 }
    
}