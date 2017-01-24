package breakout;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.PauseTransition;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

/**
 * The class that is used to manage the game-playing scene. It holds all the objects in the scene,
 * manages their movement and remove them when they are "destroyed"
 * @author Mike Liu
 *
 */
public class Level {
    
    private Scene scene;
    private Group root;
    private GameStatus status;
    private Paddle paddle;
    private Rectangle hole;
    private List<Bouncer> bouncers;
    private List<Block> blocks;
    private List<Powerup> powerups;
    private int pointMultiple;
    private PauseTransition timeDoublePoint;
    
    public Level(Scene targetScene, Group targetRoot) {
        scene = targetScene;
        root = targetRoot;
        status = new GameStatus();
        bouncers = new ArrayList<Bouncer>();
        blocks = new ArrayList<Block>();
        powerups = new ArrayList<Powerup>();
        pointMultiple = 1;
        timeDoublePoint = new PauseTransition(Duration.seconds(Powerup.DURATION));
        timeDoublePoint.setOnFinished(e -> pointMultiple = 1);
    }
    
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
    
    public boolean hasHole() {
        return hole != null;
    }
    
    public Rectangle getHole() {
        return hole;
    }
    
    public GameStatus getStatus() {
        return status;
    }

    public void step(double secondDelay) {
        double paddleSpeed = paddle.getSpeed();
        double paddleX = paddle.getX();
        if(!(paddleSpeed < 0 && paddleX < 0 || paddleSpeed > 0 && paddleX + getWidth(paddle) > scene.getWidth())) {
            paddle.step(secondDelay);
        }
        
        List<Bouncer> bouncerToRemove = new ArrayList<Bouncer>();
        for(Bouncer bouncer: bouncers) {
            bouncer.move(secondDelay, this);
            if(bouncer.inHole()) {
                status.clear();
                root.getChildren().remove(bouncer);
            }
            else if(bouncer.isDead(scene.getHeight())) {
                bouncerToRemove.add(bouncer);
            }
        }
        bouncers.removeAll(bouncerToRemove);
        root.getChildren().removeAll(bouncerToRemove);
        if(bouncers.isEmpty()) {
            status.addLife(-1);
            if(status.getLife() <= 0) {
                status.lose();
            }
            reset();
        }
        
        List<Block> blockToRemove = new ArrayList<Block>();
        for(Block block: blocks) {
            if(block.isDestroyed()) {
                blockToRemove.add(block);
                status.addPoint(block.getPoint() * pointMultiple);
                if(block instanceof PowerBlock) {
                    addPowerup(block, new Powerup());
                }
            }
            else if(block instanceof SuperBlock) {
                ((SuperBlock)block).step(secondDelay, this);
            }
        }
        blocks.removeAll(blockToRemove);
        root.getChildren().removeAll(blockToRemove);
        if(status.getLevel() == 4 && blocks.isEmpty()) {
            status.clear();
        }
        
        List<Powerup> powerupToRemove = new ArrayList<Powerup>();
        for(Powerup powerup: powerups) {
            powerup.move(secondDelay);
            if(intersect(powerup, paddle)) {
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
    
    public void reset() {
        formatPaddle(paddle);
        root.getChildren().add(addBouncerOnPaddle());
    }
    
    /**
     * Modifier. Setup the scene for the level specified and return the Level object
     * @param scene
     * @param root
     * @return
     */
    public void initializeLevel(int levelNum) {
        clear();
        root.getChildren().add(initializePaddle());
        root.getChildren().add(addBouncerOnPaddle());
        status.setLevel(levelNum);
        root.getChildren().add(status.getStatusText(scene));
        List<Block> newBlocks;
        if(levelNum == 4) {
            newBlocks = initializeBlockLevelFour();
        } else {
            root.getChildren().add(initializeHole());
            if(levelNum == 2) {
                newBlocks = initializeBlockLevelTwo();
            }
            else if(levelNum == 3) {
                newBlocks = initializeBlockLevelThree();
            }
            else {
                newBlocks = layBlock(6, 5, 1);
            }
        }
        root.getChildren().addAll(newBlocks);
    }
    
    private void clear() {
        root.getChildren().clear();
        hole = null;
        bouncers.clear();
        blocks.clear();
        powerups.clear();
    }

    private Paddle initializePaddle() {
        paddle = new Paddle();
        formatPaddle(paddle);
        return paddle;
    }
    
    private void formatPaddle(Paddle paddle) {
        paddle.setFitWidth(scene.getWidth() / 5);
        paddle.setX(scene.getWidth() / 2 - getWidth(paddle) / 2);
        paddle.setY(scene.getHeight() - getHeight(paddle));
    }
    
    private Rectangle initializeHole() {
        hole = new Rectangle(scene.getWidth() * 2 / 5, 0, scene.getWidth() / 5, 2);
        hole.setFill(Color.YELLOW);
        return hole;
    }

    private Bouncer addBouncerOnPaddle() {
        Bouncer bouncer = new Bouncer();
        bouncer.setX(paddle.getX() + getWidth(paddle) / 2 - getWidth(bouncer) / 2);
        bouncer.setY(paddle.getY() - getHeight(bouncer));
        bouncers.add(bouncer);
        paddle.addBouncer(bouncer);
        return bouncer;
    }
    
    public void addBouncerAtBouncer() {
        if(!bouncers.isEmpty()) {
            Bouncer bouncer = bouncers.get(0);
            Bouncer newBouncer = new Bouncer(bouncer.getX(), bouncer.getY());
            bouncers.add(newBouncer);
            root.getChildren().add(newBouncer);
        }
    }
    
    private List<Block> layBlock(int numAcross, int numLayer, double topOffset) {
        double width = scene.getWidth() / numAcross;
        double height = width / 3.5;
        for(int layer = 0; layer < numLayer; layer++) {
            for(int i = 0; i < numAcross; i++) {
                Block block = getBlock();
                block.setFitWidth(width);
                block.setFitHeight(height);
                block.setX(width * i);
                block.setY(height * (layer * 1.2 + topOffset));
                blocks.add(block);
            }
        }
        return blocks;
    }
    
    private Block getBlock() {
        double flag = Math.random();
        if(flag <= .2) {
            return new PowerBlock();
        } else {
            return new PlainBlock();
        }
    }
    
    private List<Block> initializeBlockLevelTwo() {
        layBlock(7, 4, 1);
        double width = scene.getWidth() / 7;
        double height = width / 3.5;
        for(int i = 0; i < 4; i++) {
            Block block = new BarrierBlock();
            block.setFitWidth(width);
            block.setFitHeight(height);
            block.setX(width * i * 2);
            block.setY(height * 8);
            blocks.add(block);
        }
        return blocks;
    }
    
    private List<Block> initializeBlockLevelThree() {
        layBlock(7, 4, 1);
        double width = scene.getWidth() / 7;
        double height = width / 3.5;
        for(int i = 0; i < 7; i++) {
            Block block;
            if(i % 2 == 0) {
                block = new PowerBlock();
            } else {
                block = new BarrierBlock();
            }
            block.setFitWidth(width);
            block.setFitHeight(height);
            block.setX(width * i);
            block.setY(height * 5.8);
            blocks.add(block);
        }
        return blocks;
    }
    
    private List<Block> initializeBlockLevelFour() {
        double width = scene.getWidth() / 3;
        double height = width / 3.5;
        Block block = new SuperBlock(this);
        block.setFitWidth(width);
        block.setFitHeight(height);
        block.setX((scene.getWidth() - getWidth(block)) / 2);
        block.setY(scene.getHeight() / 4);
        blocks.add(block);
        return blocks;
    }
    
    public void addPowerup(Block block, Powerup powerup) {
        powerup.setX(block.getX() + (getWidth(block)
                - getWidth(powerup)) / 2);
        powerup.setY(block.getY());
        powerups.add(powerup);
        root.getChildren().add(powerup);
    }
    
    public void doublePoint() {
        pointMultiple = 2;
        timeDoublePoint.playFromStart();
    }
    
    public void handleKeyPressed(KeyCode code) {
        if(!paddle.keyPressedHandler(code)) {
            if(code == KeyCode.A) {
                status.addLife(1);
            }
            else if(code == KeyCode.C) {
                for(Block block: blocks) {
                    status.addPoint(block.getPoint());
                }
                root.getChildren().removeAll(blocks);
                blocks.clear();
            }
        }
    }

    private double getWidth(Node n) {
        return n.getBoundsInLocal().getWidth();
    }
    
    private double getHeight(Node n) {
        return n.getBoundsInLocal().getHeight();
    }
    
    public static boolean intersect(Node a, Node b) {
        return a.getBoundsInLocal().intersects(b.getBoundsInLocal());
    }
}
