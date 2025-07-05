# Hearthstone-like Card Game (Java)

This project implements a simplified, two-player, turn-based card game in Java, inspired by Hearthstone. It demonstrates object-oriented design, rule enforcement, and core mechanics such as minions, mana, and hero abilities.

---

## Features

- Two-player turn-based gameplay
- Hero classes with distinct powers (Mage, Hunter)
- Minions with mechanics like Taunt and Charge
- Turn-based mana refresh and scaling
- Game rule enforcement via action validation
- Custom exception handling

---

## Project Structure

```plaintext
src/
├── engine/
│   ├── ActionValidator.java    # Validates in-game actions
│   ├── Game.java               # Core game logic and state management
│   ├── GameListener.java       # Listener for game events (e.g., game over)
│   └── Main.java               # Entry point of the application
├── model/
│   ├── heroes/                 # Hero implementations
│   └── cards/                  # Minion and card classes
└── exceptions/                 # Custom exception classes
```

---

## How to Compile and Run

```text
1. Import the project into IntelliJ IDEA, Eclipse, or another Java IDE.
2. Ensure the source folders are recognized properly.
3. Set `engine.Main` as the main class.
4. Run the program using the IDE's run configuration.
```

---

## Game Rules

```text
- Players alternate turns.
- Each turn:
  - Gain 1 maximum mana (up to 10).
  - Refresh all mana crystals.
  - Draw one card (if deck is not empty).
- Minions with Taunt must be attacked first.
- Minions can attack only if:
  - They are not sleeping, or
  - They have the Charge keyword.
- The game ends when a hero's HP reaches 0.
```

---

## Example Usage

```java
Hero player1 = new Hunter();
Hero player2 = new Mage();
Game game = new Game(player1, player2);

// Turn simulation
game.endTurn();                       // Switch to next player
player1.playMinion(someMinion);      // Play a minion
someMinion.attack(opponentMinion);   // Perform an attack
```

---

## Customization

```text
- Add new hero classes by extending the Hero base class.
- Define new minions by subclassing Minion.
- Modify game rules or turn logic in Game.java.
```

---

## Troubleshooting

```text
Issue: ClassNotFoundException  
Solution: Check that all Java files are compiled and the package structure is correct.

Issue: Unhandled exception at runtime  
Solution: Wrap method calls in appropriate try-catch blocks, especially in Main.java.

Issue: Invalid attack action  
Solution: Ensure minion state and rules (e.g., Taunt, sleep) are respected.

Issue: Game doesn’t end as expected  
Solution: Verify GameListener and onGameOver triggers.
```

---


