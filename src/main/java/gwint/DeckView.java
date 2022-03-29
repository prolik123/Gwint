package gwint;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;


public class DeckView extends HBox {
    private static final String DECK_STYLE="-fx-background-color: transparent;";
    public DeckView(double ratio) {
        Button deckEntity=new Button();
        deckEntity.setGraphic(new ImageView(new Image(App.class.getResource("back.png").toExternalForm())));
        deckEntity.setScaleX(ratio);
        deckEntity.setScaleY(ratio);
        deckEntity.setStyle(DECK_STYLE);

        getChildren().add(deckEntity);
    }
}   
