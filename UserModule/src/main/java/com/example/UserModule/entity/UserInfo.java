package com.example.UserModule.entity;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Document(collection = "User")
public class UserInfo {
	@NotNull
	@Size(min = 8, message = "Password must be at least 8 characters long")
	private String password;

	@NotNull
	@Size(min = 5, message = "Username must be at least 5 characters long")
	@Indexed(unique = true)
	@Id
	private String userName;

	@NotNull
	@Size(min = 2, message = "First name must be at least 2 characters long")
	private String firstName;

	private String middleName;

	@NotNull
	@Size(min = 2, message = "Last name must be at least 2 characters long")
	private String lastName;

	@NotNull
	@Past(message = "Date of Birth must be in the past")
	private Date dateOfBirth;

	@NotNull
	private String gender;

	@NotNull
	@Size(min = 10, max = 10, message = "Mobile number must be 10 digits long")
	@Pattern(regexp = "[0-9]+", message = "Mobile number must contain only digits")
	@Indexed(unique = true)
	private String mobileNumber;

	@NotNull
	@Email(message = "Invalid email address")
	@Indexed(unique = true)
	private String email;

	@NotNull
	private String nationality;

	@NotNull
	@Pattern(regexp = "^(user|admin)$", message = "Role must be 'user' or 'admin'")
	private String role;

	public UserInfo(@NotNull @Size(min = 8, message = "Password must be at least 8 characters long") String password,
			@NotNull @Size(min = 5, message = "Username must be at least 5 characters long") String userName,
			@NotNull @Size(min = 2, message = "First name must be at least 2 characters long") String firstName,
			String middleName,
			@NotNull @Size(min = 2, message = "Last name must be at least 2 characters long") String lastName,
			@NotNull @Past(message = "Date of Birth must be in the past") Date dateOfBirth, @NotNull String gender,
			@NotNull @Size(min = 10, max = 10, message = "Mobile number must be 10 digits long") @Pattern(regexp = "[0-9]+", message = "Mobile number must contain only digits") String mobileNumber,
			@NotNull @Email(message = "Invalid email address") String email, @NotNull String nationality,
			@NotNull @Pattern(regexp = "^(user|admin)$", message = "Role must be 'user' or 'admin'") String role) {
		super();

		this.password = password;
		this.userName = userName;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.gender = gender;
		this.mobileNumber = mobileNumber;
		this.email = email;
		this.nationality = nationality;
		this.role = role;
	}

	public UserInfo() {
		super();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
