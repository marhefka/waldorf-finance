package hu.waldorf.finance.screens.main;

import com.google.inject.Inject;
import hu.waldorf.finance.screens.Screen;
import hu.waldorf.finance.screens.ScreenLoader;
import hu.waldorf.finance.screens.befizetesek_jovairasa.BefizetesekJovairasaScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class MainScreen implements Screen {
    private final ScreenLoader screenLoader;

    @Inject
    public MainScreen(ScreenLoader screenLoader) {
        this.screenLoader = screenLoader;
    }

    @Override
    public void init() {
    }

    @FXML
    public void onBefizetesekJovairasa(ActionEvent event) {
        screenLoader.hide(event);
        screenLoader.loadScreen(BefizetesekJovairasaScreen.class);
    }
}
