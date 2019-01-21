package com.netcracker.borodin.repository;

import com.netcracker.borodin.entity.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepositories extends CrudRepository<Author, Long> {
    Author save(Author genre);

    Optional<Author> findByName(String name);

    List<Author> getAllByNameContains(String name);

    Optional<Author> findById(Long id);
}
