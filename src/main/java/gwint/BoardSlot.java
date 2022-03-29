package gwint;

import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BoardSlot {
    public List<Card> cardList;
    public int value = 0;

    BoardSlot() {
        cardList = new ArrayList<>();
        value = 0;
    }
    public void addCardToBoardSlot(Card card)  {
        cardList.add(card);
        value += card.value;
    }

    class BoardView extends HBox {
        private static final String DECK_STYLE="-fx-background-color: transparent;";
        public BoardView(double ratio) {
            for(Card card : cardList) {
                System.out.println(card);
                Image current = new Image(App.class.getResource(card.imageLink).toExternalForm());
                ImageView ImView = new ImageView(current);
                Button btn = new Button();
                btn.setGraphic(ImView);
                getChildren().add(btn);
            }
        }
    }
}
