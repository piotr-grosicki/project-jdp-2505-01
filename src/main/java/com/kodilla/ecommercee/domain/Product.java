package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "price")
    private BigDecimal price;
    @Column(name = "description")
    private String description;
    @Column(name = "stock_quantity")
    private Long stockQuantity;
    @Column(name = "is_active")
    private boolean isActive;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Cart> carts = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Group group;;
}
