package breakout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Level {
    
    private Scene scene;
    private Group root;
    private Paddle paddle;
    private List<Bouncer> bouncers;
    private List<Block> blocks;
    private List<Powerup> powerups;
    private int points;
    private int pointMultiple;
    private PauseTransition timeDoublePoint;
    
    private Level() {}
    
    public Scene getScene() {
        return scene;
    }
    
    public Paddle getPaddle() {
        return paddle;
    }
    
    public List<Bouncer> getBouncers() {
        return bouncers;
    }
    
    public List<Block> getBlocks() {
        return blocks;
    }
    
    public int getPoints() {
        return points;
    }

    public void step(double secondDelay) {
        List<Bouncer> bouncerToRemove = new ArrayList<Bouncer>();
        for(Bouncer bouncer: bouncers) {
            bouncer.move(secondDelay, this);
            if(bouncer.out(scene.getWidth(), scene.getHeight())) {
                bouncerToRemove.add(bouncer);
            }
        }
        bouncers.removeAll(bouncerToRemove);
        root.getChildren().removeAll(bouncerToRemove);
        
        List<Block> blockToRemove = new ArrayList<Block>();
        for(Block block: blocks) {
            if(block.isDestroyed()) {
                blockToRemove.add(block);
                points += block.getPoint() * pointMultiple;
                if(block instanceof PowerBlock) {
                    addPowerup(block);
                }
            }
        }
        blocks.removeAll(blockToRemove);
        root.getChildren().removeAll(blockToRemove);
        
        List<Powerup> powerupToRemove = new ArrayList<Powerup>();
        for(Powerup powerup: powerups) {
            powerup.move(secondDelay);
            if(powerup.getBoundsInLocal().intersects(paddle.getBoundsInLocal())) {
               powerup.power(this);
               powerupToRemove.add(powerup);
            }
            else if(powerup.out(scene.getHeight())) {
                powerupToRemove.add(powerup);
            }
        }
        powerups.removeAll(powerupToRemove);
        root.getChildren().removeAll(powerupToRemove);
    }
    
    public void addBouncerAtBouncer() {
        Bouncer bouncer = bouncers.get(0);
        Bouncer newBouncer = new Bouncer(bouncer.getX(), bouncer.getY());
        bouncers.add(newBouncer);
        root.getChildren().add(newBouncer);
    }
    
    private void addPowerup(Block block) {
        Powerup powerup = new Powerup();
        powerup.setX(block.getX() + (block.getBoundsInLocal().getWidth()
                - powerup.getBoundsInLocal().getWidth()) / 2);
        powerup.setY(block.getY());
        powerups.add(powerup);
        root.getChildren().add(powerup);
    }
    
    public void doublePoint() {
        pointMultiple = 2;
        timeDoublePoint.playFromStart();
    }
    
    public void handleKeyInput(KeyCode code) {
        if(!paddle.keyInputHandler(code)) {
            if(code == KeyCode.A) {
                addBouncerAtBouncer();
            }
        }
    }

    /**
     * Modifier. Setup the scene for the level specified and return the Level object
     * @param scene
     * @param root
     * @return
     */
    public static Level initializeLevel(Scene scene, Group root, int points, int levelNum) {
        Level level = new Level();
        level.scene = scene;
        level.root = root;
        double sceneWidth = scene.getWidth();
        double sceneHeight = scene.getHeight();
        level.paddle = initializePaddle(root, sceneWidth, sceneHeight);
        level.bouncers = new ArrayList<Bouncer>();
        addBouncerOnPaddle(level);
        level.powerups = new ArrayList<Powerup>();
        level.points = points;
        level.pointMultiple = 1;
        level.timeDoublePoint = new PauseTransition(Duration.seconds(Powerup.DURATION));
        level.timeDoublePoint.setOnFinished(e -> level.pointMultiple = 1);
        if(levelNum == 1) {
            level.blocks = initializeBlocksLevelOne(root, sceneWidth);
        }
        else if(levelNum == 2) {
            level.blocks = initializeBlocksLevelOne(root, sceneWidth);
        }
        else if(levelNum == 3) {
            level.blocks = initializeBlocksLevelOne(root, sceneWidth);
        }
        else {
            level.blocks = initializeBlocksLevelOne(root, sceneWidth);
        }
        return level;
    }
    
    private static Paddle initializePaddle(Group root, double sceneWidth, double sceneHeight) {
        Paddle paddle = new Paddle();
        paddle.setFitWidth(sceneWidth / 5);
        paddle.setX(sceneWidth / 2 - paddle.getBoundsInLocal().getWidth() / 2);
        paddle.setY(sceneHeight - paddle.getBoundsInLocal().getHeight());
        root.getChildren().add(paddle);
        return paddle;
    }

    private static void addBouncerOnPaddle(Level level) {
        Bouncer bouncer = new Bouncer();
        bouncer.setX(level.paddle.getX() + level.paddle.getBoundsInLocal().getWidth() / 2
                - bouncer.getBoundsInLocal().getWidth() / 2);
        bouncer.setY(level.paddle.getY() - bouncer.getBoundsInLocal().getHeight());
        level.bouncers.add(bouncer);
        level.paddle.addBouncer(bouncer);
        level.root.getChildren().add(bouncer);
    }

    private static List<Block> initializeBlocksLevelOne(Group root, double sceneWidth) {
        List<Block> newBlocks = new ArrayList<Block>();
        double width = sceneWidth / 6;
        double height = width / 3;
        for(int layer = 0; layer < 5; layer++) {
            for(int i = 0; i < 10; i++) {
                Block block = new PowerBlock();
                block.setFitWidth(width);
                block.setFitHeight(height);
                block.setX(width * i);
                block.setY(height * layer * 5 / 4);
                newBlocks.add(block);
                root.getChildren().add(block);
            }
        }
        return newBlocks;
    }
}
