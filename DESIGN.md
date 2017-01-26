# Breakout Design
Mike Liu (zl99)

## High Level Design Goals
Implement a working version of the game Breakout in such a way that the objects in the game are divided into several
broad categories. Each category is implemented by one or more classes representing the game objects belonging to it.
Each class implements behaviors specific to the game object it represents, and the game objects are contained and regulated
by an overarching class that represent a scene/level of the game. The game is run mainly by letting a main class set up
the game loop and interact with the level class, supplemented by some interaction with other classes that provide
non-game-playing functionalities such as the splash screen.

## How to add new features
### Add a new level
In the Level.initializeLevel() method, add a new if statement containing the configuration of the new level. Can possibly write
a helper method for the configuration.

### Add a new power-up
Best way is to extend the Powerup class and override the original power() method.

### Add a new type of block
Extend the Block class and implement the public hit() method. Specify the total hit, reward points and image by passing
parameters to the super constructor.  
If the new type of Block drops powerup in some way, the Level.step() method needs to be modified.

### Add a new paddle ability
Add a new if statement in Paddle.keyPressedHandler() that specifies the new ability.

### Add more key functions
Add key input handler to the class that the function should be triggered on and call the handler from the corresponding hander
in Main.

## Major Design Choices
### Extract GameStatus class from Level
Significantly simplifies the Level class as it can only keep a GameStatus object instead of keeping all the status such as
point and life as instance variables.  
No obvious drawback.

### The way to initialize a level
My implementation initializes blocks in a level depending on parameter passed to level.initializeLevel() instead of creating
subclasses of of Level each having its own implementation of initializeLevel(). In this way the level object can be reused,
and the consecutive if statements only appear in one place so there is not duplicated
code.  
Cons: Making Level abstract and creating a subclass for each level may make the code look more succinct.

### Make Block class abstract and create a subclass for each type of block
Gives each type of block a understandable name instead of just different values for instance variables. Also makes it a lot
easier to add new types of blocks such as the BarrierBlock.

### The way to initialize powerups
My implementation initializes four most basic types of powerups by assigning a type at random at construction time and decide
their function at runtime depending on their type. This is because these powerups were meant to be generated in random types
when I designed the game, and this implementation also preserves the possibility of adding new types of powerups by simply
extending the Powerup class and override power().
Cons: I could have made each type of powerup a subclass of Powerup and randomly choose one when needed, which will make the code
look cleaner and allow other developers to use each type of powerup directly. I might have actually made a bad decision for
this implementation.
