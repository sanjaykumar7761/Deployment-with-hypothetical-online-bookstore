package com.onlinebookstore.service;



import com.onlinebookstore.payload.BookRequest;

import java.util.List;

public interface BookService {
    List<BookRequest> getAllBooks();
    BookRequest getBookById(Long id);
    BookRequest addBook(BookRequest bookRequest);
    BookRequest updateBook(Long id, BookRequest bookRequest);
    void deleteBook(Long id);
}
