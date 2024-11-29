package com.example.festquestbackend.exceptions;

public class QuestNotFoundException extends CustomRuntimeExceptions {
  public QuestNotFoundException(Long id) {
    super("Quest with ID " + id + " not found.");
  }
}
