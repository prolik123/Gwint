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
import javafx.stage.Stage;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;
import javafx.scene.layout.*;


import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.IOException;

public class App extends Application {
    private static Stage stage;
    private static FXMLLoader loader;
    private static Parent root;


    //This method changes scenes 
    //menu->game and game->menu
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
                root=new StackPane();

                new GameEngine((StackPane)root);
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

        //This here gets the screen resolution and saves it into Constants
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        double height=screenBounds.getHeight();
        double width=screenBounds.getWidth();

        new Constants(width,height);

        //We start in the menu
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

    //Geralt: bywaj
    //Talar: ja ... jestem a nie bywam
    //In other words, closes the app
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