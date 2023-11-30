package com.AbuBookStore.AbuBookStore.controller;

import com.AbuBookStore.AbuBookStore.models.Category;
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
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @PostMapping("/category")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        try {
            Category createdCategory = categoryService.createCategory(category);

            // Use fromPath() instead of fromCurrentRequest()
            URI newCategoryUri = ServletUriComponentsBuilder.fromPath("/{categoryId}")
                    .buildAndExpand(createdCategory.getId()).toUri();

            HttpHeaders responseHeaders = new HttpHeaders();
            responseHeaders.setLocation(newCategoryUri);

            return new ResponseEntity<>(createdCategory, responseHeaders, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // Endpoint to get all categories
    @GetMapping("/category")
    public Iterable<Category> getAllCategories() {
        return categoryService.getAllCategory();
    }

    // Endpoint to edit a category
    @PutMapping("/category/{categoryId}")
    public ResponseEntity<Category> editCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
        try {
            Category editedCategory = categoryService.editCategory(categoryId, updatedCategory);
            return new ResponseEntity<>(editedCategory, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

    // Endpoint to create a category
//    @PostMapping("/category")
//    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
//        try {
//            Category createdCategory = categoryService.createCategory(category);
//
//            // Use fromPath() instead of fromCurrentRequest()
//            URI newCategoryUri = ServletUriComponentsBuilder.fromPath("/category/{categoryId}")
//                    .buildAndExpand(createdCategory.getId()).toUri();
//
//            HttpHeaders responseHeaders = new HttpHeaders();
//            responseHeaders.setLocation(newCategoryUri);
//
//            return new ResponseEntity<>(createdCategory, responseHeaders, HttpStatus.CREATED);
//        } catch (RuntimeException e) {
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
////        try {
////            Category createdCategory = categoryService.createCategory(category);
////            HttpHeaders responseHeaders = new HttpHeaders();
////            URI newPCategoryUri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{categoryId}").buildAndExpand(category.getId()).toUri();
////            responseHeaders.setLocation(newPCategoryUri);
////            return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
////        } catch (RuntimeException e) {
////            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
////        }
//    }
//
//    // Endpoint to get all categories
//    @GetMapping("/category")
//    public Iterable<Category> getAllCategories() {
//        return categoryService.getAllCategory();
//    }
//
//    // Endpoint to edit a category
//    @PutMapping("/category/{categoryId}")
//    public ResponseEntity<Category> editCategory(@PathVariable Long categoryId, @RequestBody Category updatedCategory) {
//        try {
//            Category editedCategory = categoryService.editCategory(categoryId, updatedCategory);
//            return new ResponseEntity<>(editedCategory, HttpStatus.OK);
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//}

