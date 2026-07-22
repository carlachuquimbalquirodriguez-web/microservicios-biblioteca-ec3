package com.ec3.biblioteca.prestamos.service;

import com.ec3.biblioteca.prestamos.client.LibroClient;
import com.ec3.biblioteca.prestamos.dto.LibroResumen;
import com.ec3.biblioteca.prestamos.dto.PrestamoRequest;
import com.ec3.biblioteca.prestamos.dto.PrestamoResponse;
import com.ec3.biblioteca.prestamos.entity.EstadoPrestamo;
import com.ec3.biblioteca.prestamos.entity.Prestamo;
import com.ec3.biblioteca.prestamos.exception.BusinessException;
import com.ec3.biblioteca.prestamos.exception.ResourceNotFoundException;
import com.ec3.biblioteca.prestamos.exception.ServiceUnavailableException;
import com.ec3.biblioteca.prestamos.mapper.PrestamoMapper;
import com.ec3.biblioteca.prestamos.repository.PrestamoRepository;
import feign.FeignException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class PrestamoService {

    private static final BigDecimal MULTA_POR_DIA = new BigDecimal("2.50");

    private final PrestamoRepository repository;
    private final PrestamoMapper mapper;
    private final LibroClient libroClient;

    public PrestamoService(PrestamoRepository repository, PrestamoMapper mapper, LibroClient libroClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.libroClient = libroClient;
    }

    @Transactional(readOnly = true)
    public List<PrestamoResponse> listar(EstadoPrestamo estado) {
        List<Prestamo> prestamos = estado == null
                ? repository.findAllByOrderByFechaPrestamoDesc()
                : repository.findByEstadoOrderByFechaPrestamoDesc(estado);
        return prestamos.stream().map(this::toResponseSafe).toList();
    }

    @Transactional(readOnly = true)
    public PrestamoResponse buscarPorId(Long id) {
        Prestamo prestamo = findEntity(id);
        return mapper.toResponse(prestamo, obtenerLibro(prestamo.getLibroId()));
    }

    @Transactional
    public PrestamoResponse crear(PrestamoRequest request, Authentication authentication) {
        LibroResumen libro = obtenerLibro(request.libroId());
        if (!libro.disponible()) {
            throw new BusinessException("El libro '" + libro.titulo() + "' no está disponible");
        }
        if (repository.existsByLibroIdAndEstado(request.libroId(), EstadoPrestamo.ACTIVO)) {
            throw new BusinessException("El libro ya tiene un préstamo activo");
        }

        Prestamo entity = mapper.toEntity(request);
        entity.setRegistradoPor(authentication.getName());
        Prestamo guardado = repository.save(entity);

        cambiarDisponibilidad(request.libroId(), false);
        return mapper.toResponse(guardado, libroConDisponibilidad(libro, false));
    }

    @Transactional
    public PrestamoResponse devolver(Long id) {
        Prestamo entity = findEntity(id);
        if (entity.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BusinessException("El préstamo ya fue devuelto");
        }

        LocalDate fechaDevolucion = LocalDate.now();
        entity.setFechaDevolucion(fechaDevolucion);
        entity.setEstado(EstadoPrestamo.DEVUELTO);
        entity.setMulta(calcularMulta(entity.getFechaLimite(), fechaDevolucion));
        Prestamo actualizado = repository.save(entity);

        LibroResumen libro = cambiarDisponibilidad(entity.getLibroId(), true);
        return mapper.toResponse(actualizado, libro);
    }

    BigDecimal calcularMulta(LocalDate fechaLimite, LocalDate fechaDevolucion) {
        if (!fechaDevolucion.isAfter(fechaLimite)) {
            return BigDecimal.ZERO;
        }
        long diasRetraso = ChronoUnit.DAYS.between(fechaLimite, fechaDevolucion);
        return MULTA_POR_DIA.multiply(BigDecimal.valueOf(diasRetraso));
    }

    private Prestamo findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el préstamo con id " + id));
    }

    private PrestamoResponse toResponseSafe(Prestamo entity) {
        try {
            return mapper.toResponse(entity, obtenerLibro(entity.getLibroId()));
        } catch (ServiceUnavailableException ex) {
            return mapper.toResponse(entity, null);
        }
    }

    private LibroResumen obtenerLibro(Long libroId) {
        try {
            return libroClient.buscarPorId(libroId);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("No existe el libro con id " + libroId);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("No se pudo comunicar con libros-service");
        }
    }

    private LibroResumen cambiarDisponibilidad(Long libroId, boolean disponible) {
        try {
            return libroClient.cambiarDisponibilidad(libroId, disponible);
        } catch (FeignException.NotFound ex) {
            throw new ResourceNotFoundException("No existe el libro con id " + libroId);
        } catch (FeignException ex) {
            throw new ServiceUnavailableException("No se pudo actualizar la disponibilidad en libros-service");
        }
    }

    private LibroResumen libroConDisponibilidad(LibroResumen libro, boolean disponible) {
        return new LibroResumen(
                libro.id(), libro.isbn(), libro.titulo(), libro.autor(), libro.categoria(),
                libro.anioPublicacion(), disponible, libro.creadoEn(), libro.actualizadoEn());
    }
}
