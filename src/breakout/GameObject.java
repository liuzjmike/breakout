package breakout;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameObject extends ImageView {

    protected GameObject(String filename) {
        super();
        Image image = new Image(getClass().getClassLoader().getResourceAsStream(filename));
        setImage(image);
    }
}
