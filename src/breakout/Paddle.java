package breakout;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Paddle extends GameObject {

    public static final String IMAGE = "paddle.gif";
    public static final int PADDLE_SPEED = 50;
    
    private List<Bouncer> bouncers;
    private PauseTransition lengthenTime;
    private boolean charged;
    
    public Paddle() {
        super(IMAGE);
        bouncers = new ArrayList<Bouncer>();
        lengthenTime = new PauseTransition(Duration.seconds(Powerup.DURATION));
        lengthenTime.setOnFinished(e -> setFitWidth(getBoundsInLocal().getWidth() / 2));
    }
    
    public boolean isLengthened() {
        return lengthenTime.getStatus() == Animation.Status.RUNNING;
    }

    public void timeLengthen() {
        lengthenTime.playFromStart();
    }
    
    public void addBouncer(Bouncer bouncer) {
        bouncers.add(bouncer);
    }
    
    public void charge() {
        charged = true;
    }
    
    public boolean keyInputHandler(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            setX(getX() + PADDLE_SPEED);
            for(int i = 0; i < bouncers.size(); i++) {
                Bouncer bouncer = bouncers.get(i);
                bouncer.setX(bouncer.getX() + PADDLE_SPEED);
            }
            return true;
        }
        else if (code == KeyCode.LEFT) {
            setX(getX() - PADDLE_SPEED);
            for(int i = 0; i < bouncers.size(); i++) {
                Bouncer bouncer = bouncers.get(i);
                bouncer.setX(bouncer.getX() - PADDLE_SPEED);
            }
            return true;
        }
        else if(code == KeyCode.SPACE) {
            for(int i = 0; i < bouncers.size(); i++) {
                Bouncer bouncer = bouncers.get(i);
                bouncer.launch();
                bouncers.remove(bouncer);
            }
            return true;
        }
        else if(code == KeyCode.L) {
            setFitWidth(getBoundsInLocal().getWidth() * 2);
            return true;
        }
        else if(code == KeyCode.S) {
            setFitWidth(getBoundsInLocal().getWidth() / 2);
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
        double bouncerCenter = bouncer.getX() + bouncer.getBoundsInLocal().getWidth() / 2;
        double thisX = getX();
        double thisWidth = getBoundsInLocal().getWidth();
        double div1 = thisX + thisWidth * 0.3;
        double div2 = thisX + thisWidth * 0.7;
        if((bouncerCenter >= thisX && bouncerCenter < div1 && speedX > 0)
                || (bouncerCenter > div2 && bouncerCenter <= thisX + thisWidth && speedX < 0)) {
            bouncer.reverseSpeedX();
        }
        if(charged) {
            bouncer.fire();
            charged = false;
        } else {
            bouncer.recover();
        }
    }
}
