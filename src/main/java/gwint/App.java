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
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;

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
        //root.setAlignment(Pos.BOTTOM_RIGHT);
        //root.setHgap(500);
        //root.setVgap(500);
        root.setGridLinesVisible(true);

        for(int i=0;i<100;i++) {
            ColumnConstraints col1 = new ColumnConstraints();
            col1.setPercentWidth(1);
            //ColumnConstraints col2 = new ColumnConstraints();
            //col2.setPercentWidth(50);
            root.getColumnConstraints().addAll(col1);

            RowConstraints row1 = new RowConstraints();
            row1.setPercentHeight(1);
            //RowConstraints row2 = new RowConstraints();
            //row2.setPercentHeight(50);
            root.getRowConstraints().addAll(row1);
        }
        
        String image = App.class.getResource("plansza.png").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: contain" 
            //MOZNA ZMIENIC NA CONTAIN 
        );
        stage.setResizable(false);

        //Add deck to root
        DeckView deckView=new DeckView(height,width);
        root.add(deckView, 83, 55);
        
        //Add scene to stage and show it
        stage.setScene(new Scene(root,width,height));
        stage.show();
    }
    
    public static void main(String[] args) {
        //3 2 1 and launch 🚀
        launch(args);
        
    }

}