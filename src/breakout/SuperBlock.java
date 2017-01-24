package breakout;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class SuperBlock extends Block {
    
    public static final int TOTAL_HIT = 10;
    public static final int POINTS = 100;
    public static final String[] IMAGES = {"brick10.gif"};
    public static final int SPEED = 100;
    public static final int BOMB_INTERVAL = 5;
    
    private Level level;
    private int speed;
    private boolean vulnerable;
    private PauseTransition timeInvulnerable, timeBomb;

    public SuperBlock(Level level) {
        super(TOTAL_HIT, POINTS, IMAGES);
        this.level = level;
        speed = SPEED;
        vulnerable = true;
        timeInvulnerable = new PauseTransition(Duration.seconds(0.1));
        timeInvulnerable.setOnFinished(e -> vulnerable = true);
        timeBomb = new PauseTransition(Duration.seconds(BOMB_INTERVAL));
        timeBomb.setOnFinished(e -> dropBomb(level));
        timeBomb.play();
    }

    @Override
    public void hit(Bouncer bouncer) {
        double bouncerCenter = bouncer.getX() + bouncer.getBoundsInLocal().getWidth() / 2;
        double thisX = getX();
        double thisWidth = getBoundsInLocal().getWidth();
        boolean hitFromAbove = bouncer.getSpeedY() > 0 && bouncerCenter > thisX && bouncerCenter < thisX + thisWidth;
        if(hitFromAbove && vulnerable) {
            hitLeft--;
            vulnerable = false;
            level.addPowerup(this, new Powerup());
            timeInvulnerable.play();
        }
        if(!bouncer.isCannon()) {
            bounceBack(bouncer);
        }
    }
    
    private void bounceBack(Bouncer bouncer) {
        double bouncerCenter = bouncer.getX() + bouncer.getBoundsInLocal().getWidth() / 2;
        if(bouncerCenter >= getX() && bouncerCenter <= getX() + getBoundsInLocal().getWidth()) {
            bouncer.reverseSpeedY();
        } else {
            bouncer.reverseSpeedX();
        }
    }
    
    public void step(double secondDelay, Level level) {
        if(getX() <= 0 || getX() > level.getScene().getWidth() - getBoundsInLocal().getWidth()) {
            speed = -speed;
        }
        setX(getX() + speed * secondDelay);
    }

    private void dropBomb(Level level) {
        level.addPowerup(this, new Bomb());
        timeBomb.play();
    }
}
