package com.kodilla.ecommercee.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDTO(
        Long id,
        String status,
        BigDecimal totalAmount,
        LocalDateTime createdAt,
        Long userId,
        Long cartId,
        List<Long> productIds
) {}