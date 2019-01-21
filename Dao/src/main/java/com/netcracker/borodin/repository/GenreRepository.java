package com.netcracker.borodin.repository;

import com.netcracker.borodin.entity.Genre;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GenreRepository extends CrudRepository<Genre, Long> {
    Genre save(Genre genre);

    Optional<Genre> findByName(String name);

    List<Genre> getAllByNameContains(String name);

    Optional<Genre> findById(Long id);
}
