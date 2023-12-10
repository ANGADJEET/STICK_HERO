# Stick Hero Game

Welcome to the Stick Hero Game project! This JavaFX-based game incorporates essential object-oriented programming (OOP) concepts and design patterns to bring the classic Stick Hero gameplay to life.

## Table of Contents
- [Overview](#overview)
- [Features](#features)
- [How to Run](#how-to-run)
- [Testing](#testing)
- [Design Patterns](#design-patterns)
- [Key Concepts](#key-concepts)
- [Game Mechanics](#game-mechanics)
- [Contributors](#contributors)

## Overview
The Stick Hero Game is a JavaFX implementation that mimics the popular Stick Hero game. Players control a character navigating between platforms, extending a stick to bridge gaps, and collecting cherries for rewards. The game emphasizes OOP principles, design patterns, and various Java features.

## Features
- Platform traversal with stick extension
- Cherry collection for rewards
- Reviving feature using cherries
- Scoring system with a focus on high scores
- Save and load game progress
- User-friendly interface with graphics and animations

## How to Run
To run the Stick Hero Game, execute the `StickHeroApp` class. This will launch the game interface, allowing you to start a new game or load a saved one.

## Testing
For testing purposes, utilize the `ControllerTest` class. This class contains JUnit tests to ensure the functionality of key components in the Stick Hero Game.

## Design Patterns
The Stick Hero Game incorporates the following design patterns:
- **Observer:** Used for the scoring system to notify observers of score updates.
- **Factory:** Implicitly employed in the creation of game elements.
- **Singleton:** Applied to the `RandomCherryGenerator` class to ensure a single instance for cherry generation.
- **Iterator:** Implicitly used in iterating through pillars.

## Key Concepts
- **Encapsulation:** Protects the game state and ensures that interactions occur through well-defined interfaces.
- **Polymorphism:** Allows different objects (e.g., platforms, characters) to be manipulated using a common interface.
- **Abstract Class:** The `Player` class serves as an abstract base for different player states.
- **Inheritance:** The `ScoreObserver` interface is inherited to update score-related changes.

## Game Mechanics
1. Control stick-hero to traverse platforms.
2. Extend the stick to bridge gaps between platforms.
3. Collect cherries for rewards and scoring.
4. Use cherries to revive and continue the game.
5. Timing is crucial for successful stick extension.
6. Save and load game progress for convenience.

## Contributors
- [Your Name]
- [Teammate's Name]

Feel free to expand and enhance the Stick Hero Game according to your creative vision. Happy coding!
