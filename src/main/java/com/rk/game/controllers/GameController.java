package main.java.com.rk.game.controllers;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import main.java.com.rk.game.util.FXMLFile;
import main.java.com.rk.game.view.SceneDispatcher;

public class GameController implements Initializable {

    @FXML
    private HBox buttons;

    @FXML
    private Canvas canvas;

    @FXML
    private Button startButton;

    @FXML
    private Button screenButton;

    @FXML
    public Button resetButton;

    @FXML
    public Button menuButton;

    private boolean[][] currentMove;
    private boolean[][] nextMove;

    private boolean play;

    private GraphicsContext offScreenGraphic;
    private SceneDispatcher dispatcher;

    private int rows;
    private int columns;
    private int delay;

    private Timer timer;

    private ImageView startImageView;
    private ImageView pauseImageView;
    private ImageView fullScreenImageView;
    private ImageView windowedImageView;

    public GameController(SceneDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    private void runGame() {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if(play) {
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            nextMove[i][j] = decide(i, j);
                        }
                    }
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < columns; j++) {
                            currentMove[i][j] = nextMove[i][j];
                        }
                    }
                    repaint();
                }
            }
        };
        timer.scheduleAtFixedRate(task, 0, delay);
    }

    public void repaint() {
        offScreenGraphic.setFill(dispatcher.getFieldColor());
        offScreenGraphic.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < columns; j++) {
                if(currentMove[i][j]) {
                    offScreenGraphic.setFill(dispatcher.getLifeColor());
                    int x = (int) (j * canvas.getWidth() / columns);
                    int y = (int) (i * canvas.getHeight() / rows);
                    offScreenGraphic.fillRect(x, y, canvas.getWidth() / columns, canvas.getHeight() / rows);
                }
            }
        }

        offScreenGraphic.setStroke(dispatcher.getGridColor());
        for(int i = 1; i < rows; i++) {
            int y = (int) (i * canvas.getHeight()/rows);
            offScreenGraphic.strokeLine(0, y, canvas.getWidth(), y);
        }
        for(int j = 1; j < columns; j++) {
            int x = (int) (j * canvas.getWidth()/columns);
            offScreenGraphic.strokeLine(x, 0, x, canvas.getHeight());
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        rows = dispatcher.getRows();
        columns = dispatcher.getColumns();
        delay = dispatcher.getDelay();
        currentMove = new boolean[rows][columns];
        nextMove = new boolean[rows][columns];
        canvas.setHeight(SceneDispatcher.SCREEN_HEIGHT);
        canvas.setWidth(SceneDispatcher.SCREEN_WIDTH);
        offScreenGraphic = canvas.getGraphicsContext2D();

        startImageView = new ImageView(new Image("images/start.jpg"));
        pauseImageView = new ImageView(new Image("images/pause.jpg"));
        windowedImageView = new ImageView(new Image("images/windowed.jpg"));
        fullScreenImageView = new ImageView(new Image("images/fullscreen.jpg"));
        ImageView menuImageView = new ImageView(new Image("images/menuB.jpg"));
        ImageView resetImageView = new ImageView(new Image("images/reset.jpg"));

        startButton.setGraphic(startImageView);
        startButton.setTooltip(new Tooltip("Play"));

        screenButton.setGraphic(windowedImageView);
        screenButton.setTooltip(new Tooltip("Windowed"));

        resetButton.setGraphic(resetImageView);
        resetButton.setTooltip(new Tooltip("Reset"));

        menuButton.setGraphic(menuImageView);
        menuButton.setTooltip(new Tooltip("Return to menu"));

        repaint();
    }

    @FXML
    public void playButtonAction() {
        play = !play;
        if(play) {
            startButton.setGraphic(pauseImageView);
            startButton.setTooltip(new Tooltip("Pause"));
            runGame();
        } else {
            startButton.setGraphic(startImageView);
            startButton.setTooltip(new Tooltip("Play"));
            timer.cancel();
        }
        repaint();
    }

    private boolean decide(int i, int j) {
        int neighbors = 0;
        if(j > 0) {
            if(currentMove[i][j - 1]) neighbors++;
            if(i > 0) {
                if(currentMove[i-1][j-1]) neighbors++;
            }
            if(i < rows - 1) {
                if(currentMove[i+1][j-1]) neighbors++;
            }
        }
        if(j < columns - 1) {
            if(currentMove[i][j + 1]) neighbors++;
            if(i > 0) {
                if(currentMove[i-1][j+1]) neighbors++;
            }
            if(i < rows - 1) {
                if(currentMove[i+1][j+1]) neighbors++;
            }
        }
        if(i > 0) {
            if(currentMove[i-1][j]) neighbors++;
        }
        if(i < rows - 1) {
            if(currentMove[i+1][j]) neighbors++;
        }
        if(neighbors == 3) return true;
        if(currentMove[i][j] && neighbors == 2) return  true;
        return false;
    }

    public void canvasOnMouseClicked(MouseEvent event) {
        int j = (int) (event.getX() * columns/canvas.getWidth());
        int i = (int) (event.getY() * rows/canvas.getHeight());
        currentMove[i][j] = !currentMove[i][j];
        repaint();
    }

    public void resetButtonAction() {
        if(play) play = false;
        if(timer != null)timer.cancel();
        startButton.setGraphic(startImageView);
        startButton.setTooltip(new Tooltip("Play"));
        currentMove = new boolean[rows][columns];
        nextMove = new boolean[rows][columns];
        repaint();
    }

    public void menuButtonAction() {
        if(play) play = false;
        if(timer != null)timer.cancel();
        dispatcher.setScene(FXMLFile.MAIN);
    }

    public void changeScreenSize() {
        if(dispatcher.getStage().isFullScreen()) {
            dispatcher.getStage().setFullScreen(false);
            screenButton.setGraphic(fullScreenImageView);
            screenButton.setTooltip(new Tooltip("Full Screen"));
        } else {
            dispatcher.getStage().setFullScreen(true);
            screenButton.setGraphic(windowedImageView);
            screenButton.setTooltip(new Tooltip("Windowed"));
        }
    }

    public void showButtons(MouseEvent event) {
        if(event.getEventType() == MouseEvent.MOUSE_ENTERED) {
            startButton.setVisible(true);
        }
    }

    public void hideButtons(MouseEvent event) {
        if (event.getEventType() == MouseEvent.MOUSE_EXITED) {
            startButton.setVisible(false);
        }
    }

    public void onEscPressedEvent(KeyEvent event) {
        if(event.getEventType() == KeyEvent.KEY_PRESSED) {
            if(KeyCode.ESCAPE == event.getCode()) {
                if(dispatcher.getStage().isFullScreen()) {
                    screenButton.setGraphic(fullScreenImageView);
                    screenButton.setTooltip(new Tooltip("Full Screen"));
                }
            }
        }
    }
}
