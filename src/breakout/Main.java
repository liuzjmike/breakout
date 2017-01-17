package breakout;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
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
    private int level;
    private Scene scene;
    private Paddle paddle;
    private List<Bouncer> bouncers;
    
    @Override
    public void start(Stage stage) throws Exception {
        level = 1;
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
        Group root = new Group();
        Scene myScene = new Scene(root, sceneWidth, sceneHeight, background);
        bouncers = new ArrayList<Bouncer>();
        initializeBlocks();
        initializePaddle(root, sceneWidth, sceneHeight);
        addBouncerOnPaddle(root);
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        myScene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return myScene;
    }

    private void step(double secondDelay) {
        List<Bouncer> toRemove = new ArrayList<Bouncer>();
        for(int i = 0; i < bouncers.size(); i++) {
            Bouncer bouncer = bouncers.get(i);
            List<ImageView> barriers = new ArrayList<ImageView>();
            barriers.add(paddle);
            bouncer.move(secondDelay, scene.getWidth(), scene.getHeight(), barriers);
            if(bouncer.out(scene.getWidth(), scene.getHeight())) {
                toRemove.add(bouncer);
            }
        }
        bouncers.removeAll(toRemove);
    }
    
    private void initializeBlocks() {
        // TODO
    }
    
    private void initializePaddle(Group group, int sceneWidth, int sceneHeight) {
        paddle = new Paddle(PADDLE_SPEED, BOUNCER_SPEED);
        paddle.setFitWidth(sceneWidth / 5);
        paddle.setX(sceneWidth / 2 - paddle.getBoundsInLocal().getWidth() / 2);
        paddle.setY(sceneHeight - paddle.getBoundsInLocal().getHeight());
        group.getChildren().add(paddle);
    }
    
    private void addBouncerOnPaddle(Group group) {
        Bouncer bouncer = new Bouncer();
        bouncer.setX(paddle.getX() + paddle.getBoundsInLocal().getWidth() / 2
                - bouncer.getBoundsInLocal().getWidth() / 2);
        bouncer.setY(paddle.getY() - bouncer.getBoundsInLocal().getHeight());
        bouncers.add(bouncer);
        paddle.addBouncer(bouncer);
        group.getChildren().add(bouncer);
    }
    
    private void addBouncerAtBouncer() {
        Bouncer bouncer = new Bouncer(bouncers.get(0));
        bouncers.add(bouncer);
        ((Group)scene.getRoot()).getChildren().add(bouncer);
    }

    private void handleKeyInput(KeyCode code) {
        if(paddle.keyInputHandler(code)) {
            return;
        }
        if(code == KeyCode.A) {
            addBouncerAtBouncer();
        }
    }
    
    private void handleMouseInput(double x, double y) {
        // TODO Auto-generated method stub
    }

    public static void main(String[] args) {
        launch(args);
    }
}
