package com.netcracker.borodin.repository;

import com.netcracker.borodin.SQLConstant;
import com.netcracker.borodin.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsernameAndHashPassword(String username, String hashPassword);

    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    List<User> findAll();

    @Modifying
    @Query(value = SQLConstant.UPDATE_USER, nativeQuery = true)
    void update(String email, String username, long id, String role, String state);

    User save(User user);

    @Modifying
    @Query(value = SQLConstant.ADD_USER_BOOK, nativeQuery = true)
    void addMyBook(long userId, long bookId, int rate);

    @Modifying
    @Query(value = SQLConstant.UPDATE_USER_BOOK, nativeQuery = true)
    void updateMyBook(long userId, long bookId, int rate);

    @Query(value = SQLConstant.GET_RATE_USER_BOOK, nativeQuery = true)
    Optional<Integer> getMyRate(long bookId, long userId);
}
