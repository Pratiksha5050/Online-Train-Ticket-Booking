package com.example.UserModule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.UserModule.controller.UserController;
import com.example.UserModule.entity.UserInfo;
import com.example.UserModule.service.UserServiceImpl;

@SpringBootTest
class UserModuleApplicationTests {

	@Test
	void contextLoads() {
	}
	@Test
    public void testAddUser() {
        // Mocking the UserService
        UserServiceImpl userServiceMock = mock(UserServiceImpl.class);

        // Creating an instance of YourController and injecting the mocked UserService
//        UserController yourController = new UserController(userServiceMock);

        // Creating a sample UserInfo object
        UserInfo user = new UserInfo();
        // Set properties of user as needed

        // Calling the addUser method
//        ResponseEntity<String> response = yourController.addUser(user);

        // Verifying that the addUser method of UserService is called with the correct argument
        verify(userServiceMock, times(1)).addUser(user);

        // Asserting the response
//        assertEquals("User registered successfully", response.getBody());
//        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
}
