package com.example.festquestbackend.exceptions.runtime;

public class QuestNotFoundException extends CustomRuntimeException {
  public QuestNotFoundException(Long id) {
    super("Quest with ID " + id + " not found.");
  }
}
