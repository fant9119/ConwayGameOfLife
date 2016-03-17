package main.java.com.rk.game.controllers;

import javafx.fxml.FXML;
import main.java.com.rk.game.util.FXMLFile;
import main.java.com.rk.game.view.SceneDispatcher;

public class MainController {

    private final SceneDispatcher dispatcher;

    public MainController(final SceneDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @FXML
    public void exitGame() {
        //TODO Some better exit
        System.exit(0);
    }

    @FXML
    public void options() {
        dispatcher.setScene(FXMLFile.OPTIONS);
    }

    @FXML
    public void playGame() {
        dispatcher.setScene(FXMLFile.GAME);
        dispatcher.getStage().setFullScreen(true);
    }

    @FXML
    public void about() {
        dispatcher.setScene(FXMLFile.ABOUT);
    }
}
