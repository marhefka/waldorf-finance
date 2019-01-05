package hu.waldorf.finance.screens;

import com.google.inject.Inject;
import com.google.inject.Injector;
import hu.waldorf.finance.screens.befizetesek_jovairasa.BefizetesekJovairasaScreen;
import hu.waldorf.finance.screens.main.MainScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ScreenLoader {
    private static final Map<Class<? extends Screen>, String> fxmls = new HashMap<>() {{
        put(MainScreen.class, "main.fxml");
        put(BefizetesekJovairasaScreen.class, "befizetesek_jovairasa.fxml");
    }};

    private final Injector injector;

    @Inject
    public ScreenLoader(Injector injector) {
        this.injector = injector;
    }

    public void loadScreen(Class<? extends Screen> screenClass) {
        if (!fxmls.containsKey(screenClass)) {
            throw new UnsupportedOperationException("Screen class not found: " + screenClass.getName());
        }

        try {
            Screen screen = injector.getInstance(screenClass);

            Stage stage = new Stage();
            URL url = getClass().getClassLoader().getResource("fxml/" + fxmls.get(screenClass));

            FXMLLoader loader = new FXMLLoader(url);
            loader.setControllerFactory(clazz -> screen);

            Parent root = loader.load();

            stage.setTitle("Sample Application");
            Scene scene = new Scene(root);
            stage.setScene(scene);

            screen.init();
            stage.show();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    public void hide(ActionEvent event) {
        ((Node) event.getSource()).getScene().getWindow().hide();
    }
}
