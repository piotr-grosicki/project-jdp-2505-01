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
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "status")
    private OrderStatusEnum status;

    @Column(name = "total_amount")
    private BigDecimal totalAmount;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

//    @JoinTable(
//            name = "order_has_product",
//            joinColumns = @JoinColumn(name = "order_id"),
//            inverseJoinColumns = @JoinColumn(name = "product_id")
//    )
//    @ManyToMany
//    private List<Product> products = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

//    @OneToOne
//    @JoinColumn(name = "cart_id")
//    private Cart cart;
}
