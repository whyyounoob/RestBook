package com.netcracker.borodin.service;

import com.netcracker.borodin.converter.BookConverter;
import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.entity.Author;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.Genre;
import com.netcracker.borodin.repository.AuthorRepositories;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BookService {

    private BookRepository bookRepository;
    private GenreRepository genreRepository;
    private AuthorRepositories authorRepositories;

    @Autowired
    public BookService(
            BookRepository bookRepository,
            GenreRepository genreRepository,
            AuthorRepositories authorRepositories) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepositories = authorRepositories;
    }

    public List<BookDTO> showTop100(String type, String value) {
        List<Book> books = null;
        List<BookDTO> finds = new ArrayList<>();
        if (type == null) {
            books = bookRepository.findTop100ByOrderByAverageRateDesc();
        } else if (type.equals("genre")) {
            Optional<Genre> genre = genreRepository.findById(Long.parseLong(value));
            if (genre.isPresent()) {
                books = bookRepository.findFirst100ByGenresOrderByAverageRateDesc(genre.get());
            }
        } else if (type.equals("author")) {
            Optional<Author> author = authorRepositories.findById(Long.parseLong(value));
            if (author.isPresent()) {
                books = bookRepository.findFirst100ByAuthorsOrderByAverageRateDesc(author.get());
            }
        }
        for (Book book : books) {
            finds.add(BookConverter.converter(book));
        }
        return finds;
    }

    public List<BookDTO> findBook(Map<String, List<String>> map) {
        List<Book> finds = new ArrayList<>();
        if (map.containsKey("ID")) {
            Optional<Book> book = bookRepository.findById(Long.parseLong(map.get("ID").get(0)));
            if (book.isPresent()) {
                Book findBook = book.get();
                Optional<BigDecimal> avgRate = bookRepository.getAverageRate(findBook.getId());
                avgRate.ifPresent(bigDecimal -> findBook.setAverageRate(bigDecimal.doubleValue()));
                return Collections.singletonList(BookConverter.converter(findBook));
            }
        } else if (map.containsKey("title")) {
            finds = bookRepository.findByTitle(map.get("title").get(0));
        } else if (map.containsKey("genre")) {
            Optional<Genre> genre = genreRepository.findById(Long.parseLong(map.get("genre").get(0)));
            if (genre.isPresent()) {
                finds = bookRepository.findByGenres(genre.get());
            }
        } else if (map.containsKey("author")) {
            Optional<Author> author = authorRepositories.findById(Long.parseLong(map.get("author").get(0)));
            if (author.isPresent()) {
                finds = bookRepository.findByAuthors(author.get());
            }
        }
        List<BookDTO> list = new ArrayList<>();
        for (Book book : finds) {
            list.add(BookConverter.converter(book));
        }
        return list;
    }

    @Transactional
    public boolean addBook(Map<String, Object> bookInfo) {
        Book book = new Book();
        for (int idG : (List<Integer>) bookInfo.get("genres")) {
            Optional<Genre> genre = genreRepository.findById((long) idG);
            if (genre.isPresent()) {
                book.addGenre(genre.get());
            } else {
                return false;
            }
        }
        for (int idA : (List<Integer>) bookInfo.get("authors")) {
            Optional<Author> author = authorRepositories.findById((long) idA);
            if (author.isPresent()) {
                book.addAuthor(author.get());
            } else return false;
        }
        book.setTitle((String) bookInfo.get("title"));
        book.setYear((int) bookInfo.get("year"));
        bookRepository.save(book);
        return true;
    }

}

