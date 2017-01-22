package breakout;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class SceneGenerator {
    
    public static final int WELCOME = 0;
    public static final int HELP = 1;
    public static final int LOSE = 2;
    public static final int WIN = 3;
    public static final Paint TEXT_FILL = Color.WHITE;
    public static final Font MAIN_TEXT_FONT = new Font("Calibri", 50);
    public static final Font SUB_TEXT_FONT = new Font("Calibri", 15);
    public static final String HELP_TEXT = "Press LEFT and RIGHT arrow to move your paddle and"
            + "catch your ball. Destroy the bricks to get points and go throught the hole at the"
            + "top of the screen to go to the next level. In the final level, you must destroy the"
            + "super block to win the game. Good luck!";

    public SceneGenerator() {
    }

    public Scene getScene(double width, double height, Paint fill, int type, int point) {
        Parent root;
        if(type == LOSE) {
            root = getGroup(width, height, "YOU LOST!", "You got " + point + " points. Press SPACE to continue");
        }
        else if(type == WIN) {
            root = getGroup(width, height, "YOU WON!", "You got " + point + " points. Press SPACE to continue");
        }
        else if(type == HELP) {
            root = getHelpRoot(width, height);
        }
        else {
            root = getGroup(width, height, "Breakout! v1.0", "Press H for help. Press SPACE to start.");
        }
        Scene scene = new Scene(root, width, height, fill);
        return scene;
    }
    
    private Parent getHelpRoot(double width, double height) {
        Group root = new Group();
        Text mainText = getText(HELP_TEXT, new Font("Calibri", 20));
        mainText.setWrappingWidth(width);
        mainText.setTextAlignment(TextAlignment.CENTER);
        mainText.relocate(0, (height - mainText.getBoundsInLocal().getHeight()) / 2);
        Text subText = getText("Press Q to quit", SUB_TEXT_FONT);
        subText.relocate(0, height - subText.getBoundsInLocal().getHeight());
        root.getChildren().addAll(mainText, subText);
        return root;
    }

    private Group getGroup(double width, double height, String main, String sub) {
        Group root = new Group();
        Text mainText = getText(main, MAIN_TEXT_FONT);
        Text subText = getText(sub, SUB_TEXT_FONT);
        mainText.relocate((width - mainText.getBoundsInLocal().getWidth()) / 2,
                height / 2 - mainText.getBoundsInLocal().getHeight());
        subText.relocate((width - subText.getBoundsInLocal().getWidth()) / 2,
                height / 2 + mainText.getBoundsInLocal().getHeight());
        root.getChildren().addAll(mainText, subText);
        return root;
    }
    
    private Text getText(String content, Font font) {
        Text text = new Text(content);
        text.setFont(font);
        text.setFill(TEXT_FILL);
        return text;
    }
}
