package com.ws101.fformaran.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateProductDto(
    @NotBlank(message = "Product name is required")
    String name,

    @NotNull(message = "Product price is required")
    @Positive(message = "must be positive") // Strict positive check for price
    Double price
) {}