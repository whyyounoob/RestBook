package com.netcracker.borodin.service;

import com.netcracker.borodin.converter.BookConverter;
import com.netcracker.borodin.converter.UserConverter;
import com.netcracker.borodin.dto.BookDTO;
import com.netcracker.borodin.dto.UserDTO;
import com.netcracker.borodin.entity.Book;
import com.netcracker.borodin.entity.User;
import com.netcracker.borodin.entity.enums.Role;
import com.netcracker.borodin.entity.enums.State;
import com.netcracker.borodin.repository.BookRepository;
import com.netcracker.borodin.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Service for work with {@link User}
 *
 * @author Maxim Borodin
 */
@Service
public class UserService {

    private UserRepository userRepository;

    private BookRepository bookRepository;

    private User currentUser;

    @Autowired
    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    /**
     * @param username     - username
     * @param hashPassword - password with hash
     * @return true if username and hashPassword correct
     */
    public boolean signIn(String username, String hashPassword) {
        Optional<User> user = userRepository.findByUsernameAndHashPassword(username, hashPassword);
        if (user.isPresent()) {
            currentUser = user.get();
            return true;
        } else return false;

    }

    /**
     * @return state of current user
     */
    public State getState() {
        return currentUser.getState();
    }

    /**
     * Check username or new user
     *
     * @param username - username
     * @return true if this username is already exists
     */
    public boolean checkUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Check email or new user
     *
     * @param email - email
     * @return true if this email is already exists
     */
    public boolean checkEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Create new user
     *
     * @param username     - username for new user
     * @param hashPassword - password with hash for new user
     * @param email        - email for new user
     */
    public void signUp(String username, String hashPassword, String email) {
        User user = new User().builder().username(username).hashPassword(hashPassword).email(email).role(Role.USER).state(State.ACTIVE).build();
        userRepository.save(user);
    }

    /**
     * Log out current user
     */
    public void signOut() {
        currentUser = null;
    }

    /**
     * Getting user`s book
     *
     * @return list with information about books
     */
    public List<BookDTO> showMyBooks() {
        List<BookDTO> list = new ArrayList<>();
        for (Book book : bookRepository.findBooksByUsers(currentUser)) {
            BookDTO bookDTO = BookConverter.converter(book);
            Optional<Integer> rate = userRepository.getMyRate(book.getId(), currentUser.getId());
            if (rate.isPresent()) {
                bookDTO.setRateByCurrentUser(rate.get());
            } else {
                bookDTO.setRateByCurrentUser(-1);
            }
            list.add(bookDTO);
        }
        return list;
    }

    /**
     * Getting info about all users
     *
     * @return list with info about users
     */
    public List<UserDTO> getUsers() {
        List<UserDTO> allUsers = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            allUsers.add(UserConverter.converter(user));
        }

        return allUsers;
    }

    /**
     * Get info about current user
     *
     * @return info about current user
     */
    public UserDTO getCurrentUser() {
        UserDTO userDTO = UserConverter.converter(currentUser);
        return userDTO;
    }

    /**
     * Change info about current user
     *
     * @param userDTO - updated info about user
     */
    @Transactional
    public void updateCurrentUser(UserDTO userDTO) {
        currentUser.setUsername(userDTO.getUsername());
        currentUser.setEmail(userDTO.getEmail());
        userRepository.update(userDTO.getEmail(), userDTO.getUsername(), userDTO.getId(),
                userDTO.getRole(), userDTO.getState());
    }

    /**
     * Check access level of current user
     *
     * @return role of current user
     */
    public Role authorization() {
        if (currentUser.getRole().equals(Role.ADMIN)) {
            return Role.ADMIN;
        } else {
            return Role.USER;
        }
    }

    @Transactional
    public boolean addMyBook(long id, int rate) {
        Optional<Book> findBook = bookRepository.findById(id);
        if (findBook.isPresent()) {
            Book book = findBook.get();
            userRepository.addMyBook(currentUser.getId(), book.getId(), rate);
            Optional<BigDecimal> avgRate = bookRepository.getAverageRate(book.getId());
            if (avgRate.isPresent()) {
                book.setAverageRate(avgRate.get().doubleValue());
                bookRepository.updateAverageRate(book.getId(), book.getAverageRate());
                return true;
            }
        } else return false;
        return false;
    }

    @Transactional
    public void updateMyBook(long id, int rate) {
        userRepository.updateMyBook(currentUser.getId(), id, rate);
        Optional<BigDecimal> avgRate = bookRepository.getAverageRate(id);
        avgRate.ifPresent(bigDecimal -> bookRepository.updateAverageRate(id, bigDecimal.doubleValue()));
    }

    @Transactional
    public boolean makeAdmin(long id) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            User user = findUser.get();
            if (user.getRole().equals(Role.ADMIN)) {
                return false;
            } else {
                user.setRole(Role.ADMIN);
                userRepository.update(user.getEmail(), user.getUsername(), user.getId(),
                        user.getRole().toString(), user.getState().toString());
                return true;
            }
        } else {
            return false;
        }
    }

    @Transactional
    public boolean changeState(long id, State state) {
        Optional<User> findUser = userRepository.findById(id);
        if (findUser.isPresent()) {
            User user = findUser.get();
            if (user.getState().equals(state)) {
                return false;
            } else {
                user.setState(state);
                userRepository.update(user.getEmail(), user.getUsername(),
                        user.getId(), user.getRole().toString(), user.getState().toString());
                return true;
            }
        }
        return false;
    }

}
