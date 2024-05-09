package com.moustafa.LibraryApi.person;

import java.util.Set;

import com.moustafa.LibraryApi.book.Book;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(
		name="person",
		uniqueConstraints = {
				@UniqueConstraint(name="person_email_unique", columnNames="email")
		})
public class Person {
	@Id
	@SequenceGenerator(
			name = "person_sequence",
			sequenceName = "person_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "person_sequence")
	@Column(
			name = "id",
			updatable = false)
	private Long id;
	@Column(
			name = "name",
			nullable = false,
			columnDefinition = "TEXT")
	private String name;
	@Column(
			name = "password",
			nullable = false,
			columnDefinition = "TEXT")
	private String password;
	@Column(
			name = "balance",
			scale = 2)
	private float balance;
	@Column(
			name = "email",
			nullable = false,
			columnDefinition = "TEXT")
	private String email; 
	@Enumerated(EnumType.STRING)
	private Role role;
	@OneToMany(mappedBy="person")
	private Set<Book> books;
	
	
	//Constructors
	public Person() {
		
	}
	
	public Person(Long id, String name, String email, String password, Role role) {
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.balance = 0.0f;
		this.role = role;
	}
	
	public Person(String name, String email, String password, Role role) {
		this.name = name;
		this.password = password;
		this.email = email;
		this.balance = 0.0f;
		this.role = role;
	}
	
	public Person(String email, String password, Role role) {
		this.email = email;
		this.password = password;
		this.balance = 0.0f;
		this.role = role;
	}
	
	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public float getBalance() {
		return this.balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	
	public Role getRole() {
		return this.role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	@Override
	public String toString() {
		return "Id: " + id + ", Name: " + name + ", Role: " + role + " , Email: " + email + ", Password: " + password + ", Balance: " + balance;
	}
}
