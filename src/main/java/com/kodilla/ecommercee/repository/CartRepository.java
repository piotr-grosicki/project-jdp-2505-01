package com.kodilla.ecommercee.repository;

import com.kodilla.ecommercee.domain.Cart;
import com.kodilla.ecommercee.domain.CartStatusEnum;
import jakarta.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
    Optional<List<Cart>> findAllByStatus(CartStatusEnum status);
    Optional<Cart> findByCreatedAtBefore(LocalDateTime dateTime);

}