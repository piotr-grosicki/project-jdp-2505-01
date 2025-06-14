package com.kodilla.ecommercee.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CartDTO (Long id,
                       String status,
                       LocalDateTime createdAt,
                       List<Long> products,
                       Long userId,
                       Long orderId) {
}
