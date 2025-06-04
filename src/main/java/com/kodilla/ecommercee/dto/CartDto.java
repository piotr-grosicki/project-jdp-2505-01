package com.kodilla.ecommercee.dto;

import com.kodilla.ecommercee.domain.CartStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class CartDto {

    private Long id;
    private CartStatusEnum status;
    private LocalDateTime createdAt;
}
