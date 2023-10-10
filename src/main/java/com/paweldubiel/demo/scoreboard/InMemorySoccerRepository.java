package com.paweldubiel.demo.scoreboard;

import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class InMemorySoccerRepository implements SoccerRepository {

    private final Map<String, Game> games = Collections.synchronizedMap(new HashMap<>());
    @Override
    public boolean containsGame(final String homeTeam, final String awayTeam) {
        return games.containsKey(buildKey(homeTeam, awayTeam));
    }

    @Override
    public void save(Game game) {
        games.put(buildKey(game.homeTeam(), game.awayTeam()), game);
    }

    @Override
    public Optional<Game> findByHomeTeamAndAwayTeam(String homeTeam, String awayTeam) {
        return Optional.ofNullable(games.get(buildKey(homeTeam, awayTeam)));
    }

    @Override
    public void delete(String homeTeam, String awayTeam) {
        games.remove(buildKey(homeTeam, awayTeam));
    }

    @Override
    public List<Game> findAll() {
       return games.values().stream().toList();
    }

    private static String buildKey(String homeTeam, String awayTeam) {
        return String.format("%s-%s", homeTeam, awayTeam);
    }
}
