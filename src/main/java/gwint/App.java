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
<<<<<<< HEAD
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
=======
import javafx.scene.layout.*;
>>>>>>> remotes/origin/alpha

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

        //Create 200x200 grid
        GridPane root = new GridPane();
        //root.setGridLinesVisible(true);

        for(int i=0;i<200;i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(0.5);
            root.getColumnConstraints().addAll(column);

            RowConstraints row = new RowConstraints();
            row.setPercentHeight(0.5);
            root.getRowConstraints().addAll(row);
        }
        
        //Set background
        String image = App.class.getResource("plansza.png").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: contain" 
<<<<<<< HEAD
            //MOZNA ZMIENIC NA CONTAIN 
        );
        GameEngine engine = new GameEngine();
        
        //BoardSlot.BoardView n = (engine.myCards).BoardView(100);
        root.add(engine.myCards.getNewBoardView(1000),0,80);
        for(int k=0;k<3;k++)
            root.add(engine.myBoard[k].getNewBoardView(1000),0,30+10*k);
        //BoardSlot.BoardView t = engine.opponentCards.getNewBoardView(1000);
        //root.add(t,500,0);
        
        stage.setScene(new Scene(root,width,height));
        stage.show();
        stage.setResizable(false);
        
        //Add scene to stage and show it
        //stage.setScene(new Scene(root,width,height));
        //stage.show();
=======
        );

        //Add elements to grid
        DeckView deckView=new DeckView(height,width);
        root.add(deckView, 166, 110);
        /*Add more elements here*/
        
        //Stage settings
        stage.setTitle("Base Game");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(new Scene(root,width,height));
        stage.show();
>>>>>>> remotes/origin/alpha
    }

    public static void main(String[] args) {
        //3 2 1 and launch 🚀
        launch(args);
    }

}
