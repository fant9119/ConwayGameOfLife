package main.java.com.rk.game.view;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.java.com.rk.game.inject.ControllerInjector;
import main.java.com.rk.game.util.FXMLFactory;
import main.java.com.rk.game.util.FXMLFile;

public class SceneDispatcher {

    public static final double SCREEN_WIDTH = getScreenWidth();
    public static final double SCREEN_HEIGHT = getScreenHeight();

    public static final Color DEFAULT_GRID_COLOR = Color.BLACK;
    public static final Color DEFAULT_FIELD_COLOR = Color.GRAY;
    public static final Color DEFAULT_LIFE_COLOR = Color.ORANGE;
    public static final int DEFAULT_ROWS_NUMBER = 50;
    public static final int DEFAULT_COLUMNS_NUMBER = 100;
    public static final int DEFAULT_DELAY = 100;

    private Stage stage;
    private FXMLFactory factory;

    private Color gridColor = DEFAULT_GRID_COLOR;
    private Color fieldColor = DEFAULT_FIELD_COLOR;
    private Color lifeColor = DEFAULT_LIFE_COLOR;
    private int rows = DEFAULT_ROWS_NUMBER;
    private int columns = DEFAULT_COLUMNS_NUMBER;
    private int delay = DEFAULT_DELAY;

    public SceneDispatcher(Stage stage) {
        this.stage = stage;

        ControllerInjector injector = new ControllerInjector(this);
        factory = new FXMLFactory(injector);
    }

    public void errorWindow(String title, String text) {
        Alert alert = new Alert(Alert.AlertType.ERROR, text, ButtonType.CLOSE);
        alert.setTitle(title);
        alert.setResizable(false);
        alert.show();
    }

    public void launchMainScene() {
        stage.setScene(createScene(FXMLFile.MAIN));
        stage.setTitle("Conway's Game Of Life");
        stage.setOnCloseRequest(event -> {
            if(event.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                //TODO Some better exit
                System.exit(0);
            }
        });
        stage.show();
    }

    public Scene createScene(FXMLFile file) {
        return new Scene(factory.getFxmlRoot(file),SCREEN_WIDTH, SCREEN_HEIGHT);
    }

    public void setScene(FXMLFile file) {
        Parent root = factory.getFxmlRoot(file);
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);
        stage.setScene(scene);
    }

    public Stage getStage() {
        return stage;
    }

    public Color getGridColor() {
        return gridColor;
    }

    public void setGridColor(Color gridColor) {
        this.gridColor = gridColor;
    }

    public Color getFieldColor() {
        return fieldColor;
    }

    public void setFieldColor(Color fieldColor) {
        this.fieldColor = fieldColor;
    }

    public Color getLifeColor() {
        return lifeColor;
    }

    public void setLifeColor(Color lifeColor) {
        this.lifeColor = lifeColor;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private static double getScreenWidth() {
        return Screen.getPrimary().getBounds().getWidth();
    }

    private static double getScreenHeight() {
        return Screen.getPrimary().getBounds().getHeight();
    }
}
