package com.moustafa.LibraryApi.library;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moustafa.LibraryApi.book.*;
import com.moustafa.LibraryApi.person.*;

@Service
public class LibraryService {

	private final PersonRepository personRepository;
	private final BookRepository bookRepository;
	
	@Autowired
	public LibraryService(PersonRepository personRepository, BookRepository bookRepository) {
		this.personRepository = personRepository;
		this.bookRepository = bookRepository;
	}
	
	
	//Authentication
	public Long login(Person person) {
		Optional<Person> personOptional = personRepository.findPersonByEmail(person.getEmail());
		if(!personOptional.isPresent()) {
			throw new IllegalStateException("Email does not exist");
		}
		if(!personOptional.get().getPassword().equals(person.getPassword())) {
			throw new IllegalStateException("Incorrect password");
		}
		return personOptional.get().getId();
	}
	
	
	//Student
	public List<Person> getPersons() {
		return personRepository.findAll();
	}
	public Long addNewPerson(Person person) {
		Optional<Person> personOptional = personRepository.findPersonByEmail(person.getEmail());
		if(personOptional.isPresent()) {
			throw new IllegalStateException("Email already exists");
		}
		personRepository.save(person);
		return person.getId();
	}
	public void deletePerson(Long personId) {
		boolean exists = personRepository.existsById(personId);
		if(!exists) {
			throw new IllegalStateException("User does not exist");
		}
		personRepository.deleteById(personId);
	}
	
	public List<Book> getPersonBooks(Long personId){
		Optional<Person> personOptional = personRepository.findById(personId);
		if(!personOptional.isPresent()) {
			throw new IllegalStateException("User does not exist");
		}
		
		Person person = personOptional.get();
		
		return personRepository.getBooks(person);
	}
	
	public String personRentBook(Long personId, Long bookId) {		
		Optional<Person> personOptional = personRepository.findById(personId);
		if(!personOptional.isPresent()) {
			throw new IllegalStateException("User does not exist");
		}
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if(!bookOptional.isPresent()) {
			throw new IllegalStateException("Book does not exist");
		}
		
		Person person = personOptional.get();
		Book book = bookOptional.get();
		
		if(book.getPerson() != null) {
			throw new IllegalStateException("Book is already owned");
		}
		
		book.setPerson(person);
		bookRepository.save(book);
		return book.toString();
		
	}
	
	public String personReturnBook(Long personId, Long bookId) {		
		Optional<Person> personOptional = personRepository.findById(personId);
		if(!personOptional.isPresent()) {
			throw new IllegalStateException("User does not exist");
		}
		Optional<Book> bookOptional = bookRepository.findById(bookId);
		if(!bookOptional.isPresent()) {
			throw new IllegalStateException("Book does not exist");
		}
		
		Person person = personOptional.get();
		Book book = bookOptional.get();
		
		if(book.getPerson() == null) {
			throw new IllegalStateException("Book has no owner");
		}
		else if(book.getPerson().getId() != person.getId()) {
			throw new IllegalStateException("Book belonged to different owner");
		}
		
		book.setPerson(null);
		book.setDaysRented(0);
		bookRepository.save(book);
		return book.toString();
		
	}
	
	public void personMakePayment(Long personId, float payment) {
		Optional<Person> personOptional = personRepository.findById(personId);
		if(!personOptional.isPresent()) {
			throw new IllegalStateException("Person does not exist");
		}
		
		Person person = personOptional.get();
		
		makePayment(person, payment);
	}
	
	
	//Books
	public List<Book> getBooks(){
		return bookRepository.findAll();
	}
	
	public String addNewBook(Book book) {
		bookRepository.save(book);
		return book.toString();
	}
	
	public void deleteBook(Long bookId) {
		boolean exists = bookRepository.existsById(bookId);
		if(!exists) {
			throw new IllegalStateException("book does not exist");
		}
		bookRepository.deleteById(bookId);
	}
	
	public void passTime(int days) {
		List<Book> studentBooks = bookRepository.getTeacherBooks();
		List<Book> teacherBooks = bookRepository.getStudentBooks();
		
		int daysRented = 0;
		int newDaysValue = 0;
		
		for(Book book : studentBooks) {
			daysRented = book.getDaysRented();
			newDaysValue = daysRented + days;
			book.setDaysRented(newDaysValue);
			calculateStudentBalance(newDaysValue, days, book.getPerson());
			bookRepository.save(book);
		}
		for(Book book : teacherBooks) {
			daysRented = book.getDaysRented();
			newDaysValue = daysRented + days;
			book.setDaysRented(newDaysValue);
			calculateTeacherBalance(newDaysValue, days, book.getPerson());
			bookRepository.save(book);
		}
	}
	
	public void calculateStudentBalance(int daysRented, int days, Person person) {
		float balance = person.getBalance();
		if(daysRented == days && days > 5) {
			balance += (float) ((daysRented - 5) * 1.5);
		}else {
			balance += (float) (daysRented  * 1.5);
		}
		person.setBalance(balance);
		personRepository.save(person);
	}
	public void calculateTeacherBalance(int daysRented, int days,  Person person) {
		float balance = person.getBalance();
		if(daysRented == days && days > 7) {
			balance += (float) ((daysRented - 7) * 2.3);
		}else {
			balance += (float) (daysRented * 2.3);
		}
		person.setBalance(balance);
		personRepository.save(person);
	}
	
	public void makePayment(Person person, float payment) {
		float balance = person.getBalance();
		balance -= payment;
		person.setBalance(balance);
		personRepository.save(person);
		}
	
}
