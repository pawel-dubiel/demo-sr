package com.paweldubiel.demo.scoreboard;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class GameTest {

  @Test
  @DisplayName("Should calculate total score correctly")
  public void testGameTotalScore() {
    // given
    Game game = new Game("TeamA", "TeamB", 3, 2, 1L);

    // when
    int totalScore = game.gameTotalScore();

    // then
    assertEquals(5, totalScore);
  }

  @Test
  @DisplayName("Should throw exception for negative scores")
  public void testNegativeScores() {
    // Assert
    assertThrows(IllegalArgumentException.class, () -> new Game("TeamA", "TeamB", -1, 2, 1L));
    assertThrows(IllegalArgumentException.class, () -> new Game("TeamA", "TeamB", 3, -1, 1L));
  }
}
