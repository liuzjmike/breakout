package breakout;

public class BarrierBlock extends Block {

    public static final int TOTAL_HIT = 1;
    public static final int POINTS = 10;
    public static final String[] IMAGES = {"brick2.gif", "brick3.gif"};
    
    public BarrierBlock() {
        super(TOTAL_HIT, POINTS, IMAGES);
    }
    
    @Override
    public void hit(Bouncer bouncer) {
        double bouncerCenter = bouncer.getX() + bouncer.getBoundsInLocal().getWidth() / 2;
        double thisX = getX();
        double thisWidth = getBoundsInLocal().getWidth();
        boolean hitFromAbove = bouncer.getSpeedY() > 0 && bouncerCenter > thisX && bouncerCenter < thisX + thisWidth;
        hit(bouncer, hitFromAbove);
    }
}
