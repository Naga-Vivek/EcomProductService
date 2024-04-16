package com.scaler.EcomProductService.repository;

import com.scaler.EcomProductService.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Product findByTitle(String title);
    Product findByTitleAndDescription(String title , String description);
    Product findByTitleOrDescription(String title , String description);
    Product findByPrice_amountLessThanEqual(double amount);
    Product findByPrice_amountLessThan(double amount);
    Product findByPrice_amountGreaterThan(double amount);
    Product findByPrice_amountGreaterThanEqual(double amount);
    Product findByPrice_amountBetween(double startAmount , double endAmount);

    // custom queries can also be done in JPA
    @Query(value = CustomQueries.FIND_PRODUCT_BY_TITLE , nativeQuery = true)
    List<Product> findByTitleLike(String title);

    @Query(value = "select * from product where description like %:descr% and title like %:title% ",nativeQuery = true)
    Product findByDescriptionTitleLike(String descr , String title);

    // To implement search using paging
    List<Product> findAllByTitleContainingIgnoreCase(String title,Pageable pageable);

}