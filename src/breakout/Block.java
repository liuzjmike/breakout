package breakout;

import java.util.Random;

public abstract class Block extends GameObject {
    
    private int hitLeft;
    private final int points;
    
    protected Block(int totalHit, int points, String[] images) {
        super(images[new Random().nextInt(images.length)]);
        hitLeft = totalHit;
        this.points = points;
    }
    
    public int getPoint() {
        return points;
    }
    
    public boolean isDestroyed() {
        return hitLeft <= 0;
    }
    
    /**
     * Called when the block is hit. Bounces back the bouncer and
     * determines whether this block should be destroyed
     * @return whether this block should be destroyed
     */
    public abstract void hit(Bouncer bouncer);
    
    protected void hit(Bouncer bouncer, boolean damaged) {
        if(damaged) {
            hitLeft--;
        }
        bounceBack(bouncer);
    }
    
    private void bounceBack(Bouncer bouncer) {
        if(bouncer.isCannon()) {
            hitLeft = 0;
        } else {
            double bouncerCenter = bouncer.getX() + bouncer.getBoundsInLocal().getWidth() / 2;
            double thisX = getX();
            double thisWidth = getBoundsInLocal().getWidth();
            if(bouncerCenter >= thisX && bouncerCenter <= thisX + thisWidth) {
                bouncer.reverseSpeedY();
            } else {
                bouncer.reverseSpeedX();
            }
        }
        
        /*
        double x = bouncer.getX();
        double y = bouncer.getY();
        double width = bouncer.getBoundsInLocal().getWidth();
        double height = bouncer.getBoundsInLocal().getHeight();
        
        double thisWidth = getBoundsInLocal().getWidth();
        double thisHeight = getBoundsInLocal().getHeight();
        
        double distX = Math.min(Math.abs(x + width - getX()), Math.abs(x - getX() - thisWidth));
        double distY = Math.min(Math.abs(y + height - getY()), Math.abs(y - getY() - thisHeight));
        
        double ratio = distX / distY;        
        if(ratio < 2/3) {
            bouncer.reverseSpeedX();
        }
        else if(ratio > 3/2) {
            bouncer.reverseSpeedY();
        }
        else {
            bouncer.reverseSpeedX();
            bouncer.reverseSpeedY();
        }
        */
    }
    
}
