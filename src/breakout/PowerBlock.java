package breakout;

public class PowerBlock extends Block {

    private static final int TOTAL_HIT = 2;
    private static final int POINTS = 0;
    private static final String[] IMAGES = {"brick4.gif", "brick5.gif"};
    
    public PowerBlock() {
        super(TOTAL_HIT, POINTS, IMAGES);
    }

    @Override
    public void hit(Bouncer bouncer) {
        hit(bouncer, true);
    }
}
