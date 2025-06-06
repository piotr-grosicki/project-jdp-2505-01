package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Transactional
@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {
}
