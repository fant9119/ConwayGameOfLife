package main.java.com.rk.game.inject;

import javafx.util.Callback;
import main.java.com.rk.game.controllers.AboutController;
import main.java.com.rk.game.controllers.GameController;
import main.java.com.rk.game.controllers.MainController;
import main.java.com.rk.game.controllers.OptionsController;
import main.java.com.rk.game.view.SceneDispatcher;

import java.util.HashMap;
import java.util.Map;

public class ControllerInjector implements Callback<Class<?>, Object> {

    private final Map<Class<?>, Object> instances;

    public ControllerInjector(SceneDispatcher dispatcher) {
        instances = new HashMap<>();
        injectControllers(dispatcher);
    }

    private void injectControllers(final SceneDispatcher dispatcher) {
        final MainController mainController = new MainController(dispatcher);
        final GameController gameController = new GameController(dispatcher);
        final OptionsController optionsController = new OptionsController(dispatcher);
        final AboutController aboutController = new AboutController(dispatcher);

        instances.put(MainController.class, mainController);
        instances.put(GameController.class, gameController);
        instances.put(OptionsController.class, optionsController);
        instances.put(AboutController.class, aboutController);
    }

    @Override
    public Object call(Class<?> param) {
        return instances.get(param);
    }
}
