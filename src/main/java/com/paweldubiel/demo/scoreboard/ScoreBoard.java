package com.paweldubiel.demo.scoreboard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

class ScoreBoard {

  private final Map<String, Game> games = new HashMap<>();
  private final AtomicLong gameCounter = new AtomicLong(0);

  public void startGame(String homeTeam, String awayTeam) {
    String key = buildKey(homeTeam, awayTeam);

    if (games.containsKey(key)) {
      throw new GameAlreadyExistsException("Start Game: Game already exists");
    }

    games.put(key, new Game(homeTeam, awayTeam, 0, 0, gameCounter.getAndIncrement()));
  }

  public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {
    String key = buildKey(homeTeam, awayTeam);

    if (!games.containsKey(key)) {
      throw new GameDoesNotExistException("Update Score: Game does not exist");
    }

    Game oldGame = games.get(key);
    Game newGame = new Game(homeTeam, awayTeam, homeScore, awayScore, oldGame.order());
    games.put(key, newGame);
  }

  public void finishGame(String homeTeam, String awayTeam) {
    String key = buildKey(homeTeam, awayTeam);

    if (!games.containsKey(key)) {
      throw new GameDoesNotExistException("Finish Game: Game does not exist");
    }

    games.remove(key);
  }

  public List<String> getSummary() {
    return games.values().stream()
        .sorted(gameComparator())
        .map(
            game ->
                String.format(
                    "%s %d - %s %d",
                    game.homeTeam(), game.homeScore(), game.awayTeam(), game.awayScore()))
        .collect(Collectors.toList());
  }

  private Comparator<Game> gameComparator() {
    return Comparator.comparingInt(Game::gameTotalScore).thenComparing(Game::order).reversed();
  }

  private String buildKey(String homeTeam, String awayTeam) {
    return String.format("%s-%s", homeTeam, awayTeam);
  }
}
