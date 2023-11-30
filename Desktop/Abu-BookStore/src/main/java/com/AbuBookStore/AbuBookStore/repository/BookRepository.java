package com.AbuBookStore.AbuBookStore.repository;

import com.AbuBookStore.AbuBookStore.models.Book;
import com.AbuBookStore.AbuBookStore.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BookRepository extends CrudRepository<Book,Long> {
    List<Book> findByCategory(Category category);
    List<Book> findByNameContainingIgnoreCaseOrSkuContainingIgnoreCase(String name, String sku);
}
