package com.paweldubiel.demo.scoreboard;

public record Game(String homeTeam, String awayTeam, int homeScore, int awayScore, long order) {

  public Game {
    if (homeScore < 0 || awayScore < 0) {
      throw new IllegalArgumentException("Scores cannot be negative");
    }
  }

  public int gameTotalScore() {
    return homeScore + awayScore;
  }
}
