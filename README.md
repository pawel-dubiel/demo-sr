# ScoreBoard Application

## Overview

The ScoreBoard application is a simple Demo Java project designed to track ongoing games, their scores, and display a summary sorted by specific criteria. 

## Features

- **Start a new game**: Begin tracking a new game with team names.
- **Finish an existing game**: End and remove a game from the tracking.
- **Update the score of an ongoing game**: Modify the scores of the teams in a game.
- **Generate a sorted summary of games**: Output a sorted list of ongoing games based on their scores and time of creation.

## Decisions and considerations 

For Simplicity I decided to use counter instead of Timestamps

### Simplicity
Using a counter is straightforward and doesn't require any special libraries or methods to manipulate time.

### Testing
In automated testing scenarios, multiple games might be created at the same nanosecond, leading to flaky tests that can't be relied upon. By using a counter, we can more precisely control the order of the games without dealing with time-related issues, and manual clock advancing in tests

### Code Maintenance
Less code related to time manipulation means the codebase remains lean and easy to understand, making it easier to maintain and expand upon in the future.

### Observer Pattern
One design pattern considered for further decoupling the `Game` classes from the `ScoreBoard` was the Observer Pattern. This would allow the `ScoreBoard` to "observe" changes in individual games. While it adds another layer of abstraction and could be useful in more complex applications, it was deemed unnecessary for this specific project's requirements.

### Time Complexity 

We can also consider TreeMap if the number of games is huge.

## Potential Improvements

The current implementation sorts the games every time getSummary is called. While this works for a small number of games, it might be inefficient when dealing with larger datasets. A couple of strategies could be employed to optimize this:

Pre-Sorted State: Keep a separate, pre-sorted list of games. Only update this list when there's a change in the game states, i.e., when a new game starts, when a score updates, or when a game ends.

Change Flagging: Use a boolean flag (isChanged) to track if any game-related change occurs. Re-sort and update the sorted list only when this flag is true.

## How to Test

1. **Clone the Repository**:
    ```bash
    git clone git@github.com:pawel-dubiel/demo-sr.git
    ```
   
2. **Navigate to the Project Directory if you haven't already**:
    ```bash
    cd demo-sr
    ```

3. **Run the Test Suite**:
    ```bash
    ./gradlew test
    ```