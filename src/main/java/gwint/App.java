/*
 * PO Projekt - Karcianka chwilowo bez nazwy
 * @author Hurkala Binieda Sanocki
 * 
 */

package gwint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.IOException;

public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        //Get screen dimentions and set ratio
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height=screenBounds.getHeight();
        double width=screenBounds.getWidth();
        double ratio=Math.sqrt((width*height))/2300; //Const to scale cards etc. Lowered it btw

        new Constants(ratio);
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
        new GameEngine(root);


        /*Add more elements here*/
        
        //Stage settings
        stage.setTitle("Base Game");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(new Scene(root,width,height));
        stage.show();
    }

    public static void main(String[] args) {
        //Background music player 🔊
        Media backgroundMusic=new Media(new File("sound/background.mp3").toURI().toString());
        MediaPlayer player=new MediaPlayer(backgroundMusic);
        player.play();

        //3 2 1 and launch 🚀
        launch(args);
    }

}