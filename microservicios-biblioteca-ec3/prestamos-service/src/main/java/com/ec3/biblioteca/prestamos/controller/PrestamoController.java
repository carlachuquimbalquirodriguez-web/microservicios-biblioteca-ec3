package com.ec3.biblioteca.prestamos.controller;

import com.ec3.biblioteca.prestamos.dto.PrestamoRequest;
import com.ec3.biblioteca.prestamos.dto.PrestamoResponse;
import com.ec3.biblioteca.prestamos.entity.EstadoPrestamo;
import com.ec3.biblioteca.prestamos.service.PrestamoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/prestamos")
@Tag(name = "Préstamos", description = "Gestión de préstamos y devoluciones")
public class PrestamoController {

    private final PrestamoService service;

    public PrestamoController(PrestamoService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar préstamos", description = "Puede filtrarse por ACTIVO o DEVUELTO")
    public List<PrestamoResponse> listar(@RequestParam(required = false) EstadoPrestamo estado) {
        return service.listar(estado);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar un préstamo por id")
    public PrestamoResponse buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PostMapping
    @Operation(summary = "Registrar un préstamo", description = "Consulta libros-service y cambia el libro a no disponible")
    public ResponseEntity<PrestamoResponse> crear(
            @Valid @RequestBody PrestamoRequest request,
            Authentication authentication) {
        PrestamoResponse creado = service.crear(request, authentication);
        return ResponseEntity.created(URI.create("/api/prestamos/" + creado.id())).body(creado);
    }

    @PostMapping("/{id}/devolucion")
    @Operation(summary = "Registrar devolución", description = "Calcula S/ 2.50 por día de retraso y libera el libro")
    public PrestamoResponse devolver(@PathVariable Long id) {
        return service.devolver(id);
    }
}
