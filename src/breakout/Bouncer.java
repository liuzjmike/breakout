package breakout;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class Bouncer extends GameObject {

    public static final String IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 400;
    
    private boolean active, cannon;
    private double speedX, speedY;
    
    public Bouncer() {
        super(IMAGE);
        active = true;
    }
    
    public Bouncer(double x, double y) {
        this();
        setX(x);
        setY(y);
        launch(Math.PI * Math.random());
    }
    
    public double getSpeedX() {
        return speedX;
    }
    
    public double getSpeedY() {
        return speedY;
    }
    
    public void reverseSpeedX() {
        speedX = -speedX;
    }
    
    public void reverseSpeedY() {
        speedY = -speedY;
    }
    
    public boolean isCannon() {
        return cannon;
    }
    
    public void fire() {
        cannon = true;
    }
    
    public void recover() {
        cannon = false;
    }

    public void launch(double angle) {
        speedX = Math.cos(angle) * BOUNCER_SPEED;
        speedY = - Math.sin(angle) * BOUNCER_SPEED;
    }
    
    public void stop() {
        speedX = speedY = 0;
    }
    
    public void move(double secondDelay, Level level) {
        if(level.hasHole()) {
            handleHole(level.getHole());
        }
        if(active) {
            handleBoundary(level.getScene());
            handlePaddle(level.getPaddle());
            handleBlocks(level.getBlocks());
        }
        setX(getX() + speedX * secondDelay);
        setY(getY() + speedY * secondDelay);
    }
    
    private void handleHole(Rectangle hole) {
        if(getY() <= 0 && getX() >= hole.getX()
                && getX() <= hole.getX() + hole.getBoundsInLocal().getWidth()) {
            active = false;
        }
    }
    
    private void handleBoundary(Scene scene) {
        if(getX() <= 0 || getX() >= scene.getWidth() - getBoundsInLocal().getWidth()) {
            speedX = -speedX;
        }
        if(getY() <= 0) {
            speedY = -speedY;
        }
    }

    private void handlePaddle(Paddle paddle) {
        if(Level.intersect(this, paddle)) {
            paddle.bounceBack(this);
        }
    }

    private void handleBlocks(List<Block> blocks) {
        for(Block block: blocks) {
            if(Level.intersect(this, block)) {
                block.hit(this);
            }
        }
    }

    public boolean isDead(double sceneHeight) {
        return getY() >= sceneHeight;
    }
    
    public boolean inHole() {
        return !active && getY() < -getBoundsInLocal().getHeight();
    }
}
