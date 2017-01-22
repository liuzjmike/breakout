package breakout;

public class Bomb extends Powerup {
    
    public static final String IMAGE = "bomb.gif";
    
    public Bomb() {
        super(IMAGE);
    }
    
    @Override
    public void power(Level level) {
        level.getPaddle().shorten();
    }
}
