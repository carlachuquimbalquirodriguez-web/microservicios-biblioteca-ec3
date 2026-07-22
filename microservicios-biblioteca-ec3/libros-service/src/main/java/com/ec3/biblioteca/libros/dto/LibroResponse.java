package com.ec3.biblioteca.libros.dto;

import java.time.LocalDateTime;

public record LibroResponse(
        Long id,
        String isbn,
        String titulo,
        String autor,
        String categoria,
        Integer anioPublicacion,
        boolean disponible,
        LocalDateTime creadoEn,
        LocalDateTime actualizadoEn
) {}
