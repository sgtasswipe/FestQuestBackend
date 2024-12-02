package com.example.festquestbackend.exceptions.runtime;

public class InvalidQuestStateException extends CustomRuntimeException {
  public InvalidQuestStateException(String message) {
    super(message);
  }
}
