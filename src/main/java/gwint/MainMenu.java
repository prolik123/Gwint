package gwint;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.input.MouseEvent;

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
public void handleEntered(MouseEvent e){
    Animations.scaleTo((Node)e.getSource(), 1.2, 1.2, 100);
}
public void handleExited(MouseEvent e){
    Animations.scaleTo((Node)e.getSource(), 1, 1, 100);
}




}
