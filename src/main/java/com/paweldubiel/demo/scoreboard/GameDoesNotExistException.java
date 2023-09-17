package com.paweldubiel.demo.scoreboard;

class GameDoesNotExistException extends RuntimeException {
  public GameDoesNotExistException(String message) {
    super(message);
  }
}
