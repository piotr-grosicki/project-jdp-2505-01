package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.*;

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
    @Setter
    @Column(name = "status")
    private OrderStatusEnum status;
    @Setter
    @Column(name = "total_amount")
    private BigDecimal totalAmount;
    @Setter
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Setter
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "order_has_product",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<Product> products = new ArrayList<>();
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

}
