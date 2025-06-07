package com.kodilla.ecommercee.dto;

public record ProductDTO (Long id,
                          String name,
                          String description,
                          String price,
                          String quantity,
                          String status,
                          String createdAt) {
}
