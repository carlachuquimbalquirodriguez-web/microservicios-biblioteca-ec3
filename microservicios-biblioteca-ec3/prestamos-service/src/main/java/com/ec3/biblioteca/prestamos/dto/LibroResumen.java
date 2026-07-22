package com.ec3.biblioteca.prestamos.dto;

import java.time.LocalDateTime;

public record LibroResumen(
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
