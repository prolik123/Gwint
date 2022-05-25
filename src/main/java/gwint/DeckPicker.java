package gwint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DeckPicker implements Initializable {
    @FXML
    //TilePane tilePane;
    TilePane tilePane;

    private List <Card> cards;

    private final int howMany = 3;
    private int howManySelected = 0;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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
}
