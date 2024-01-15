package com.example.UserModule.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.UserModule.entity.UserInfo;
import com.example.UserModule.exception.UserNotFoundException;
import com.example.UserModule.repository.UserRepository;

@Service
public class UserServiceImpl  implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserInfo addUser(UserInfo user) 
	{
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	public void deleteUser(String userName) 
	{
		 userRepository.deleteById(userName);;
	}

	 public List<UserInfo> getAllUser(){
         return userRepository.findAll();
    }
	
	public UserInfo updateUser(String userName, UserInfo updatedUser) {
		UserInfo existingUser = userRepository.findById(userName).orElseThrow(() -> new UserNotFoundException(userName));
		
		existingUser.setFirstName(updatedUser.getFirstName());
		existingUser.setLastName(updatedUser.getLastName());
		existingUser.setMiddleName(updatedUser.getMiddleName());
		existingUser.setEmail(updatedUser.getEmail());
		existingUser.setGender(updatedUser.getGender());
		existingUser.setDateOfBirth(updatedUser.getDateOfBirth());
		existingUser.setMobileNumber(updatedUser.getMobileNumber());
		existingUser.setPassword(updatedUser.getPassword());
		existingUser.setNationality(updatedUser.getNationality());
		
		userRepository.save(existingUser);
		return existingUser;
	}

//	@Override
//    public UserDetails loadUserByUserName(String userName) throws UserNotFoundException {
//        Optional<UserInfo> userInfo = userRepository.findByName(userName);
//        return userInfo.map(UserInfoDetails::new)
//                .orElseThrow(()-> new UserNotFoundException("User not found"+userName));
//    }

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserInfo> userInfo = userRepository.findByName(username);
        return userInfo.map(UserInfoDetails::new)
                .orElseThrow(()-> new UserNotFoundException("User not found"+username));
	}

	public UserInfo getUser(String UserName){
        return userRepository.findById(UserName).get();
    }

	

	
}
