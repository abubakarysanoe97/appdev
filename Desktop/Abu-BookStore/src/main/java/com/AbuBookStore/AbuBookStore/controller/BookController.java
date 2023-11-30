package com.AbuBookStore.AbuBookStore.controller;

import com.AbuBookStore.AbuBookStore.exceptions.BookNotFoundException;
import com.AbuBookStore.AbuBookStore.models.Book;
import com.AbuBookStore.AbuBookStore.models.Category;
import com.AbuBookStore.AbuBookStore.service.BookService;
import com.AbuBookStore.AbuBookStore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.NoSuchElementException;
@RestController
public class BookController {
    @Autowired
    private BookService bookService;
    @Autowired
    CategoryService categoryService;

    // Endpoint to create a book and ensure it has a category
    @PostMapping("/book")
    public ResponseEntity<Book> createBook(
            @RequestBody Book book,
            @RequestParam(name = "categoryId") Long categoryId
    ) {
        try {
            Category category = categoryService.getCategoryById(categoryId);
            book.setCategory(category);

            Book createdBook = bookService.createBook(book);
            HttpHeaders responseHeaders = new HttpHeaders();
            URI newBookUri = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{bookId}")
                    .buildAndExpand(book.getId())
                    .toUri();
            responseHeaders.setLocation(newBookUri);

            return new ResponseEntity<>(createdBook, responseHeaders, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


//    @PostMapping("/book")
//    public ResponseEntity<Book> createBook(@RequestBody Book book, @RequestParam Long categoryId) {
//        try {
//            Category category = categoryService.getCategoryById(categoryId);
//            book.setCategory(category);
//
//            Book createdBook = bookService.createBook(book);
//            HttpHeaders responseHeaders = new HttpHeaders();
//            URI newBookUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{bookId}").buildAndExpand(book.getId()).toUri();
//            responseHeaders.setLocation(newBookUri);
//
//            return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//    }

    // Endpoint to get a single book
    @GetMapping("/book/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        try {
            Book book = bookService.getBookById(bookId).orElseThrow(() ->
                    new BookNotFoundException("Book not found with id: " + bookId));

            return new ResponseEntity<>(book, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get all books and to search for books by name or sku
    @GetMapping("/book")
    public Iterable<Book> getAllBooks(@RequestParam(required = false) String keyword) {
        if (keyword != null) {
            return bookService.searchBooks(keyword);
        } else {
            return bookService.getAllBooks();
        }
    }

    // Endpoint to update a book
    @PutMapping("/book/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) {
        try {
            Book updated = bookService.editBook(bookId, updatedBook);
            return new ResponseEntity<>(updated, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to delete a book
    @DeleteMapping("book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        try {
            bookService.deleteBook(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint to get all books that belong to a category
    @GetMapping("book/category")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable Long categoryId) {
        try {
            List<Book> books = bookService.getBooksByCategory(categoryId);
            return new ResponseEntity<>(books, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

