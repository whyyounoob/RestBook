package com.netcracker.borodin.exception;

public class ResourceAlreadyExistException extends RuntimeException {
  public ResourceAlreadyExistException() {
    super();
  }

  public ResourceAlreadyExistException(String message) {
    super(message);
  }
}
