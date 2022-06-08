package gwint;


import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenu implements Initializable {

    @FXML
    private BorderPane mainMenuPane;
    @FXML
    private StackPane stackPane;
    @FXML
    private VBox settingsPane;
    @FXML
    private Slider musicVolumeSlider;
    @FXML
    private Slider effectsVolumeSlider;


    public void handlePlayButtonAction(ActionEvent e){
        App.switchScene("DeckBuilder");
    }

    public void handleSettingsButtonAction(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("Settings.fxml"));
        settingsPane = loader.load();

        settingsPane.setOpacity(0);
        stackPane.getChildren().add(settingsPane);


        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), mainMenuPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), settingsPane);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        ParallelTransition fade = new ParallelTransition(fadeIn, fadeOut);
        fade.play();

        new Thread(() -> {
            try {
                Thread.sleep(500);
                Platform.runLater(() -> mainMenuPane.setDisable(true));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();

    }


    public void handleExitSettingsButtonAction(ActionEvent e) throws IOException {
        Pane parent = App.getRoot();
        Pane mainMenu = (Pane) parent.getChildren().get(0);
        mainMenu.setOpacity(0.15);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(500), mainMenu);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(500), settingsPane);
        fadeOut.setFromValue(1);
        fadeOut.setToValue(0);

        mainMenu.setDisable(false);

        ParallelTransition fade = new ParallelTransition(fadeIn, fadeOut);
        fade.play();

        new Thread(() -> {
            try {
                Thread.sleep(500);
                Platform.runLater(() -> parent.getChildren().remove(settingsPane));
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public void handleExitButtonAction(ActionEvent e){
        App.exit();
    }

    public void handleEntered(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1.2);
        btnTrans.setToY(1.2);
        btnTrans.play();
    }

    public void handleExited(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1);
        btnTrans.setToY(1);
        btnTrans.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (this.musicVolumeSlider == null) return;

        musicVolumeSlider.valueProperty().setValue(100);
        musicVolumeSlider.valueProperty().addListener((observableValue, number, t1) ->
                Platform.runLater(() -> Sounds.backgroundMusicPlayer.setVolume(musicVolumeSlider.getValue()/100)));

        effectsVolumeSlider.valueProperty().setValue(100);
        effectsVolumeSlider.valueProperty().addListener((observableValue, number, t1) ->
                Platform.runLater(() -> Sounds.soundEffectVolume = effectsVolumeSlider.getValue()));
    }
}
