# AngryBirbs

AngryBirbs is an open-source clone of the popular game Angry Birds. The game is developed primarily using Java and LibGDX.

![Gameplay GIF](https://github.com/Celerium-Ce/AngryBirbs/blob/mergingbranch/Media/gifdemo.gif)

### Members

`Mohammad Umar` `2023324`

`Praveer Singh Chauhan` `2023397`

## Running the Project

### Using IntelliJ IDEA

1. Download and Open in IntelliJ IDEA.
2. Project from Existing Sources -> build.gradle file
3. Click on the **Gradle** tools in the right panel.

- **To clean**:
   - `AngryBirbs` -> `Tasks` -> `build` -> `clean`

- **To build**:
    - `AngryBirbs` -> `Running Configurations` -> `AP Project[build]`

- **To run**:
    - `AngryBirbs` -> `Running Configurations` -> `AP ProjectrRun]`
  
- **To run tests**:
    - `AngryBirbs` -> `Running Configurations` -> `AP Project[test]`

### Running from the Command Line

#### Linux

- **To clean**:
  ```bash
  sh gradlew clean
  ```

- **To build**:
  ```bash
  sh gradlew build
  ```

- **To run**:
  ```bash
  sh gradlew run
  ```

#### Windows

- **To clean**:
  ```bash
  .\gradlew.bat clean
  ```

- **To build**:
  ```bash
  .\gradlew.bat build
  ```

- **To run**:
  ```bash
  .\gradlew.bat run
  ```

## Features

- **Load/Save Game**: The game can be saved and loaded at any point.
- **Level Editor**: The game has a level editor that allows users to create custom levels needs to use [Tiled Map Editor](https://www.mapeditor.org/)
- **Abilities**: The game has different abilities for different birds.
  - **Red** : Normal bird
  - **Blue** : Splits into 3 birds
  - **Yellow** : Speeds up
- **Accurate Physics**: The game uses Box2D for physics simulation.

## Implementation of Theory

### OOps Concepts Used

Among others here are some of the OOPs concepts used in the project:
- **Inheritance**: The `Bird` class extends the `GameObject` class.
- **Polymorphism**: The `Bird` class overrides the `update` method from the `GameObject` class.
- **Encapsulation**: The `GameObject` class has private fields and public getter and setter methods.
- **Abstraction**: The `GameObject` class is an abstract class.
- **Interfaces**: The `Collidable` interface is implemented by the `GameObject` class.
- **Composition**: The `Game` class has a list of `GameObject` objects.
- **Aggregation**: The `Game` class has a `Level` object.
- **Association**: The `Game` class has a `Level` object.
- **Dependency**: The `Game` class depends on the `Level` class.

### Design Patterns Used

Among others here are some of the design patterns used in the project:


- **Singleton Pattern**: Ensures that only one instance of a class is created and provides a global point of access to it.
    - **Class**: `Game`

- **Composite Pattern**: Composes objects into tree structures to represent part-whole hierarchies. Composite lets clients treat individual objects and compositions of objects uniformly.
    - **Class**: `Level`

- **Command Pattern**: Encapsulates a request as an object, thereby allowing for parameterization of clients with queues, requests, and operations.
    - **Class**: `Slingshot`, `SlingshotInput`

- **Template Method Pattern**: Defines the skeleton of an algorithm in a method, deferring some steps to subclasses.
    - **Class**: `Bird`, `Pig`, `Material`

- **Mediator Pattern**: Defines an object that encapsulates how a set of objects interact.
    - **Class**: `Menu`

- **Chain of Responsibility Pattern**: Passes a request along a chain of handlers.
    - **Class**: `GameContactListener`

## Testing

The project uses JUnit for testing. The tests are located in the `core/test` directory.
### Tests

#### BirdTest

- **Initialization**: Tests the initialization of the `Bird` class.
- **Toggle Physics**: Tests toggling the physics of the bird.
- **Set Dead**: Tests setting the bird to dead.
- **Set Position**: Tests setting the position of the bird.
- **Get Velocity**: Tests getting the velocity of the bird.
- **Dispose**: Tests disposing of the bird.

#### PigTest

- **Initialization**: Tests the initialization of the `Pig` class.
- **Toggle Physics**: Tests toggling the physics of the pig.
- **Set Dead**: Tests setting the pig to dead.
- **Set Position**: Tests setting the position of the pig.
- **Get Velocity**: Tests getting the velocity of the pig.
- **Dispose**: Tests disposing of the pig.
- **Take Damage**: Tests taking damage by the pig.

#### MaterialTest

- **Initialization**: Tests the initialization of the `Material` class.
- **Set Position**: Tests setting the position of the material.
- **Get Velocity**: Tests getting the velocity of the material.
- **Dispose**: Tests disposing of the material.
- **Take Damage**: Tests taking damage by the material.


## Online Resources

- [Geeks for Geeks](https://www.geeksforgeeks.org/)
- [Stack Overflow](https://stackoverflow.com/)

## Repository

- [https://github.com/Celerium-Ce/AngryBirbs/tree/mergingbranch](https://github.com/Celerium-Ce/AngryBirbs/tree/mergingbranch) ( mergingbranch branch has)



To pause press esc, to see game end screen shoot the birds and destroy the blocks and pig
