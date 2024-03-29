package com.example.UserModule.service;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.UserModule.entity.UserInfo;

public class UserInfoDetails implements UserDetails{

	String userName=null;
	String password=null;
	List<GrantedAuthority> authorities;
	public UserInfoDetails(UserInfo userInfo) {
		userName=userInfo.getUserName();
		password=userInfo.getPassword();
		authorities= Arrays.stream(userInfo.getRole().split(","))
	               .map(SimpleGrantedAuthority::new)
	               .collect(Collectors.toList());
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return userName;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
