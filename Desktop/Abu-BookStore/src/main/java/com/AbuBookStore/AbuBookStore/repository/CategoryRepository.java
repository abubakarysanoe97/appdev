package com.AbuBookStore.AbuBookStore.repository;

import com.AbuBookStore.AbuBookStore.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Long> {
}
