package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * This class is used to simplify the constructor of its subclasses and should not be instantiated
 * @author Mike Liu
 *
 */
public class GameObject extends ImageView {

    protected GameObject(String filename) {
        super();
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(filename));
        setImage(image);
    }
}
