package main.java.com.rk.game;

import javafx.application.Application;
import javafx.stage.Stage;
import main.java.com.rk.game.view.SceneDispatcher;

public class Launch extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneDispatcher dispatcher = new SceneDispatcher(primaryStage);
        dispatcher.launchMainScene();
    }

    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
