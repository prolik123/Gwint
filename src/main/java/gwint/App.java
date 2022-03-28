package gwint;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height=screenBounds.getHeight();
        double width=screenBounds.getWidth();

        stage.setTitle("Base Game");
        stage.setFullScreen(true);
        GridPane root = new GridPane();
        String image = App.class.getResource("plansza.png").toExternalForm();
        //Image Plan = new Image(image);
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: cover" 
            //MOZNA ZMIENIC NA CONTAIN 
        );
        stage.setResizable(false);
        
        stage.setScene(new Scene(root,width,height));
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
        
    }

}