package com.onlinebookstore.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRequest {

    private Long id;
    private String title;
    private String author;
    private String isbn;
    private LocalDate publishedDate;
    private String genre;
}
