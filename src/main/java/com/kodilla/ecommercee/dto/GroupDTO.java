package com.kodilla.ecommercee.dto;

import java.time.LocalDateTime;
import java.util.List;

public record GroupDTO (Long id,
                        String name,
                        String description,
                        LocalDateTime createdAt,
                        List<Long> productsIds) {
}
