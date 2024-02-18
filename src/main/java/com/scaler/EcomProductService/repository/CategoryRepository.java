package com.scaler.EcomProductService.repository;

import com.scaler.EcomProductService.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}