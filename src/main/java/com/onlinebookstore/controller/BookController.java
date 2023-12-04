package com.onlinebookstore.controller;

import com.onlinebookstore.payload.BookRequest;
import com.onlinebookstore.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    //http://localhost:8080/api/books
    @GetMapping
    public ResponseEntity<List<BookRequest>> getAllBooks() {
        List<BookRequest> dto=  bookService.getAllBooks();
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //http://localhost:8080/api/books/id
    @GetMapping("/{id}")
    public ResponseEntity<BookRequest> getBookById(@PathVariable Long id) {
        BookRequest dto= bookService.getBookById(id);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    //http://localhost:8080/api/books
    @PostMapping
    public ResponseEntity<BookRequest> addBook(@RequestBody BookRequest bookRequest) {
        BookRequest dto= bookService.addBook(bookRequest);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookRequest> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        BookRequest dto= bookService.updateBook(id, bookRequest);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return new ResponseEntity<>("Book is deleted",HttpStatus.OK);
    }
}

