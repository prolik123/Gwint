package gwint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenu {
@FXML
    private Button playButton;
@FXML
    private Button hTPButton;
@FXML
    private Button exitButton;

public void handlePlayButtonAction(ActionEvent e){
    App.switchScene("BaseGame");
}
public void handleExitButtonAction(ActionEvent e){
    App.exit();
}




}
