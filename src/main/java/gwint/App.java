/*
 * PO Projekt - Karcianka chwilowo bez nazwy
 * @author Hurkala Binieda Sanocki
 * 
 */

package gwint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //Get screen dimentions
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height=screenBounds.getHeight();
        double width=screenBounds.getWidth();

        //Set stage
        stage.setTitle("Base Game");
        stage.setFullScreen(true);
        GridPane root = new GridPane();
        String image = App.class.getResource("plansza.png").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: contain" 
            //MOZNA ZMIENIC NA CONTAIN 
        );
        stage.setResizable(false);
        
        //Add scene to stage and show it
        stage.setScene(new Scene(root,width,height));
        stage.show();
    }
    
    public static void main(String[] args) {
        //3 2 1 and launch 🚀
        launch(args);
        
    }

}