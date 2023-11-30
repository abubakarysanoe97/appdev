package com.AbuBookStore.AbuBookStore.service;

import com.AbuBookStore.AbuBookStore.exceptions.BookNotFoundException;
import com.AbuBookStore.AbuBookStore.models.Book;
import com.AbuBookStore.AbuBookStore.models.Category;
import com.AbuBookStore.AbuBookStore.repository.BookRepository;
import com.AbuBookStore.AbuBookStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
@Service
public class BookService {
//    Endpoint needed to create a book and ensure it has a category
//    Endpoint needed to get a single book
//    Endpoint needed to get all books and to search for books by name or by sku
//    Endpoint needed to update a book
//    Endpoint needed to delete a book
//    Endpoint needed to get all of the books that belong to a category such as: "comic books or text books"
    @Autowired
    BookRepository bookRepository;
    @Autowired
    CategoryRepository categoryRepository;
public Book createBook(Book book){
    return bookRepository.save(book);
}
public Optional<Book> getBookById(Long id){
        return bookRepository.findById(id);
}
public Iterable<Book> getAllBooks() {
    return bookRepository.findAll();
    }
    public List<Book> getBooksByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));

        return bookRepository.findByCategory(category);
    }

public List<Book> searchBooks(String keyword) {
        return bookRepository.findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(keyword, keyword);
}

public Book editBook(Long id, Book book ) {
            Optional<Book> existingBookOptional = bookRepository.findById(id);
            if (existingBookOptional.isPresent()) {
                Book existingBook = existingBookOptional.get();

                existingBook.setName(book.getName());
                existingBook.setCategory(book.getCategory());
                existingBook.setDescription(book.getDescription());
                existingBook.setImage(book.getImage());
                existingBook.setPrice(book.getPrice());
                existingBook.setSku(book.getSku());
                existingBook.setStock(book.getStock());

                return bookRepository.save(existingBook);
            } else {

                throw new NoSuchElementException("Book with ID " + id + " not found");
            }
}
public void deleteBook(Long id) {
                bookRepository.deleteById(id);
    }

}


