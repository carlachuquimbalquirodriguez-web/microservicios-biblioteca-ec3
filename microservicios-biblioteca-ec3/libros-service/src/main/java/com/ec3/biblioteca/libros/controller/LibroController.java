package com.ec3.biblioteca.libros.controller;

import com.ec3.biblioteca.libros.dto.LibroRequest;
import com.ec3.biblioteca.libros.dto.LibroResponse;
import com.ec3.biblioteca.libros.service.LibroService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Administración del catálogo de libros")
public class LibroController {

    private final LibroService service;

    public LibroController(LibroService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todos los libros")
    public List<LibroResponse> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un libro por id")
    public LibroResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Registrar un libro")
    public ResponseEntity<LibroResponse> crear(@Valid @RequestBody LibroRequest request) {
        LibroResponse creado = service.crear(request);
        return ResponseEntity.created(URI.create("/api/libros/" + creado.id())).body(creado);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un libro")
    public LibroResponse actualizar(@PathVariable Long id, @Valid @RequestBody LibroRequest request) {
        return service.actualizar(id, request);
    }

    @PatchMapping("/{id}/disponibilidad")
    @Operation(summary = "Cambiar la disponibilidad de un libro", description = "Usado por préstamos-service mediante OpenFeign")
    public LibroResponse cambiarDisponibilidad(@PathVariable Long id, @RequestParam boolean disponible) {
        return service.cambiarDisponibilidad(id, disponible);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Eliminar un libro disponible")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
