package hu.waldorf.finance;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFxApp extends Application {
    @Override
    @SuppressWarnings({"ConstantConditions", "AccessStaticViaInstance"})
    public void start(Stage primaryStage) throws Exception {
        Injector injector = Guice.createInjector(new BasicModule());

        FXMLLoader loader = new FXMLLoader();
        loader.setControllerFactory(injector::getInstance);

        Parent root = loader.load(getClass().getClassLoader().getResource("fxml/main.fxml"));

        primaryStage.setTitle("Sample Application");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}