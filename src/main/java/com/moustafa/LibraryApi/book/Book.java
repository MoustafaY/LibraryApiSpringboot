package com.moustafa.LibraryApi.book;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import org.hibernate.annotations.Check;
import com.moustafa.LibraryApi.person.Person;



@Entity
@Table(name="book")
public class Book {

	@Id
	@SequenceGenerator(
			name = "book_sequence",
			sequenceName = "book_sequence",
			allocationSize = 1)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "book_sequence")
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
			name = "category",
			nullable = false,
			columnDefinition = "TEXT")
	private String category;
	@Column(
			name = "author",
			nullable = false,
			columnDefinition = "TEXT")
	private String author;
	@Column(
			name = "daysRented",
			nullable = false)
	private int daysRented;
	@ManyToOne
	@JoinColumn(name = "person_id")
	private Person person;


	
	//Constructors
	public Book() {
		
	}
	
	public Book(Long id, String name, String category, String author) {
		this.id = id;
		this.name = name;
		this.category = category;
		this.author = author;
		this.daysRented = 0;
		this.person = null;
	}
	
	public Book(String name, String category, String author) {
		this.name = name;
		this.category = category;
		this.author = author;
		this.daysRented = 0;
		this.person = null;
	}
	
	
	//Getters and Setters
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}
	
	public String getAuthor() {
		return author;
	}
	
	public void setAuthor(String author) {
		this.author = author;
	}
	
	public int getDaysRented() {
		return daysRented;
	}
	
	public void setDaysRented(int daysRented) {
		this.daysRented = daysRented;
	}
	
	public Person getPerson() {
		return this.person;
	}
	
	public void setPerson(Person person) {
		this.person = person;
	}
	
	
	
	@Override
	public String toString() {
		String personName = "";
		if(person != null) {
			personName = ", Renter: " + person.getName();
		}
		return "Id: " + id + ", Name: " + name + ", Category: " + category + ", Author: " + author + ", Days Rented: " + daysRented + personName;
	}
	
	
}
