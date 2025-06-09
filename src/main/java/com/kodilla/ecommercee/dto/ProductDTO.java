package com.kodilla.ecommercee.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO (Long id,
                          String name,
                          String description,
                          BigDecimal price,
                          Long quantity,
                          Boolean isActive,
                          LocalDateTime createdAt) {
}
