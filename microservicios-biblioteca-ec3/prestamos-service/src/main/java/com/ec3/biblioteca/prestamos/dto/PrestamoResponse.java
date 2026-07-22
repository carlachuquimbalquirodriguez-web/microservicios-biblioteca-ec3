package com.ec3.biblioteca.prestamos.dto;

import com.ec3.biblioteca.prestamos.entity.EstadoPrestamo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record PrestamoResponse(
        Long id,
        Long libroId,
        LibroResumen libro,
        String lectorNombre,
        String lectorDocumento,
        LocalDate fechaPrestamo,
        LocalDate fechaLimite,
        LocalDate fechaDevolucion,
        EstadoPrestamo estado,
        BigDecimal multa,
        String registradoPor,
        LocalDateTime creadoEn,
        LocalDateTime actualizadoEn
) {}
