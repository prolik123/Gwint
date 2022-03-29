package gwint;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.image.*;


public class DeckView extends HBox {
    private static final String DECK_STYLE="-fx-background-color: transparent;";
    public DeckView(double height, double width) {
        Button deckEntity=new Button();
        deckEntity.setGraphic(new ImageView(new Image(App.class.getResource("back.png").toExternalForm())));
        double C=width/3366;
        double CC=1990*C;
        deckEntity.setScaleX((225.1/150.1)*width/3366.1);
        deckEntity.setScaleY((225.1/150.1)*width/3366.1);
        deckEntity.setStyle(DECK_STYLE);

        getChildren().add(deckEntity);
    }
}   
