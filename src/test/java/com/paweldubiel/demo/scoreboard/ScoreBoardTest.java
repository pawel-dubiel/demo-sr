package com.paweldubiel.demo.scoreboard;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ScoreBoardTest {

    private ScoreBoard scoreBoard;

    @BeforeEach
    void setUp() {
        scoreBoard = new ScoreBoard();
    }

    @Test
    @DisplayName("Should start a new game with 0-0")
    void shouldStartNewGame() {
        // when
       scoreBoard.startGame("Germany", "France");

        // then
        List<String> expectedSummary = List.of(
                "Germany 0 - France 0"
        );
        assertEquals(expectedSummary,  scoreBoard.getSummary());
    }

    @Test
    @DisplayName("Should not start a game if it already exists")
    void shouldNotStartGameIfAlreadyExists() {
        // given
        scoreBoard.startGame("Germany", "France");

        // when / then
        assertThrows(GameAlreadyExistsException.class, () -> scoreBoard.startGame("Germany", "France"));
    }

    @Test
    @DisplayName("Should finish a game")
    void shouldFinishGame() {
        // given
        scoreBoard.startGame("Germany", "France");

        // when
       scoreBoard.finishGame("Germany", "France");

        // then
        assertEquals(Collections.EMPTY_LIST,  scoreBoard.getSummary());
    }

    @Test
    @DisplayName("Should not finish a game that doesn't exist. And should throw an exception")
    void shouldNotFinishGameThatDoesNotExist() {
        // when / then
        assertThrows(GameDoesNotExistException.class, () -> scoreBoard.finishGame("Germany", "France"));
    }

    @Test
    @DisplayName("Should update the score of an existing game")
    void shouldUpdateScore() {
        // given
        scoreBoard.startGame("Germany", "France");

        // when
        scoreBoard.updateScore("Germany", "France", 2, 2);

        // then
        List<String> expectedSummary = List.of(
                "Germany 2 - France 2"
        );
        assertEquals(expectedSummary,  scoreBoard.getSummary());
    }

    @Test
    @DisplayName("Should not update the score of a non-existing game. And should throw an exception")
    void shouldNotUpdateScoreOfNonExistingGame() {
        // when / then
        assertThrows(GameDoesNotExistException.class, () -> scoreBoard.updateScore("Germany", "France", 2, 2));
    }

    @Test
    @DisplayName("Should get a summary sorted by total score and most recently added")
    void shouldGetSummary() {
        // given
        scoreBoard.startGame("Mexico", "Canada");
        scoreBoard.startGame("Spain", "Brazil");
        scoreBoard.startGame("Germany", "France");
        scoreBoard.startGame("Uruguay", "Italy");
        scoreBoard.startGame("Argentina", "Australia");

        // Update the scores
        scoreBoard.updateScore("Argentina", "Australia", 3, 1);
        scoreBoard.updateScore("Mexico", "Canada", 0, 5);
        scoreBoard.updateScore("Spain", "Brazil", 10, 2);
        scoreBoard.updateScore("Germany", "France", 2, 2);
        scoreBoard.updateScore("Uruguay", "Italy", 6, 6);

        // Expected summary, sorted by total score and most recently added
        List<String> expectedSummary = List.of(
                "Uruguay 6 - Italy 6", //total  12 // most recently added
                "Spain 10 - Brazil 2", // total 12
                "Mexico 0 - Canada 5", // total 5
                "Argentina 3 - Australia 1", // total 4 // most recently added
                "Germany 2 - France 2" // total 4
        );

        // when
        List<String> actualSummary = scoreBoard.getSummary();

        // then
        assertEquals(expectedSummary, actualSummary);
    }
}
