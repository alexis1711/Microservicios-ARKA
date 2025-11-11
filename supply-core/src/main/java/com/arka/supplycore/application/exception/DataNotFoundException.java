package com.arka.supplycore.application.exception;

import java.io.Serial;

public class DataNotFoundException extends Exception {

  @Serial
  private static final long serialVersionUID = 4193115072184744449L;

  public DataNotFoundException() {
    super("Can't find the requested data");
  }

  public DataNotFoundException(String message) {
    super(message);
  }
}
