package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.*;
import java.util.List;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "email")
    private String email;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "token")
    private String token;

    @Column(name = "token_created_at")
    private LocalDateTime tokenCreatedAt;

    @Column(name = "token_expires_at")
    private LocalDateTime tokenExpiresAt;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Order> orders;

    @OneToMany( fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Cart> carts;

}