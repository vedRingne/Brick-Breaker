# Brick Breaker Game

A fun and customizable **Brick Breaker** game built using Java Swing. Break all the bricks by bouncing the ball off the paddle while avoiding losing the ball.

---

## Features
- **Three difficulty levels**: Easy, Medium, and Hard.
- Dynamic brick layouts that vary based on difficulty.
- Randomly colored bricks for vibrant gameplay.
- Paddle and ball mechanics with smooth collision detection.
- Scoring system to track your progress.
- Game-over and level-up mechanics.

---

## Gameplay
1. **Choose a difficulty**: Select Easy, Medium, or Hard at the start of the game.
2. **Controls**:
   - Move paddle: `Left Arrow` and `Right Arrow`.
   - The ball bounces off the paddle and breaks bricks.
3. **Objective**:
   - Clear all bricks to level up.
   - Avoid letting the ball fall below the paddle.
   - The game ends when the ball drops below the screen.

---

## Installation and Running the Game
1. **Clone the repository**:
    ```bash
    git clone https://github.com/vedRingne/Brick-Breaker.git
    cd brick-breaker
    ```

2. **Compile the code**:
    Ensure you have the [Java Development Kit (JDK)](https://www.oracle.com/java/technologies/javase-downloads.html) installed.
    ```bash
    javac Main.java
    ```

3. **Run the game**:
    ```bash
    java Main
    ```

---

## Code Overview
- **Main.java**:
  - Implements the game logic, rendering, and collision detection.
  - Uses `javax.swing` for graphics and game controls.
  - Difficulty settings adjust ball speed and brick layout.

- **Dynamic Elements**:
  - Bricks are dynamically created and colored.
  - Difficulty affects the number of rows and speed of the ball.

---

## Customize the Game
The game is easily modifiable for personal tweaks:
- **Brick Patterns**:
  - Adjust the `createPattern` method to create unique brick layouts.
- **Difficulty Levels**:
  - Modify `ballDX`, `ballDY`, and `maxRows` in the `initializeGame` method to change difficulty behavior.
- **Graphics**:
  - Change colors, paddle size, or ball speed for a personalized feel.

---

## Technologies Used
- **Programming Language**: Java
- **Graphics Library**: Swing

---

## Future Enhancements (Ideas)
- Add power-ups (e.g., multi-ball, paddle size changes).
- Introduce a timer or time-based challenges.
- Create a main menu for settings and high scores.

---

## License
This project is open-source and available under the MIT License.

---

## Author
Developed by **[Ved Ringne](https://github.com/vedRingne)**.  
Feel free to reach out for feedback, ideas, or collaboration!

---

Enjoy the game! 🎮
