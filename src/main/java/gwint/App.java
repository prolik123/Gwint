package gwint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Base Game");
        GridPane root = new GridPane();
        String image = App.class.getResource("plansza.jpg").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: center top;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: 1200 500; "
        );
        stage.setResizable(false);
        stage.setScene(new Scene(root,1200,700));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
        
    }

}