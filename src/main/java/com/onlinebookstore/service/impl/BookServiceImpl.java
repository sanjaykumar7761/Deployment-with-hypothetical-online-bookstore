package com.onlinebookstore.service.impl;

import com.onlinebookstore.entity.Book;
import com.onlinebookstore.exception.ResourceNotFoundException;
import com.onlinebookstore.payload.BookRequest;
import com.onlinebookstore.repository.BookRepository;
import com.onlinebookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {


    private final BookRepository bookRepository;

    public List<BookRequest> getAllBooks() {
        List<Book> book = bookRepository.findAll();
       return book.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public BookRequest getBookById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found with this id: " + id)
        );
        return mapToDto(book);
    }

    public BookRequest addBook(BookRequest bookRequest) {
        Book book=mapToEntity(bookRequest);
        Book saved = bookRepository.save(book);
        return mapToDto(saved);
    }

    public BookRequest updateBook(Long id, BookRequest bookRequest) {
        Book book = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book not found with this id: " + id)
        );
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setPublishedDate(bookRequest.getPublishedDate());
        book.setGenre(bookRequest.getGenre());
        Book updatedBook = bookRepository.save(book);
        return mapToDto(updatedBook);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }

    Book mapToEntity(BookRequest bookRequest){
        Book book=new Book();
        book.setTitle(bookRequest.getTitle());
        book.setAuthor(bookRequest.getAuthor());
        book.setIsbn(bookRequest.getIsbn());
        book.setPublishedDate(bookRequest.getPublishedDate());
        book.setGenre(bookRequest.getGenre());
        return book;
    }

    BookRequest mapToDto(Book book){
        BookRequest dto=new BookRequest();
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setIsbn(book.getIsbn());
        dto.setPublishedDate(book.getPublishedDate());
        dto.setGenre(book.getGenre());
        return dto;
    }
}
