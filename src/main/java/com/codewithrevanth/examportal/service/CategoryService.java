package com.codewithrevanth.examportal.service;

import com.codewithrevanth.examportal.model.Category;

import java.util.Set;

public interface CategoryService {


    Category addCategory(Category category);


    Category updateCategory(Category category);


    Set<Category> getCategories();


    Category getCategory(Long categoryId);


    void deleteCategory(Long categoryId);
}