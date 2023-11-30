package com.AbuBookStore.AbuBookStore.service;

import com.AbuBookStore.AbuBookStore.models.Category;
import com.AbuBookStore.AbuBookStore.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CategoryService {
//    Endpoint needed to create a category
//    Endpoint needed to get all categories
//    Endpoint needed to edit a category
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category){
        return categoryRepository.save(category);
    }
    public Iterable<Category> getAllCategory(){
        return categoryRepository.findAll();
    }
    public Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with id: " + categoryId));
    }

    public Category editCategory(Long id, Category category ){
    Optional<Category> existingCategoryOptional = categoryRepository.findById(id);

        if (existingCategoryOptional.isPresent()) {
        Category existingCategory = existingCategoryOptional.get();

        existingCategory.setName(category.getName());

        return categoryRepository.save(existingCategory);
    } else {

        throw new NoSuchElementException("Category with ID " + id + " not found");
    }


}

}
