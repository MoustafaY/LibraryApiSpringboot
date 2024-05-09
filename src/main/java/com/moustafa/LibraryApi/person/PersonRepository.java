package com.moustafa.LibraryApi.person;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.moustafa.LibraryApi.book.Book;
import com.moustafa.LibraryApi.person.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	@Query("Select p FROM Person p WHERE p.email = ?1")
	Optional<Person> findPersonByEmail(String email);
	
	@Query("SELECT b FROM Book b JOIN Person p ON b.person = ?1")
	List<Book> getBooks(Person person);
}
