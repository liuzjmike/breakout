package breakout;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Powerup extends ImageView {
    
    public static final int POWERUP_SPEED = 200;
    public static final int DURATION = 5;
    public static final String[] IMAGES = {"superball.gif", "extraballpower.gif",
            "lengthenpaddle.gif", "pointspower.gif"};
    
    private int type;
    
    protected Powerup() {
        super();
        type = new Random().nextInt(4);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGES[type]));
        setImage(image);
    }

    public void move(double secondDelay) {
        setY(getY() + POWERUP_SPEED * secondDelay);
    }

    public boolean out(double sceneHeight) {
        return getY() > sceneHeight;
    }
    
    public void power(Level level) {
        if(type == 0) {
            level.getPaddle().charge();
        }
        else if(type == 1) {
            level.addBouncerAtBouncer();
        }
        else if(type == 2) {
            Paddle paddle = level.getPaddle();
            if(!paddle.isLengthened()) {
                paddle.setFitWidth(paddle.getBoundsInLocal().getWidth() * 2);
            }
            paddle.timeLengthen();
        }
        else if(type == 3) {
            level.doublePoint();
        }
    }
}
