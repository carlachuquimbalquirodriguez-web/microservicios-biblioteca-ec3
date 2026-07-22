package com.ec3.biblioteca.prestamos.client;

import com.ec3.biblioteca.prestamos.dto.LibroResumen;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "libros-service", path = "/api/libros")
public interface LibroClient {

    @GetMapping("/{id}")
    LibroResumen buscarPorId(@PathVariable("id") Long id);

    @PatchMapping("/{id}/disponibilidad")
    LibroResumen cambiarDisponibilidad(@PathVariable("id") Long id, @RequestParam("disponible") boolean disponible);
}
