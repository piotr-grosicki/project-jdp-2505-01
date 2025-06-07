package com.kodilla.ecommercee.dto;

import java.util.List;

public record CartDTO (Long id, String status, String createdAt, List<ProductDTO> products) {
}
