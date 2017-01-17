package breakout;

import java.util.Random;

import javafx.scene.image.Image;

public class PlainBlock extends Block {
    
    private static final String[] IMAGES = {"brick2.gif", "brick3.gif"};
    private static final int POINTS = 1;
    
    public PlainBlock() {
        super();
        int index = new Random().nextInt(IMAGES.length);
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(IMAGES[index]));
        setImage(image);
    }

    @Override
    public int destroy() {
        return POINTS;
    }
}
