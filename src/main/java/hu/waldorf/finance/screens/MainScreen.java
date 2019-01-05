package hu.waldorf.finance.screens;

import com.google.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainScreen implements Screen {
    private final ScreenLoader screenLoader;

    @Inject
    public MainScreen(ScreenLoader screenLoader) {
        this.screenLoader = screenLoader;
    }

    @FXML
    public void onBefizetesekJovairasa(ActionEvent event) {
        screenLoader.hide(event);
        screenLoader.loadScreen(BefizetesekJovairasaScreen.class);
    }
}
