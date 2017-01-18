package breakout;

public class PlainBlock extends Block {

    public static final int TOTAL_HIT = 1;
    public static final int POINTS = 1;
    public static final String[] IMAGES = {"brick2.gif", "brick3.gif"};
    
    public PlainBlock() {
        super(TOTAL_HIT, POINTS, IMAGES);
    }

    @Override
    public void hit(Bouncer bouncer) {
        hit(bouncer, true);
    }
}
