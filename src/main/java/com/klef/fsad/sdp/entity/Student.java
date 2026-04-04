package com.klef.fsad.sdp.entity;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "student_table")
public class Student 
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, length = 50, unique = true)
	private String username;
	@Column(nullable = false, length = 50, unique = false)
	private String password;
	@Column(nullable = false, length = 50, unique = true)
	private String email;
	@Column(nullable = false, length = 50, unique = false)
	private String firstName;
	@Column(nullable = false, length = 50, unique = false)
	private String lastName;
	@Column(nullable = false,length = 20,unique = true)
	private String contact;
	@Column(nullable = false,length = 50)
    private String gender;
	@Column(nullable = false,length = 100)
	private String location;
	@CreationTimestamp
	@Column(updatable = false) // Prevents updating this value later
	private LocalDateTime registeredAt;

	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDateTime getRegisteredAt() {
		return registeredAt;
	}
	public void setRegisteredAt(LocalDateTime registeredAt) {
		this.registeredAt = registeredAt;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", firstName=" + firstName + ", lastName=" + lastName + ", contact=" + contact + ", gender=" + gender
				+ ", location=" + location + ", registeredAt=" + registeredAt + "]";
	}

}



/*

id: Long (Primary Key)

username: String (Unique)

password: String (Encrypted)

email: String (Unique)

firstName / lastName: String

role: Enum (ADMIN, INSTRUCTOR, STUDENT)

createdAt: LocalDateTime

*/

