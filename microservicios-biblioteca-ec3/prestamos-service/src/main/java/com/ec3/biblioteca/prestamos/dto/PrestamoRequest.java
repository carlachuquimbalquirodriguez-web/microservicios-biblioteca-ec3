package com.ec3.biblioteca.prestamos.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record PrestamoRequest(
        @NotNull(message = "El id del libro es obligatorio")
        Long libroId,

        @NotBlank(message = "El nombre del lector es obligatorio")
        @Size(max = 140, message = "El nombre no puede superar 140 caracteres")
        String lectorNombre,

        @NotBlank(message = "El documento del lector es obligatorio")
        @Size(max = 20, message = "El documento no puede superar 20 caracteres")
        String lectorDocumento,

        @NotNull(message = "La fecha límite es obligatoria")
        @FutureOrPresent(message = "La fecha límite no puede estar en el pasado")
        LocalDate fechaLimite
) {}
