package breakout;

import java.util.List;

import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;

public class Bouncer extends GameObject {

    public static final String IMAGE = "ball.gif";
    public static final int BOUNCER_SPEED = 375;
    
    private boolean active, cannon;
    private double speedX, speedY;
    
    public Bouncer() {
        super(IMAGE);
        active = true;
    }
    
    public Bouncer(double x, double y) {
        this();
        double angle = Math.PI * Math.random();
        speedX = Math.cos(angle) * BOUNCER_SPEED;
        speedY = - Math.sin(angle) * BOUNCER_SPEED;
        setX(x);
        setY(y);
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

    public void launch() {
        double angle = Math.PI / 2 * (Math.random() + 0.5);
        speedX = Math.cos(angle) * BOUNCER_SPEED;
        speedY = - Math.sin(angle) * BOUNCER_SPEED;
    }
    
    public void move(double secondDelay, Level level) {
        //handleHole(level.getHole());
        if(active) {
            handleBoundary(level.getScene());
            handlePaddle(level.getPaddle());
            handleBlocks(level.getBlocks());
        }
        setX(getX() + speedX * secondDelay);
        setY(getY() + speedY * secondDelay);
    }
    
    private void handleHole(Rectangle hole) {
        // TODO
    }
    
    private void handleBoundary(Scene scene) {
        if(getX() <= 0 || getX() >= scene.getWidth() - getBoundsInLocal().getWidth()) {
            speedX = -speedX;
        }
        if(getY() <= 0 || getY() >= scene.getHeight() - getBoundsInLocal().getHeight()) {
            speedY = -speedY;
        }
    }

    private void handlePaddle(Paddle paddle) {
        if(this.getBoundsInLocal().intersects(paddle.getBoundsInLocal())) {
            paddle.bounceBack(this);
        }
    }

    private void handleBlocks(List<Block> blocks) {
        for(Block block: blocks) {
            if(this.getBoundsInLocal().intersects(block.getBoundsInLocal())) {
                block.hit(this);
            }
        }
    }

    /*
    public void move(double secondDelay, double sceneWidth, double sceneHeight, List<ImageView> barriers) {
        double x = getX();
        double y = getY();
        double width = getBoundsInLocal().getWidth();
        double height = getBoundsInLocal().getHeight();
        if(y <= 0 && x >= sceneWidth * 0.4 && x <= sceneWidth * 0.6 - width) {
            active = false;
        }
        if(active) {
            if(x <= 0 || x >= sceneWidth - width) {
                speedX = -speedX;
            }
            if(y <= 0 || y >= sceneHeight - height) {
                speedY = -speedY;
            }
        }
        handleBarrier(barriers);
        setX(x + speedX * secondDelay);
        setY(y + speedY * secondDelay);
    }
    
    private void handleBarrier(List<ImageView> barriers) {
        for(ImageView barrier: barriers) {
            if(this.getBoundsInLocal().intersects(barrier.getBoundsInLocal())) {
                if(barrier instanceof Paddle) {
                    ((Paddle)barrier).bounceBack(this);
                }
                else if(barrier instanceof Block) {
                    System.out.println(1);
                    ((Block)barrier).hit(this);
                }
            }
        }
    }
    */

    public boolean out(double sceneWidth, double sceneHeight) {
        double x = getX();
        double y = getY();
        double width = getBoundsInLocal().getWidth();
        double height = getBoundsInLocal().getHeight();
        return y <= -height || y >= sceneHeight || x <= -width || x >= sceneWidth;
    }
}
