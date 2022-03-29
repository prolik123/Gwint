package gwint;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;


public class DeckView extends HBox {
    private static final String DECK_STYLE="-fx-background-color: transparent;";
    public DeckView(double height, double width) {
        Button deckEntity=new Button();
        deckEntity.setGraphic(new ImageView(new Image(App.class.getResource("back.png").toExternalForm())));
        double C=1990/3366;
        deckEntity.setScaleX((0.6)+C*height/width);
        deckEntity.setScaleY((0.6)+C*height/width);
        deckEntity.setStyle(DECK_STYLE);

        getChildren().add(deckEntity);
    }
}   
