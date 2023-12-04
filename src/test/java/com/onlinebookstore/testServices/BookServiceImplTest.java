package com.onlinebookstore.testServices;
import com.onlinebookstore.entity.Book;
import com.onlinebookstore.exception.ResourceNotFoundException;
import com.onlinebookstore.payload.BookRequest;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.service.impl.BookServiceImpl;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    public void testGetAllBooks() {
        // Mocking data
        Book book1 = new Book(1L, "Title1", "Author1", "123456789", LocalDate.parse("2022-01-01"), "Fiction");
        Book book2 = new Book(2L, "Title2", "Author2", "987654321", LocalDate.parse("2022-02-01"), "Non-Fiction");
        when(bookRepository.findAll()).thenReturn(Arrays.asList(book1, book2));

        // Testing the method
        List<BookRequest> result = bookService.getAllBooks();

        // Assertions
        assertEquals(2, result.size());
        assertEquals("Title1", result.get(0).getTitle());
        assertEquals("Author1", result.get(0).getAuthor());
        assertEquals("Title2", result.get(1).getTitle());
        assertEquals("Author2", result.get(1).getAuthor());
    }

    @Test
    public void testGetBookById() {
        // Mocking data
        Long bookId = 1L;
        Book book = new Book(bookId, "Title", "Author", "123456789", LocalDate.parse("2022-01-01"), "Fiction");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        // Testing the method
        BookRequest result = bookService.getBookById(bookId);

        // Assertions
        assertEquals(bookId, result.getId());
        assertEquals("Title", result.getTitle());
        assertEquals("Author", result.getAuthor());
        assertEquals(LocalDate.parse("2022-01-01"), result.getPublishedDate());
    }

    @Test
    public void testGetBookById_ThrowsException() {
        // Mocking data
        Long invalidBookId = 99L;
        when(bookRepository.findById(invalidBookId)).thenReturn(Optional.empty());

        // Testing the method and expecting an exception
        assertThrows(ResourceNotFoundException.class, () -> bookService.getBookById(invalidBookId));
    }

    @Test
    public void testAddBook() {
        // Mocking data
        BookRequest bookRequest = new BookRequest(null, "NewTitle", "NewAuthor", "987654321", LocalDate.parse("2023-01-01"), "Mystery");
        Book book = new Book(1L, "NewTitle", "NewAuthor", "987654321", LocalDate.parse("2023-01-01"), "Mystery");
        when(bookRepository.save(Mockito.any())).thenReturn(book);

        // Testing the method
        BookRequest result = bookService.addBook(bookRequest);

        // Assertions
        assertEquals(1L, result.getId());
        assertEquals("NewTitle", result.getTitle());
        assertEquals("NewAuthor", result.getAuthor());
        assertEquals(LocalDate.parse("2023-01-01"), result.getPublishedDate());
    }

    @Test
    public void testUpdateBook() {
        // Mocking data
        Long bookId = 1L;
        LocalDate updatedPublishedDate = LocalDate.parse("2023-01-01");
        BookRequest bookRequest = new BookRequest(null, "UpdatedTitle", "UpdatedAuthor", "987654321", updatedPublishedDate, "Thriller");
        Book existingBook = new Book(bookId, "OriginalTitle", "OriginalAuthor", "123456789", LocalDate.parse("2022-01-01"), "Fiction");
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(Mockito.any())).thenReturn(existingBook);

        // Testing the method
        BookRequest result = bookService.updateBook(bookId, bookRequest);

        // Assertions
        assertEquals(bookId, result.getId());
        assertEquals("UpdatedTitle", result.getTitle());
        assertEquals("UpdatedAuthor", result.getAuthor());
        assertEquals("Thriller", result.getGenre());
        assertEquals(updatedPublishedDate, result.getPublishedDate());
    }

    @Test
    public void testDeleteBook() {
        // Mocking data
        Long bookId = 1L;

        // Testing the method
        bookService.deleteBook(bookId);

        // Verify that deleteById was called once
        verify(bookRepository, times(1)).deleteById(bookId);
    }
}
