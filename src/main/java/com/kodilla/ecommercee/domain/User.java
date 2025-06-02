package com.kodilla.ecommercee.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user")
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

    //@OneToMany
    //@JoinColumn(name = "user_id")
    //private List<Order> orders;

    //@OneToMany
    //@JoinColumn(name = "user_id")
    //private List<Cart> carts;

}