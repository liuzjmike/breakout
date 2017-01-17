package breakout;

import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Bouncer extends ImageView {

    public static final String IMAGE = "ball.gif";
    
    private boolean active;
    private double speedX, speedY;
    
    public Bouncer() {
        super();
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE));
        setImage(image);
        active = true;
    }
    
    public Bouncer(Bouncer bouncer) {
        this();
        double speed = Math.hypot(bouncer.speedX, bouncer.speedY);
        double angle = Math.PI * Math.random();
        speedX = Math.cos(angle) * speed;
        speedY = - Math.sin(angle) * speed;
        setX(bouncer.getX());
        setY(bouncer.getY());
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

    public void launch(double speed) {
        double angle = Math.PI / 2 * (Math.random() + 0.5);
        speedX = Math.cos(angle) * speed;
        speedY = - Math.sin(angle) * speed;
    }
    
    public void move(double secondDelay, double sceneWidth, double sceneHeight, List<ImageView> barriers) {
        double x = getX();
        double y = getY();
        double width = getBoundsInLocal().getWidth();
        double height = getBoundsInLocal().getHeight();
        /*
        if(y <= 0 && x >= sceneWidth * 0.4 && x <= sceneWidth * 0.6 - width) {
            active = false;
        }
        */
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
                Class<? extends ImageView> c = barrier.getClass();
                if(c == Paddle.class) {
                    ((Paddle)barrier).bounceBack(this);
                } else {
                    
                }
            }
        }
    }

    public boolean out(double sceneWidth, double sceneHeight) {
        double x = getX();
        double y = getY();
        double width = getBoundsInLocal().getWidth();
        double height = getBoundsInLocal().getHeight();
        return y <= -height || y >= sceneHeight || x <= -width || x >= sceneWidth;
    }
}
