package com.paweldubiel.demo.scoreboard;

import java.util.List;
import java.util.Optional;

public interface SoccerRepository {

    boolean containsGame(String homeTeam, String awayTeam);

    void save(Game game);

    Optional<Game> findByHomeTeamAndAwayTeam(String homeTeam, String awayTeam);

    void delete(final String homeTeam, final String awayTeam);
    List<Game> findAll();

}
