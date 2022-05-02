/*
 * PO Projekt - Karcianka chwilowo bez nazwy
 * @author Hurkala Binieda Sanocki
 * 
 */

package gwint;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;
import javafx.scene.text.Font;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.IOException;

public class App extends Application {
    private static Stage stage;
    private static FXMLLoader loader;
    private static Parent root;


    public static void switchScene(String rootName)
    {
        try
        {
            if(rootName.equals("MainMenu")) {
                loader = new FXMLLoader(App.class.getResource("MainMenu.fxml"));
                root = loader.load();
                stage.setTitle("Main Menu");
            }

            else if(rootName.equals("BaseGame"))
            {
                //
                //proponuje przenieść to do GameEngine
                //
                root = new GridPane();
                //root.setGridLinesVisible(true);

                for(int i=0;i<200;i++) {
                    ColumnConstraints column = new ColumnConstraints();
                    column.setPercentWidth(0.5);
                    ((GridPane) root).getColumnConstraints().addAll(column);

                    RowConstraints row = new RowConstraints();
                    row.setPercentHeight(0.5);
                    ((GridPane) root).getRowConstraints().addAll(row);
                }
                new GameEngine((GridPane) root);

                /*Add more elements here*/

                //Stage settings

            }
            stage.setFullScreen(true);
            stage.setResizable(false);
            stage.getScene().setRoot(root);
            stage.show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public void start(Stage stage) throws IOException {
        App.stage = stage;

        //Get screen dimentions and set ratio
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height=screenBounds.getHeight();
        double width=screenBounds.getWidth();
        double ratio=Math.sqrt((width*height))/2300; //Const to scale cards etc. Lowered it btw


        new Constants(ratio);
        try {

            loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            root =  loader.load();

        }catch (IOException e)
        {
            e.printStackTrace();
        }
        
        //Stage settings
        stage.setTitle("Main Menu");
        stage.setFullScreen(true);
        stage.setResizable(false);
        stage.setScene(new Scene(root,width,height));
        stage.show();
    }

    public static void exit(){
        Platform.exit();
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