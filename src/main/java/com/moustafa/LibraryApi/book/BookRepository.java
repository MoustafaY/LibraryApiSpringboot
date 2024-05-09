package com.moustafa.LibraryApi.book;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	@Query("SELECT b FROM Book b WHERE b.person IS NOT NULL AND b.person.role = 'TEACHER'")
	List<Book> getTeacherBooks();
	
	@Query("SELECT b FROM Book b WHERE b.person IS NOT NULL AND b.person.role = 'STUDENT'")
	List<Book> getStudentBooks();
}
