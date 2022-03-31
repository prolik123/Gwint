/*
 * Creates a deck of cards to placed on table
 */

package gwint;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;


public class DeckView extends HBox {
    private static final String DECK_STYLE="-fx-background-color: transparent;s";
    public DeckView(double ratio, GameEngine ongoingGameEngine) {
        //For now it's a buttons
        Button deckEntity=new Button();
        ImageView imView=new ImageView(new Image(App.class.getResource("back.png").toExternalForm()));
        imView.setFitHeight(200*ratio);
        imView.setFitWidth(150*ratio);
        deckEntity.setGraphic(imView);
        deckEntity.setStyle(DECK_STYLE);

        //Add it to layout
        getChildren().add(deckEntity);
    }
}   
