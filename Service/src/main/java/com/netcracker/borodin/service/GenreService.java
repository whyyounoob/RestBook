package com.netcracker.borodin.service;

import com.netcracker.borodin.converter.GenreConverter;
import com.netcracker.borodin.dto.GenreDTO;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GenreService {

    private GenreRepository genreRepository;

    @Autowired
    public GenreService(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    public List<GenreDTO> findGenre(Map<String, List<String>> map) {
        List<Genre> list = new ArrayList<>();
        if (map.containsKey("ID")) {
            Optional<Genre> genre = genreRepository.findById(Long.parseLong(map.get("ID").get(0)));
            if (genre.isPresent()) {
                return Collections.singletonList(GenreConverter.converter(genre.get()));
            }
        } else if (map.containsKey("name")) {
            list = genreRepository.getAllByNameContains(map.get("name").get(0));
        }
        List<GenreDTO> finds = new ArrayList<>();
        for (Genre genre : list) {
            finds.add(GenreConverter.converter(genre));
        }
        return finds;
    }

    public boolean addGenre(String name) {
        if (genreRepository.findByName(name).isPresent()) {
            return false;
        } else {
            genreRepository.save(Genre.builder().name(name).build());
            return true;
        }
    }
}
