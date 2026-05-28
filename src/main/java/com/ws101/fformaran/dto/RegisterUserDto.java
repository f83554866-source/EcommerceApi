package com.ws101.fformaran.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterUserDto(
    @NotBlank(message = "Username is required")
    @Size(min = 8, max = 20, message = "must be between 8 and 20 characters")
    String username,

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "must be at least 8 characters")
    String password,

    @NotBlank(message = "Role is required")
    String role
) {}