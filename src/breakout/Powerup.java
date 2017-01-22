package breakout;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Powerup extends ImageView {
    
    public static final int POWERUP_SPEED = 200;
    public static final int DURATION = 5;
    public static final String[] IMAGES = {"superball.gif", "extraballpower.gif",
            "lengthenpaddle.gif", "pointspower.gif"};
    public static final int SIZE = 16;
    
    private int type;
    
    public Powerup() {
        super();
        type = new Random().nextInt(4);
        configureImage(IMAGES[type]);
    }
    
    public Powerup(String image) {
        super();
        configureImage(image);
    }
    
    private void configureImage(String filename) {
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(filename));
        setImage(image);
        setFitWidth(SIZE);
        setFitHeight(SIZE);
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
            level.getPaddle().lengthen();
        }
        else if(type == 3) {
            level.doublePoint();
        }
    }
}
