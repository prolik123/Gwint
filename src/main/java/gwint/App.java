/*
 * PO Projekt - Karcianka chwilowo bez nazwy
 * @author Hurkala Binieda Sanocki
 * 
 */

package gwint;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.layout.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

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


        BorderPane newRoot=new BorderPane();

        String image = App.class.getResource("sth.jpg").toExternalForm();
        newRoot.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: auto 100%; " +
            "-fx-font-family: MedievalSharp; " +
            "-fx-font-size: 20 "
        );

        BorderPane centerPane=new BorderPane();
        VBox centerBox=new VBox(10);
        centerBox.setMinWidth(width/1.5);
        centerBox.setMaxWidth(width/1.5);
        //centerBox.setMinHeight(700);
        centerBox.setMaxHeight(height-height/7.0-84.0);

        for(int i=0;i<6;i++) {
            HBox secondBox=new HBox();
            HBox.setMargin(secondBox, new Insets(12,12,12,12)); // optional

            secondBox.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
            secondBox.setOpacity(0.5);
            secondBox.setMinWidth(width/1.5);
            secondBox.setMaxWidth(width/1.5);
            secondBox.setMinHeight((height-84)/7.0);
            secondBox.setMaxHeight((height-84)/7.0);

            centerBox.getChildren().add(secondBox);
        }

        centerPane.setCenter(centerBox);
        BorderPane.setAlignment(centerBox, Pos.CENTER);

        HBox firstBox=new HBox();
        firstBox.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
        firstBox.setMinWidth(width/1.5);
        firstBox.setMaxWidth(width/1.5);
        firstBox.setMinHeight((height-84.0)/7.0);
        firstBox.setMaxHeight((height-84.0)/7.0);

        BorderPane.setAlignment(firstBox, Pos.BOTTOM_CENTER);
        centerPane.setBottom(firstBox);

        newRoot.setCenter(centerPane);

        System.out.println(width+" "+height);

        PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
        perspectiveTrasform.setUlx(width/14.0); //gora lewy
        perspectiveTrasform.setUly(0.0); //gora lewy
        perspectiveTrasform.setUrx(width/1.5-width/14.0); //gora prawy
        perspectiveTrasform.setUry(0.0); //gora prawy
        perspectiveTrasform.setLrx(width/1.5); //dol prawy
        perspectiveTrasform.setLry(height-(height/7.0)-24); //dol prawy
        perspectiveTrasform.setLlx(0.0); //dol lewy
        perspectiveTrasform.setLly(height-(height/7.0)-24); //dol lewy

        centerBox.setEffect(perspectiveTrasform);

        VBox rightBox=new VBox();
        rightBox.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
        rightBox.setMinWidth(100);
        rightBox.setMaxWidth(100);
        rightBox.setMinHeight(height);
        rightBox.setMaxHeight(height);
        newRoot.setRight(rightBox);

        VBox leftBox=new VBox();
        leftBox.setMinWidth(100);
        leftBox.setMaxWidth(100);
        leftBox.setMinHeight(height);
        leftBox.setMaxHeight(height);
        newRoot.setLeft(leftBox);
        
        //Stage settings
        stage.setTitle("Base Game");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(new Scene(newRoot,width,height));
        stage.show();
    }

    public static void main(String[] args) {
        //Background music player 🔊
        Media backgroundMusic=new Media(new File(Constants.backgroundSoundPath).toURI().toString());
        MediaPlayer player=new MediaPlayer(backgroundMusic);
        player.play();

        //3 2 1 and launch 🚀
        launch(args);
    }

}