package hu.waldorf.finance;

import com.google.inject.Guice;
import com.google.inject.Injector;
import hu.waldorf.finance.screens.MainScreen;
import hu.waldorf.finance.screens.ScreenLoader;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFxApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.close();

        Injector injector = Guice.createInjector(new BasicModule());
        ScreenLoader screenLoader = injector.getInstance(ScreenLoader.class);
        screenLoader.loadScreen(MainScreen.class);
    }

    public static void main(String args[]) {
        launch(args);
    }
}