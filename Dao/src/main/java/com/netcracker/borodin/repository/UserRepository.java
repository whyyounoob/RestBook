package com.netcracker.borodin.repository;

import com.netcracker.borodin.SQLConstant;
import com.netcracker.borodin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  Optional<User> findByUsername(String username);

  List<User> findAll();

  @Modifying
  @Query(value = SQLConstant.UPDATE_USER, nativeQuery = true)
  void update(long id, String role, String state);
}
