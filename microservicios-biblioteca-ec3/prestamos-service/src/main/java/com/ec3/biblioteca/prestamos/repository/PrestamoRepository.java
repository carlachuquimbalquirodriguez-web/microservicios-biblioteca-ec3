package com.ec3.biblioteca.prestamos.repository;

import com.ec3.biblioteca.prestamos.entity.EstadoPrestamo;
import com.ec3.biblioteca.prestamos.entity.Prestamo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long> {
    List<Prestamo> findByEstadoOrderByFechaPrestamoDesc(EstadoPrestamo estado);
    List<Prestamo> findAllByOrderByFechaPrestamoDesc();
    boolean existsByLibroIdAndEstado(Long libroId, EstadoPrestamo estado);
}
