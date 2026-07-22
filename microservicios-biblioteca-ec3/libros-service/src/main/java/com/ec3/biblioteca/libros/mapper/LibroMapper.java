package com.ec3.biblioteca.libros.mapper;

import com.ec3.biblioteca.libros.dto.LibroRequest;
import com.ec3.biblioteca.libros.dto.LibroResponse;
import com.ec3.biblioteca.libros.entity.Libro;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LibroMapper {

    LibroResponse toResponse(Libro entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", constant = "true")
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    Libro toEntity(LibroRequest request);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "disponible", ignore = true)
    @Mapping(target = "creadoEn", ignore = true)
    @Mapping(target = "actualizadoEn", ignore = true)
    void updateEntity(LibroRequest request, @MappingTarget Libro entity);
}
