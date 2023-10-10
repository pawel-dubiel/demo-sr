package com.paweldubiel.demo.scoreboard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class ScoreBoard {

  private final SoccerRepository repository;
  private final AtomicLong gameCounter = new AtomicLong(0);

  /**
   * start game
   *
   * @throws  GameAlreadyExistsException throw
   *
   * @param homeTeam
   * @param awayTeam
   */
  public void startGame(final String homeTeam, final String awayTeam) {


    if (repository.containsGame(homeTeam, awayTeam)) {
      throw new GameAlreadyExistsException("Start Game: Game already exists");
    }

    repository.save(new Game(homeTeam, awayTeam, 0, 0, gameCounter.getAndIncrement()));
  }

  public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore) {

    Game oldGame = repository.findByHomeTeamAndAwayTeam(homeTeam, awayTeam).orElseThrow(
            ()-> new GameDoesNotExistException("Update Score: Game does not exist")
    );

    Game newGame = new Game(homeTeam, awayTeam, homeScore, awayScore, oldGame.order());
    repository.save(newGame);
  }

  public void finishGame(String homeTeam, String awayTeam) {

    if (!repository.containsGame(homeTeam, awayTeam)) {
      throw new GameDoesNotExistException("Finish Game: Game does not exist");
    }
    repository.delete(homeTeam, awayTeam);
  }

  public List<String> getSummary() {
    return repository.findAll().stream()
        .sorted(gameComparator())
        .map(
            game ->
                String.format(
                    "%s %d - %s %d",
                    game.homeTeam(), game.homeScore(), game.awayTeam(), game.awayScore()))
        .collect(Collectors.toList());
  }

  private static Comparator<Game> gameComparator() {
    return Comparator.comparingInt(Game::gameTotalScore).thenComparing(Game::order).reversed();
  }

  public String getScore(final String team) {

    List<Game> games =repository.findAll().stream().filter(currentGame-> currentGame.homeTeam().equals(team) || currentGame.awayTeam().equals(team)).toList();

    Game game = games.get(0);
    if (game.homeTeam().equals(team) ) {
      return String.valueOf(game.homeScore());
    } else if (game.awayTeam().equals(team)) {
      return String.valueOf(game.awayScore());
    } else {
      return "-1";
    }

  }
}
