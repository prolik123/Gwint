/*
 * Creates a deck of cards to placed on table
 */

package gwint;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;


public class DeckView extends HBox {
    
    public DeckView(double ratio) {
        //For now it's a buttons
        Button deckEntity=new Button();
        ImageView imView=new ImageView(new Image(App.class.getResource("back.png").toExternalForm()));
        imView.setFitHeight(200*ratio);
        imView.setFitWidth(150*ratio);
        deckEntity.setGraphic(imView);
        deckEntity.setStyle(Constants.DECK_STYLE);
        deckEntity.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(!GameEngine.addCardToHand(GameEngine.human.myCards, GameEngine.human.myCardStack)) {
                    deckEntity.setVisible(false);
                }
            }
        });


        //Add it to layout
        getChildren().add(deckEntity);
    }
}   
