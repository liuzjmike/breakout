// This entire file is part of my masterpiece.
// Mike Liu

package breakout;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

public class Paddle extends GameObject {

    public static final String IMAGE = "paddle.gif";
    public static final int PADDLE_SPEED = 350;
    
    private List<Bouncer> bouncers;
    private PauseTransition lengthenTime, shortenTime, catchTime;
    private boolean charged, catchBouncer;
    private int speed;
    
    public Paddle() {
        super(IMAGE);
        bouncers = new ArrayList<Bouncer>();
        lengthenTime = new PauseTransition(Duration.seconds(Powerup.DURATION));
        lengthenTime.setOnFinished(e -> setFitWidth(getBoundsInLocal().getWidth() / 2));
        shortenTime = new PauseTransition(Duration.seconds(Powerup.DURATION));
        shortenTime.setOnFinished(e -> setFitWidth(getBoundsInLocal().getWidth() * 2));
        catchTime = new PauseTransition(Duration.seconds(0.1));
        catchTime.setOnFinished(e -> catchBouncer = false);
    }
    
    public int getSpeed() {
        return speed;
    }

    public void lengthen() {
        if(lengthenTime.getStatus() != Animation.Status.RUNNING) {
            setFitWidth(getBoundsInLocal().getWidth() * 2);
        }
        lengthenTime.playFromStart();
    }
    
    public void shorten() {
        if(shortenTime.getStatus() != Animation.Status.RUNNING) {
            setFitWidth(getBoundsInLocal().getWidth() / 2);
        }
        shortenTime.playFromStart();
    }
    
    public void addBouncer(Bouncer bouncer) {
        bouncers.add(bouncer);
    }
    
    public void charge() {
        charged = true;
    }
    
    public boolean keyPressedHandler(KeyCode code) {
        if (code == KeyCode.RIGHT) {
            speed = PADDLE_SPEED;
        }
        else if (code == KeyCode.LEFT) {
            speed = -PADDLE_SPEED;
        }
        else if(code == KeyCode.SPACE) {
            for(Bouncer bouncer: bouncers) {
                bouncer.launch(Math.PI / 2 * (Math.random() + 0.5));
            }
            bouncers.clear();
        }
        else if(code == KeyCode.L) {
            setFitWidth(getBoundsInLocal().getWidth() * 2);
        }
        else if(code == KeyCode.S) {
            setFitWidth(getBoundsInLocal().getWidth() / 2);
        }
        else if(code == KeyCode.H && !catchBouncer) {
            catchBouncer = true;
            catchTime.play();
        } else {
            return false;
        }
        return true;
    }

    public void keyReleasedHandler(KeyCode code) {
        if(code == KeyCode.RIGHT || code == KeyCode.LEFT) {
            speed = 0;
        }
    }

    public void step(double secondDelay) {
        double d = speed * secondDelay;
        setX(getX() + d);
        for(Bouncer bouncer: bouncers) {
            bouncer.setX(bouncer.getX() + d);
        }
    }
    
    public void bounceBack(Bouncer bouncer) {
        if(bouncer.getSpeedY() <= 0) {
            return;
        }
        if(catchBouncer == true) {
            bouncer.stop();
            bouncers.add(bouncer);
            return;
        }
        double speedX = bouncer.getSpeedX();
        double bouncerCenter = bouncer.getX() + bouncer.getBoundsInLocal().getWidth() / 2;
        double thisX = getX();
        double thisWidth = getBoundsInLocal().getWidth();
        double div1 = thisX + thisWidth * 0.2;
        double div2 = thisX + thisWidth * 0.8;
        if((bouncerCenter >= thisX && bouncerCenter < div1 && speedX > 0)
                || (bouncerCenter > div2 && bouncerCenter <= thisX + thisWidth && speedX < 0)) {
            bouncer.reverseSpeedX();
            bouncer.reverseSpeedY();
        } else {
            double angle = Math.atan2(-bouncer.getSpeedY(), speedX);
            if(speedX > 10) {
                bouncer.launch(calculateAngle(angle, (bouncerCenter-div1)/(thisX+thisWidth-div1)));
            }
            else if(speedX < 10){
                bouncer.launch(calculateAngle(angle, (div2-bouncerCenter)/(div2-thisX)));
            }
            else {
                if(Math.random() > 0.5) {
                    bouncer.launch(Math.PI * 3 / 5);
                } else {
                    bouncer.launch(Math.PI * 2 / 5);
                }
            }
        }
        if(charged) {
            bouncer.fire();
            charged = false;
        } else {
            bouncer.recover();
        }
    }
    
    /**
     * Calculates out angle based on in angle.
     * This calculation will change gaming experience. Modify with care.
     * @param angle must be between -PI and PI
     * @param proportion position on paddle
     * @param offset
     * @return
     */
    private double calculateAngle(double angle, double proportion) {
        double deviation = (angle+Math.PI/2) * 1.5*proportion;
        double deviationAdj = Math.min(Math.abs(deviation)+Math.PI/20, Math.PI/3);
        deviation = deviation > 0 ? deviationAdj : -deviationAdj;
        return Math.PI/2 - deviation;
    }
}
