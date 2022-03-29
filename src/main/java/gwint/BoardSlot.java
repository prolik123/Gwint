package gwint;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class BoardSlot {
    public List<Card> cardList;
    public int value = 0;
    boolean hand = false;
    BoardView curentView;
    BoardSlot Bords[];
    final String cardPathPrefix = "Cards/";

    BoardSlot() {
        cardList = new ArrayList<>();
        value = 0;
    }
    public void setBords(BoardSlot[] B) {
        if(!hand)
            return;
        Bords = B;
    }

    public void addCardToBoardSlot(Card card)  {
        cardList.add(card);
        value += card.value;
    }

    public BoardView getNewBoardView(double ratio) {
        curentView = new BoardView(ratio);
        return curentView;
    }

    public BoardView getCurentBoardView(){
        return curentView;
    }

    class BoardView extends HBox {
        private static final String DECK_STYLE="-fx-background-color: transparent;";
        public BoardView(double ratio) {
            for(Card card : cardList) {
                
                Image current = new Image(App.class.getResource(cardPathPrefix+card.imageLink).toExternalForm());
                ImageView ImView = new ImageView(current);
                Button btn = new Button();
                btn.setGraphic(ImView);
                if(hand){
                    btn.setOnAction(new EventHandler<ActionEvent>() {

                        @Override
                        public void handle(ActionEvent event) {
                            getChildren().remove(btn);
                            value += card.value;
                            Bords[card.boardType].getCurentBoardView().getChildren().add(btn);
                            Bords[card.boardType].value+= card.value;
                            cardList.remove(card);
                        }
                    });
                }
                btn.setStyle(DECK_STYLE);
                getChildren().add(btn);
            }
        }
    }
}
