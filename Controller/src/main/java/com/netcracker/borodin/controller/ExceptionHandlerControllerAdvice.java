package com.netcracker.borodin.controller;

import com.netcracker.borodin.dto.ExceptionResponse;
import com.netcracker.borodin.exception.NotYourCommentException;
import com.netcracker.borodin.exception.ResourceAlreadyExistException;
import com.netcracker.borodin.exception.ResourceNotFoundException;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
  @ExceptionHandler(ResourceNotFoundException.class)
  @ResponseStatus(value = HttpStatus.NOT_FOUND)
  public @ResponseBody ExceptionResponse handleResourceNotFound(
      final ResourceNotFoundException exception, final HttpServletRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setErrorMessage(exception.getMessage());
    error.callerURL(request.getRequestURI());

    return error;
  }

  @ExceptionHandler(ResourceAlreadyExistException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ExceptionResponse handleResourceAlreadyExist(
      final ResourceAlreadyExistException exception, final HttpServletRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setErrorMessage(exception.getMessage());
    error.callerURL(request.getRequestURI());

    return error;
  }

  @ExceptionHandler(NotYourCommentException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ExceptionResponse handleNotYourComment(
      final NotYourCommentException exception, final HttpServletRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setErrorMessage(exception.getMessage());
    error.callerURL(request.getRequestURI());

    return error;
  }

  @ExceptionHandler(JDBCConnectionException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ExceptionResponse handleHaveNotConnection(
      final JDBCConnectionException exception, final HttpServletRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setErrorMessage("We lost connection with database, sorry!");
    error.callerURL(request.getRequestURI());

    return error;
  }

  @ExceptionHandler(NumberFormatException.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ExceptionResponse handleNumberFormatException(
      final NumberFormatException exception, final HttpServletRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setErrorMessage("Incorrect input!");
    error.callerURL(request.getRequestURI());

    return error;
  }

  @ExceptionHandler(Exception.class)
  @ResponseStatus(value = HttpStatus.BAD_REQUEST)
  public @ResponseBody ExceptionResponse handleException(
          final Exception exception, final HttpServletRequest request) {

    ExceptionResponse error = new ExceptionResponse();
    error.setErrorMessage(exception.getMessage());
    error.callerURL(request.getRequestURI());

    return error;
  }
}
