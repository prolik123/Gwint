/*
 * PO Projekt - Karcianka chwilowo bez nazwy
 * @author Hurkala Binieda Sanocki
 * 
 */

package gwint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.*;

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
        GameEngine engine = new GameEngine();
        
        //BoardSlot.BoardView n = (engine.myCards).BoardView(100);
        root.add(engine.myCards.getNewBoardView(1000),0,0);
        BoardSlot.BoardView t = engine.opponentCards.getNewBoardView(1000);
        root.add(t,0,500);
        
        stage.setScene(new Scene(root,width,height));
        stage.show();
        engine.opponentCards.cardList.remove(0);
        try{
            TimeUnit.SECONDS.sleep(5);
        }catch (Exception e){}

        root.getChildren().remove(engine.opponentCards.getCurentBoardView());
        root.add(engine.opponentCards.getNewBoardView(1000),0,500);
        stage.setResizable(false);
        
        //Add scene to stage and show it
        //stage.setScene(new Scene(root,width,height));
        //stage.show();
    }

    public static void main(String[] args) {
        //3 2 1 and launch 🚀
        launch(args);
    }

}
