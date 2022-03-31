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
import javafx.scene.layout.*;

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

        //Create 600x400 grid
        GridPane root = new GridPane();
        //root.setGridLinesVisible(true);

        for(int i=0;i<400;i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(0.25);
            root.getRowConstraints().addAll(row);
        }
        for(int i=0;i<800;i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(0.125);
            root.getColumnConstraints().addAll(column);
        }
        
        //Set background
        String image = App.class.getResource("plansza.png").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: contain" 
            //MOZNA ZMIENIC NA cover 
        );
        GameEngine engine = new GameEngine();

        //root.add(engine.myCards.getNewBoardView(1000),0,300);
        //for(int k=0;k<3;k++)
          //  root.add(engine.myBoard[k].getNewBoardView(1000),0,30+30*k);

        

        //Add elements to grid
        DeckView deckView=new DeckView(height,width,engine);
        root.add(deckView, 657,219);
        /*Add more elements here*/
        
        //Stage settings
        stage.setTitle("Base Game");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(new Scene(root,width,height));
        stage.show();
    }

    public static void main(String[] args) {
        //3 2 1 and launch 🚀
        launch(args);
    }

}
