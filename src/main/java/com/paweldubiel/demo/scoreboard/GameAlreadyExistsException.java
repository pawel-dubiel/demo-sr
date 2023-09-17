package com.paweldubiel.demo.scoreboard;

class GameAlreadyExistsException extends RuntimeException {
  public GameAlreadyExistsException(String message) {
    super(message);
  }
}
