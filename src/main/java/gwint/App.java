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


    public static void switchScene(String rootName)
    {
        try
        {
            if(rootName.equals("MainMenu.fxml")) {
                loader = new FXMLLoader(App.class.getResource(rootName));
                root = (Parent) loader.load();
                stage.setTitle("Main Menu");
            }

            else if(rootName.equals("BaseGame"))
            {
                root=new BorderPane();

                new GameEngine((BorderPane)root);
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

        new Constants(ratio,width,height);
        try {
            loader = new FXMLLoader(getClass().getResource("MainMenu.fxml"));
            root = (Parent) loader.load();
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