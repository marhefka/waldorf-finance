package hu.waldorf.finance;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class JavaFxSample extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        Group group = new Group();

        Scene scene = new Scene(group, 600, 300);
        scene.setFill(Color.BROWN);
        Text text = new Text(100, 100, "Hello Andris");
        text.setFont(new Font(45));
        group.getChildren().add(text);

        primaryStage.setTitle("Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}