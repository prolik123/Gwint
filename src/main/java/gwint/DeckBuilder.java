package gwint;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeckBuilder implements Initializable {
    @FXML
    //TilePane tilePane;
    TilePane tilePane;
    @FXML
    Button playButton;
    @FXML
    Button backButton;

    private List <Card> cards;

    private final int howMany = 3;
    private int howManySelected = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tilePane.setAlignment(Pos.CENTER);
        cards = JsonCardParser.getCardsList();

        for(Card card : cards) {
            Button currentButton = card.genCardView();
            currentButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(!card.selected && howManySelected < howMany) {
                        card.selected = true;
                        howManySelected++;
                        Animations.scaleTo(currentButton, 1.2, 1.2, 100);
                    }
                    else if(card.selected) {
                        card.selected = false;
                        howManySelected--;
                        Animations.scaleTo(currentButton, 1, 1, 100);
                    }
                }
            });
            currentButton.setScaleX(1.25);
            currentButton.setScaleY(1.25);
            tilePane.getChildren().add(currentButton);
        }
    }


    public void handlePlayButtonAction(ActionEvent event) {
        App.switchScene("BaseGame");
    }
    public void handleBackButtonAction(ActionEvent event) {
        App.switchScene("MainMenu");
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
}
