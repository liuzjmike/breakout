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
    public static final int PADDLE_SPEED = 50;
    public static final int BOUNCER_SPEED = 375;

    // some things we need to remember during our game
    private Scene scene;
    private Group root;
    private Level level;
    private int points;
    //private Paddle paddle;
    //private List<Bouncer> bouncers;
    //private List<Block> blocks;
    
    @Override
    public void start(Stage stage) throws Exception {
        scene = setupGame(WIDTH, HEIGHT, BACKGROUND);
        stage.setScene(scene);
        stage.setTitle(TITLE);
        stage.show();
        KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                      e -> step(SECOND_DELAY));
        Timeline animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private Scene setupGame(int sceneWidth, int sceneHeight, Paint background) {
        root = new Group();
        Scene newScene = new Scene(root, sceneWidth, sceneHeight, background);
        level = Level.initializeLevel(newScene, root, 0, 1);
        /*
        bouncers = new ArrayList<Bouncer>();
        blocks = new ArrayList<Block>();
        initializeBlocks(root, sceneWidth, sceneHeight);
        initializePaddle(root, sceneWidth, sceneHeight);
        addBouncerOnPaddle(root);
        */
        newScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return newScene;
    }

    private void step(double secondDelay) {
        level.step(secondDelay);
        /*
        List<Bouncer> toRemove = new ArrayList<Bouncer>();
        for(int i = 0; i < bouncers.size(); i++) {
            Bouncer bouncer = bouncers.get(i);
            List<ImageView> barriers = new ArrayList<ImageView>();
            barriers.add(paddle);
            bouncer.move(secondDelay, level);
            if(bouncer.out(scene.getWidth(), scene.getHeight())) {
                toRemove.add(bouncer);
            }
        }
        bouncers.removeAll(toRemove);
        ((Group)scene.getRoot()).getChildren().removeAll(toRemove);
        */
    }
    
    /*
    private void initializeBlocks(Group root, int sceneWidth, int sceneHeight) {
        double width = sceneWidth / 10;
        double height = width / 3;
        for(int layer = 0; layer < 5; layer++) {
            for(int i = 0; i < 10; i++) {
                Block block = new PlainBlock();
                block.setFitWidth(width);
                block.setFitHeight(height);
                block.setX(width * i);
                block.setY(height * layer * 5 / 4);
                blocks.add(block);
                root.getChildren().add(block);
            }
        }
    }
    
    private void initializePaddle(Group root, int sceneWidth, int sceneHeight) {
        paddle = new Paddle(PADDLE_SPEED, BOUNCER_SPEED);
        paddle.setFitWidth(sceneWidth / 5);
        paddle.setX(sceneWidth / 2 - paddle.getBoundsInLocal().getWidth() / 2);
        paddle.setY(sceneHeight - paddle.getBoundsInLocal().getHeight());
        root.getChildren().add(paddle);
    }
    
    private void addBouncerOnPaddle(Group root) {
        Bouncer bouncer = new Bouncer();
        bouncer.setX(paddle.getX() + paddle.getBoundsInLocal().getWidth() / 2
                - bouncer.getBoundsInLocal().getWidth() / 2);
        bouncer.setY(paddle.getY() - bouncer.getBoundsInLocal().getHeight());
        bouncers.add(bouncer);
        paddle.addBouncer(bouncer);
        root.getChildren().add(bouncer);
    }
    
    private void addBouncerAtBouncer() {
        Bouncer bouncer = new Bouncer(bouncers.get(0));
        bouncers.add(bouncer);
        ((Group)scene.getRoot()).getChildren().add(bouncer);
    }
    */

    private void handleKeyInput(KeyCode code) {
        if(code == KeyCode.DIGIT1 || code == KeyCode.DIGIT2
                || code == KeyCode.DIGIT3 || code == KeyCode.DIGIT4) {
            changeLevel(Integer.parseInt(code.getName()));
        } else {
            level.handleKeyInput(code);
        }
    }

    private void changeLevel(int levelNum) {
        root.getChildren().clear();
        level = Level.initializeLevel(scene, root, level.getPoints(), levelNum);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
