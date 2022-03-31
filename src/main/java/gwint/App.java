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
        double ratio=(225.1/170.1)*width/3366.1; //Const to scale cards etc. Lowered it btw

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

        root.setGridLinesVisible(true);
        
        //Set background
        String image = App.class.getResource("plansza.png").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: contain" 
            //MOZNA ZMIENIC NA cover 
        );

        //Initialize the Game Engine
        GameEngine engine = new GameEngine();

        //Add elements to grid
        DeckView deckView=new DeckView(ratio,engine);
        deckView.toFront();
        root.add(deckView, 166, 110);
        
        for(int k=0;k<3;k++) root.add(engine.myBoard[k].getNewBoardView(ratio),67,148-26*k);
        root.add(engine.myCards.getNewBoardView(ratio),52,174);
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
