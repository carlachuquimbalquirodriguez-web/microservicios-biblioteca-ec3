package com.ec3.biblioteca.libros.dto;

import jakarta.validation.constraints.*;

public record LibroRequest(
        @NotBlank(message = "El ISBN es obligatorio")
        @Size(max = 20, message = "El ISBN no puede superar 20 caracteres")
        String isbn,

        @NotBlank(message = "El título es obligatorio")
        @Size(max = 160, message = "El título no puede superar 160 caracteres")
        String titulo,

        @NotBlank(message = "El autor es obligatorio")
        @Size(max = 120, message = "El autor no puede superar 120 caracteres")
        String autor,

        @NotBlank(message = "La categoría es obligatoria")
        @Size(max = 80, message = "La categoría no puede superar 80 caracteres")
        String categoria,

        @NotNull(message = "El año de publicación es obligatorio")
        @Min(value = 1000, message = "El año debe ser igual o mayor a 1000")
        @Max(value = 2100, message = "El año debe ser igual o menor a 2100")
        Integer anioPublicacion
) {}
