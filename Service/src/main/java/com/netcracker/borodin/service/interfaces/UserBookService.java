package com.netcracker.borodin.service.interfaces;

import com.netcracker.borodin.dto.UserBookDTO;
import com.netcracker.borodin.dto.UserBookForm;

import java.util.List;

public interface UserBookService {

  /**
   * This method for retrieving list of books with their rate for current user
   *
   * @param userId - id of the current user
   * @return list of books with rate
   */
  List<UserBookDTO> getMyBooks(long userId);

  /**
   * This method for adding book to your list with your rate
   *
   * @param userId - user`s id
   * @return true if book added to your list
   */
  boolean addMyBook(UserBookForm userBookForm, long userId);

  /**
   * This method for updating rate in ypu list
   *
   * @param userId - user`s id
   * @return true if rate was updated
   */
  boolean updateRate(UserBookForm userBookForm, long userId);
}
