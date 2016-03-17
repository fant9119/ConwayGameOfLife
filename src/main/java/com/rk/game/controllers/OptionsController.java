package main.java.com.rk.game.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import main.java.com.rk.game.util.FXMLFile;
import main.java.com.rk.game.view.SceneDispatcher;
import java.net.URL;
import java.util.ResourceBundle;

public class OptionsController implements Initializable {

    @FXML
    private ColorPicker gridColor;

    @FXML
    private ColorPicker fieldColor;

    @FXML
    private ColorPicker lifeColor;

    @FXML
    private TextField rowNumberField;

    @FXML
    private TextField columnNumberField;

    @FXML
    private TextField delayField;

    @FXML
    private Button menuButton;

    private SceneDispatcher dispatcher;


    public OptionsController(SceneDispatcher dispatcher) {
        this.dispatcher = dispatcher;
    }

    @FXML
    public void menuButtonAction() {
        if(checkInput()) {
            dispatcher.setRows(Integer.parseInt(rowNumberField.getText()));
            dispatcher.setColumns(Integer.parseInt(columnNumberField.getText()));
            dispatcher.setDelay(Integer.parseInt(delayField.getText()));
            dispatcher.setGridColor(gridColor.getValue());
            dispatcher.setFieldColor(fieldColor.getValue());
            dispatcher.setLifeColor(lifeColor.getValue());
            dispatcher.setScene(FXMLFile.MAIN);
        } else {
            dispatcher.errorWindow("Wrong input!", "Check input fields for row, column, delay!");
        }
    }

    @FXML
    public void restoreButtonAction() {
        gridColor.setValue(SceneDispatcher.DEFAULT_GRID_COLOR);
        fieldColor.setValue(SceneDispatcher.DEFAULT_FIELD_COLOR);
        lifeColor.setValue(SceneDispatcher.DEFAULT_LIFE_COLOR);
        rowNumberField.setText(String.valueOf(SceneDispatcher.DEFAULT_ROWS_NUMBER));
        columnNumberField.setText(String.valueOf(SceneDispatcher.DEFAULT_COLUMNS_NUMBER));
        delayField.setText(String.valueOf(SceneDispatcher.DEFAULT_DELAY));
    }

    private boolean checkInput() {
        String rowText = rowNumberField.getText();
        String columnText = columnNumberField.getText();
        String delayText = delayField.getText();

        int errors = 0;

        if(!rowText.matches("^[1-9][0-9]*")) {
            rowNumberField.getStyleClass().remove("good");
            rowNumberField.getStyleClass().add("error");
            errors++;
        } else {
            rowNumberField.getStyleClass().remove("error");
            rowNumberField.getStyleClass().add("good");
        }
        if(!columnText.matches("^[1-9][0-9]*")) {
            columnNumberField.getStyleClass().remove("good");
            columnNumberField.getStyleClass().add("error");
            errors++;
        } else {
            columnNumberField.getStyleClass().remove("error");
            columnNumberField.getStyleClass().add("good");
        }
        if(!delayText.matches("^[1-9][0-9]*")) {
            delayField.getStyleClass().remove("good");
            delayField.getStyleClass().add("error");
            errors++;
        } else {
            delayField.getStyleClass().remove("error");
            delayField.getStyleClass().add("good");
        }
        return errors == 0;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gridColor.setValue(dispatcher.getGridColor());
        fieldColor.setValue(dispatcher.getFieldColor());
        lifeColor.setValue(dispatcher.getLifeColor());
        rowNumberField.setText(String.valueOf(dispatcher.getRows()));
        columnNumberField.setText(String.valueOf(dispatcher.getColumns()));
        delayField.setText(String.valueOf(dispatcher.getDelay()));
    }

    public void checkInput(KeyEvent event) {
        Platform.runLater(() -> {
            TextField textField = (TextField)event.getSource();
            if(textField.getText().matches("^[1-9][0-9]*")) {
                textField.getStyleClass().remove("error");
                textField.getStyleClass().addAll("good");
            } else {
                textField.getStyleClass().remove("good");
                textField.getStyleClass().addAll("error");
            }
        });
    }
}

