package breakout;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GameStatus {

    public static final int STARTING_LIFE = 3;
    public static final int TEXT_BORDER = 0;
    
    private int point, life, level;
    private Text pointText, lifeText, levelText;
    private boolean clear, lost;
    
    public GameStatus() {
        life = STARTING_LIFE;
        pointText = getFormatedText("Points: 0");
        lifeText = getFormatedText("Lifes: " + life);
        levelText = getFormatedText("Level 0");
    }
    
    private Text getFormatedText(String content) {
        Text text = new Text(content);
        text.setFont(new Font("Calibri", 20));
        text.setFill(Color.WHITE);
        return text;
    }
    
    public void clear() {
        clear = true;
    }
    
    public boolean isClear() {
        return clear;
    }
    
    public void lose() {
        lost = true;
    }
    
    public boolean isLost() {
        return lost;
    }
    
    public void addPoint(int toAdd) {
        point += toAdd;
        pointText.setText("Points: " + point);
    }
    
    public void addLife(int toAdd) {
        life += toAdd;
        lifeText.setText("Lives: " + life);
    }
    
    public void setLevel(int level) {
        this.level = level;
        levelText.setText("Level: " + level);
        clear = false;
        lost = false;
    }
    
    public void reset() {
        point = 0;
        life = STARTING_LIFE;
    }
    
    public int getPoint() {
        return point;
    }
    
    public int getLife() {
        return life;
    }
    
    public int getLevel() {
        return level;
    }
    
    public Group getStatusText(Scene scene) {
        Group container = new Group();
        pointText.relocate(TEXT_BORDER, TEXT_BORDER);
        lifeText.relocate(TEXT_BORDER,
                scene.getHeight() - lifeText.getBoundsInLocal().getHeight() - TEXT_BORDER);
        levelText.relocate(scene.getWidth() - levelText.getBoundsInLocal().getWidth() - TEXT_BORDER,
                TEXT_BORDER);
        container.getChildren().addAll(pointText, lifeText, levelText);
        return container;
    }
}
