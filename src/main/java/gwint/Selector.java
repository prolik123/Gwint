package gwint;

import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class Selector extends VBox{
    public Selector(List<Card> list) {
        setMargin(this, new Insets(10));
        setMinHeight(Constants.height);
        setMinWidth(Constants.width);
        setMaxHeight(Constants.height);
        setMaxWidth(Constants.width);
        setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.75),null,null)));
        setAlignment(Pos.CENTER);

        Text selectorText=new Text("Select 2 cards to be discarded");
        selectorText.setFont((Font.font("MedievalSharp",32)));
        selectorText.setFill(Color.WHITE);

        Button selectorBtn=new Button("Accept");
        
        HBox selectorCards=new HBox();
        selectorCards.setAlignment(Pos.CENTER);

        for(Card card:list) {
            selectorCards.getChildren().add(card.genCardView());
        }

        getChildren().addAll(selectorText,selectorCards,selectorBtn);
    }
}
