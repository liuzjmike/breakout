package breakout;

import javafx.scene.image.ImageView;

public abstract class Block extends ImageView {
    
    protected Block() {
        super();
    }
    
    abstract public int destroy();
    
    public void bounceBack(Bouncer bouncer) {
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
    }
    
}
