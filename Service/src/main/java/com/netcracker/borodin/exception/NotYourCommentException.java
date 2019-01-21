package com.netcracker.borodin.exception;

public class NotYourCommentException extends RuntimeException {
  public NotYourCommentException() {
    super();
  }

  public NotYourCommentException(String message) {
    super(message);
  }
}
