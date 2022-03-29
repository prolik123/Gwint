/*
 * Creates a deck of cards to placed on table
 */

package gwint;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;


public class DeckView extends HBox {
    private static final String DECK_STYLE="-fx-background-color: transparent;";
    public DeckView(double height, double width) {
        //For now it's a button
        Button deckEntity=new Button();
        deckEntity.setGraphic(new ImageView(new Image(App.class.getResource("back.png").toExternalForm())));

        //To be rethinked later
        deckEntity.setScaleX((225.1/150.1)*width/3366.1);
        deckEntity.setScaleY((225.1/150.1)*width/3366.1);
        deckEntity.setStyle(DECK_STYLE);

        //Add it to layout
        getChildren().add(deckEntity);
    }
}   
