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
<<<<<<< HEAD
import java.util.*;
/**
 * JavaFX App
 */
=======

>>>>>>> remotes/origin/alpha
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
<<<<<<< HEAD
        String image = App.class.getResource("plansza.jpg").toExternalForm();
=======
        String image = App.class.getResource("plansza.png").toExternalForm();
>>>>>>> remotes/origin/alpha
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
<<<<<<< HEAD
            "-fx-background-size: 1200 500; "
        );
        stage.setResizable(false);
        stage.setScene(new Scene(root,1200,700));
=======
            "-fx-background-size: cover" 
            //MOZNA ZMIENIC NA CONTAIN 
        );
        stage.setResizable(false);
        
        //Add scene to stage and show it
        stage.setScene(new Scene(root,width,height));
>>>>>>> remotes/origin/alpha
        stage.show();
    }

    public static void main(String[] args) {
        //3 2 1 and launch 🚀
        launch(args);
        
    }

}