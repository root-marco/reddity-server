package com.slowed.reddity.exceptions;

public class SpringReddityException extends RuntimeException {

  public SpringReddityException(String exMessage, Exception exception) {
    super(exMessage, exception);
  }

  public SpringReddityException(String exMessage) {
    super(exMessage);
  }

}
