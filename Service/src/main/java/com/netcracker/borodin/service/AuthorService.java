package com.netcracker.borodin.service;

import com.netcracker.borodin.converter.AuthorConverter;
import com.netcracker.borodin.dto.AuthorDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.repository.AuthorRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AuthorService {

    private AuthorRepositories authorRepositories;

    @Autowired
    public AuthorService(AuthorRepositories authorRepositories) {
        this.authorRepositories = authorRepositories;
    }

    public boolean addAuthor(String name) {
        if (authorRepositories.findByName(name).isPresent()) {
            return false;
        } else {
            authorRepositories.save(Author.builder().name(name).build());
            return true;
        }
    }

    public List<AuthorDTO> findAuthor(Map<String, List<String>> map) {
        List<Author> list = new ArrayList<>();
        if (map.containsKey("ID")) {
            Optional<Author> author = authorRepositories.findById(Long.parseLong(map.get("ID").get(0)));
            if (author.isPresent()) {
                return Collections.singletonList(AuthorConverter.converter(author.get()));
            }
        } else if (map.containsKey("name")) {
            list = authorRepositories.getAllByNameContains(map.get("name").get(0));
        }
        List<AuthorDTO> finds = new ArrayList<>();
        for (Author author : list) {
            finds.add(AuthorConverter.converter(author));
        }
        return finds;
    }

}
