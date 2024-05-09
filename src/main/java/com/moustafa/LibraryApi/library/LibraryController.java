package com.moustafa.LibraryApi.library;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.moustafa.LibraryApi.book.Book;
import com.moustafa.LibraryApi.person.Person;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping(path="/")
public class LibraryController {
	private final LibraryService libraryService;
	
	@Autowired
	public LibraryController(LibraryService libraryService) {
		this.libraryService = libraryService;
	}
	
	//Students
	@GetMapping(path="Users")
	public List<Person> getPerson(){
		return libraryService.getPersons();
	}
	
	@PostMapping(path="signup")
	public ResponseEntity<String> createNewStudent(@RequestBody Person person) {
		Long id = libraryService.addNewPerson(person);
		return ResponseEntity.ok("User Id: " + id);
	}
	
	@PostMapping(path="login")
	public ResponseEntity<String> loginPerson(@RequestBody Person person){
		Long id = libraryService.login(person);
		return ResponseEntity.ok("User Id: " + id);
	}
	
	@DeleteMapping(path="/Users/{personId}")
	public ResponseEntity<String> deletePerson(@PathVariable("personId") Long personId){
		libraryService.deletePerson(personId);
		return ResponseEntity.ok("User removed");
	}
	
	@GetMapping(path="/Users/{personId}/Books")
	public List<Book> getStudentBooks(@PathVariable("personId") Long personId){
		return libraryService.getPersonBooks(personId);
	}
	
	@PostMapping(path="/Users/{personId}/Books")
	public ResponseEntity<String> personRentBook(@PathVariable("personId") Long personId, @RequestBody Long bookId){
		String book = libraryService.personRentBook(personId, bookId);
		return ResponseEntity.ok(book);
	}
	
	@DeleteMapping(path="/Users/{personId}/Books")
	public ResponseEntity<String> personReturnBook(@PathVariable("personId") Long personId, @RequestBody Long bookId){
		String book = libraryService.personReturnBook(personId, bookId);
		return ResponseEntity.ok(book);
	}
	
	@PostMapping(path="/Users/{personId}")
	public ResponseEntity<String> personMakePayment(@PathVariable("personId") Long personId, @RequestBody float payment){
		libraryService.personMakePayment(personId, payment);
		return ResponseEntity.ok("Payment received");
	}
	
	
	//Books
	@PostMapping(path="Books")
	public ResponseEntity<String> createNewBook(@RequestBody Book book){
		String bookOut = libraryService.addNewBook(book);
		return ResponseEntity.ok(bookOut);
	}
	
	@GetMapping(path="Books")
	public List<Book> getBooks(){
		return libraryService.getBooks();
	}
	
	@DeleteMapping(path="/Books/{bookId}")
	public ResponseEntity<String> deleteBook(@PathVariable("bookId") Long bookId){
		libraryService.deleteBook(bookId);
		return ResponseEntity.ok("Book removed");
	}
	
	@PostMapping(path="/passtime")
	public ResponseEntity<String> passTime(@RequestBody int days){
		libraryService.passTime(days);
		return ResponseEntity.ok("Time passed");
	}
}
