package com.codewithrevanth.examportal.repository;

import com.codewithrevanth.examportal.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    // Standard CRUD methods like save, findAll, findById, and delete are inherited out-of-the-box
}