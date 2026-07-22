package com.ec3.biblioteca.libros.service;

import com.ec3.biblioteca.libros.dto.LibroRequest;
import com.ec3.biblioteca.libros.dto.LibroResponse;
import com.ec3.biblioteca.libros.entity.Libro;
import com.ec3.biblioteca.libros.exception.BusinessException;
import com.ec3.biblioteca.libros.exception.ResourceNotFoundException;
import com.ec3.biblioteca.libros.mapper.LibroMapper;
import com.ec3.biblioteca.libros.repository.LibroRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LibroService {

    private final LibroRepository repository;
    private final LibroMapper mapper;

    public LibroService(LibroRepository repository, LibroMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<LibroResponse> listar() {
        return repository.findAll(Sort.by(Sort.Direction.ASC, "titulo"))
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public LibroResponse buscarPorId(Long id) {
        return mapper.toResponse(findEntity(id));
    }

    @Transactional
    public LibroResponse crear(LibroRequest request) {
        if (repository.existsByIsbn(request.isbn())) {
            throw new BusinessException("Ya existe un libro con el ISBN " + request.isbn());
        }
        return mapper.toResponse(repository.save(mapper.toEntity(request)));
    }

    @Transactional
    public LibroResponse actualizar(Long id, LibroRequest request) {
        Libro entity = findEntity(id);
        if (repository.existsByIsbnAndIdNot(request.isbn(), id)) {
            throw new BusinessException("Ya existe otro libro con el ISBN " + request.isbn());
        }
        mapper.updateEntity(request, entity);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public LibroResponse cambiarDisponibilidad(Long id, boolean disponible) {
        Libro entity = findEntity(id);
        entity.setDisponible(disponible);
        return mapper.toResponse(repository.save(entity));
    }

    @Transactional
    public void eliminar(Long id) {
        Libro entity = findEntity(id);
        if (!entity.isDisponible()) {
            throw new BusinessException("No se puede eliminar un libro que está prestado");
        }
        repository.delete(entity);
    }

    private Libro findEntity(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró el libro con id " + id));
    }
}
