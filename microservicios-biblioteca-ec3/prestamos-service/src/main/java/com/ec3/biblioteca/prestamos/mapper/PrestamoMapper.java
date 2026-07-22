package com.ec3.biblioteca.prestamos.mapper;

import com.ec3.biblioteca.prestamos.dto.LibroResumen;
import com.ec3.biblioteca.prestamos.dto.PrestamoRequest;
import com.ec3.biblioteca.prestamos.dto.PrestamoResponse;
import com.ec3.biblioteca.prestamos.entity.Prestamo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PrestamoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "fechaPrestamo", expression = "java(java.time.LocalDate.now())")
    @Mapping(target = "fechaDevolucion", ignore = true)
    @Mapping(target = "estado", expression = "java(com.ec3.biblioteca.prestamos.entity.EstadoPrestamo.ACTIVO)")
    @Mapping(target = "multa", expression = "java(java.math.BigDecimal.ZERO)")
    @Mapping(target = "registradoPor", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    Prestamo toEntity(PrestamoRequest request);

    @Mapping(target = "id", source = "entity.id")
    @Mapping(target = "libroId", source = "entity.libroId")
    @Mapping(target = "libro", source = "libro")
    @Mapping(target = "lectorNombre", source = "entity.lectorNombre")
    @Mapping(target = "lectorDocumento", source = "entity.lectorDocumento")
    @Mapping(target = "fechaPrestamo", source = "entity.fechaPrestamo")
    @Mapping(target = "fechaLimite", source = "entity.fechaLimite")
    @Mapping(target = "fechaDevolucion", source = "entity.fechaDevolucion")
    @Mapping(target = "estado", source = "entity.estado")
    @Mapping(target = "multa", source = "entity.multa")
    @Mapping(target = "registradoPor", source = "entity.registradoPor")
    @Mapping(target = "creadoEn", source = "entity.creadoEn")
    @Mapping(target = "actualizadoEn", source = "entity.actualizadoEn")
    PrestamoResponse toResponse(Prestamo entity, LibroResumen libro);
}
