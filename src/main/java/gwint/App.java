package gwint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
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
        String image = App.class.getResource("plansza.jpg").toExternalForm();
        Image Plan = new Image(image);
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
           "-fx-background-position: center top;" + 
           "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: 1280 560; "
        );
        stage.setResizable(false);
        stage.setScene(new Scene(root,1280,760));
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}