package breakout;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;

public class Paddle extends ImageView{

    public static final String IMAGE = "paddle.gif";
    
    private int speed, bouncerSpeed;
    private List<Bouncer> bouncers;
    
    public Paddle(int speed, int bouncerSpeed) {
        super();
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGE));
        setImage(image);
        this.speed = speed;
        this.bouncerSpeed = bouncerSpeed;
        bouncers = new ArrayList<Bouncer>();
    }
    
    public void addBouncer(Bouncer bouncer) {
        bouncers.add(bouncer);
    }
    
    public boolean keyInputHandler(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            setX(getX() + speed);
            for(int i = 0; i < bouncers.size(); i++) {
                Bouncer bouncer = bouncers.get(i);
                bouncer.setX(bouncer.getX() + speed);
            }
            return true;
        }
        else if (code == KeyCode.LEFT) {
            setX(getX() - speed);
            for(int i = 0; i < bouncers.size(); i++) {
                Bouncer bouncer = bouncers.get(i);
                bouncer.setX(bouncer.getX() - speed);
            }
            return true;
        }
        else if(code == KeyCode.SPACE) {
            for(int i = 0; i < bouncers.size(); i++) {
                Bouncer bouncer = bouncers.get(i);
                bouncer.launch(bouncerSpeed);
                bouncers.remove(bouncer);
            }
            return true;
        }
        return false;
    }
    
    public void bounceBack(Bouncer bouncer) {
        double speedX = bouncer.getSpeedX();
        double speedY = bouncer.getSpeedY();
        if(speedY < 0) {
            return;
        }
        bouncer.reverseSpeedY();
        double center = bouncer.getX() + bouncer.getBoundsInLocal().getWidth() / 2;
        double x = getX();
        double width = getBoundsInLocal().getWidth();
        double div1 = x + width * 0.3;
        double div2 = x + width * 0.7;
        if((center >= x && center < div1 && speedX > 0)
                || (center > div2 && center <= x + width && speedX < 0)) {
            bouncer.reverseSpeedX();
        }
    }
}
