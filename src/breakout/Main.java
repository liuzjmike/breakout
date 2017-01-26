package breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {

    public static final String TITLE = "Breakout";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 500;
    public static final Paint BACKGROUND = Color.BLACK;
    public static final int FRAMES_PER_SECOND = 60;
    public static final double MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private Level level;
    private Timeline animation;
    
    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle(TITLE);
        startWelcome(stage, WIDTH, HEIGHT, BACKGROUND);
        stage.show();
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
    }
    
    private Scene startWelcome(Stage stage, int sceneWidth, int sceneHeight, Paint background) {
        Scene newScene = new SceneGenerator().getScene(WIDTH, HEIGHT, BACKGROUND, SceneGenerator.WELCOME, 0);
        newScene.setOnKeyPressed(e -> welcomeKeyInputHandler(e.getCode(), stage));
        stage.setScene(newScene);
        return newScene;
    }
    
    private Scene startHelp(Stage stage, int sceneWidth, int sceneHeight, Paint background) {
        Scene newScene = new SceneGenerator().getScene(WIDTH, HEIGHT, BACKGROUND, SceneGenerator.HELP, 0);
        newScene.setOnKeyPressed(e -> helpKeyInputHandler(e.getCode(), stage));
        stage.setScene(newScene);
        return newScene;
    }

    private Scene startGame(Stage stage, int sceneWidth, int sceneHeight, Paint background) {
        Group root = new Group();
        Scene newScene = new Scene(root, sceneWidth, sceneHeight, background);
        level = new Level(newScene, root);
        level.initializeLevel(1);
        newScene.setOnKeyPressed(e -> handleKeyPressed(e.getCode()));
        newScene.setOnKeyReleased(e -> handleKeyReleased(e.getCode()));
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                e -> step(SECOND_DELAY, stage));
        animation.getKeyFrames().clear();
        animation.getKeyFrames().add(frame);
        animation.play();
        stage.setScene(newScene);
        return newScene;
    }

    private Scene end(Stage stage, boolean won, int point) {
        animation.stop();
        Scene newScene;
        if(won) {
            newScene = new SceneGenerator().getScene(WIDTH, HEIGHT, BACKGROUND, SceneGenerator.WIN, point);
        } else {
            newScene = new SceneGenerator().getScene(WIDTH, HEIGHT, BACKGROUND, SceneGenerator.LOSE, point);
        }
        newScene.setOnKeyPressed(e -> endKeyInputHandler(e.getCode(), stage));
        stage.setScene(newScene);
        return newScene;
    }

    private void step(double secondDelay, Stage stage) {
        level.step(secondDelay);
        if(level.getStatus().isClear()) {
            int levelNum = level.getStatus().getLevel();
            if(levelNum == 4) {
                end(stage, true, level.getStatus().getPoint());
            } else {
                level.initializeLevel(levelNum + 1);
                level.getStatus().addLife(1);
            }
        }
        else if(level.getStatus().isLost()) {
            end(stage, false, level.getStatus().getPoint());
        }
    }

    private void handleKeyPressed(KeyCode code) {
        if(code == KeyCode.DIGIT1 || code == KeyCode.DIGIT2
                || code == KeyCode.DIGIT3 || code == KeyCode.DIGIT4) {
            level.initializeLevel((Integer.parseInt(code.getName())));
        } else {
            level.handleKeyPressed(code);
        }
    }
    
    private void handleKeyReleased(KeyCode code) {
        level.getPaddle().keyReleasedHandler(code);
    }

    private void welcomeKeyInputHandler(KeyCode code, Stage stage) {
        if(code == KeyCode.H) {
            startHelp(stage, WIDTH, HEIGHT, BACKGROUND);
        }
        else if(code == KeyCode.SPACE) {
            startGame(stage, WIDTH, HEIGHT, BACKGROUND);
        }
    }

    private void helpKeyInputHandler(KeyCode code, Stage stage) {
        if(code == KeyCode.Q) {
            startWelcome(stage, WIDTH, HEIGHT, BACKGROUND);
        }
    }

    private void endKeyInputHandler(KeyCode code, Stage stage) {
        if(code == KeyCode.SPACE) {
            startWelcome(stage, WIDTH, HEIGHT, BACKGROUND);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
