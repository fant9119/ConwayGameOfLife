package main.java.com.rk.game.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Callback;
import java.io.IOException;

public class FXMLFactory {

    private final Callback<Class<?>, Object> controllerInjector;

    public FXMLFactory(Callback<Class<?>, Object> controllerInjector) {
        this.controllerInjector = controllerInjector;
    }
    
    public Parent getFxmlRoot(final FXMLFile file) {
        final FXMLLoader loader = new FXMLLoader(file.url());

        loader.setControllerFactory(controllerInjector);

        try {
            loader.load();
        } catch (IOException e) {
            throw new IllegalStateException("Can't load FXML file [" + file.url() + "]", e);
        }
        return loader.getRoot();
    }
}
