package gwint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("cos");
        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: brown");
        stage.setResizable(false);
        stage.setScene(new Scene(root,640,480));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}