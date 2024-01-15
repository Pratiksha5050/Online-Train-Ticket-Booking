package com.example.UserModule.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.UserModule.entity.AuthRequest;
import com.example.UserModule.entity.UserInfo;
import com.example.UserModule.repository.UserRepository;
import com.example.UserModule.service.JwtService;
import com.example.UserModule.service.UserServiceImpl;

@RestController
@EnableAutoConfiguration
@RequestMapping("/auth")
public class UserController 
{
	
	@Autowired
    private UserServiceImpl userService;

	@Autowired
    private AuthenticationManager authenticationManager;
	
    @Autowired
    private JwtService jwtService;
    
	@GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Spring Security ";
    }
	
    @PostMapping("/addUser")
    public ResponseEntity<String> addUser(@RequestBody UserInfo user) 
    {
        userService.addUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }
    
//    @PutMapping("/{userName}")
//    public ResponseEntity<String> updateUser(@PathVariable String userName,@RequestBody UserInfo updatedUser) 
//    {
//        userService.updateUser(userName, updatedUser);
//        return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
//    }
    
    @DeleteMapping("/{userName}")
    public ResponseEntity<String> deleteUser(@PathVariable String userName) {
        userService.deleteUser(userName);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.NO_CONTENT);
    }
    
    @PostMapping("/login")
    public String addUser(@RequestBody AuthRequest authRequest){
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(), authRequest.getPassword()));
        if(authenticate.isAuthenticated()){
            return jwtService.generateToken(authRequest.getUserName());
        }else {
            throw new UsernameNotFoundException("Invalid user request");
        }
    }
    @GetMapping("/getUsers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<UserInfo> getAllUsers(){
        return userService.getAllUser();
    }
    @GetMapping("/getUsers/{UserName}")
    @PreAuthorize("hasAuthority('USER')")
    public UserInfo getAllUsers(@PathVariable String UserName){
        return userService.getUser(UserName);
    }
}
