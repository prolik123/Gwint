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
    BoardView currentView;
    BoardSlot Boards[];

    BoardSlot() {
        cardList = new ArrayList<>();
        value = 0;
    }
    public void setBoards(BoardSlot[] B) {
        if(!hand)
            return;
        Boards = B;
    }

    public void addCardToBoardSlot(Card card)  {
        cardList.add(card);
        value += card.value;
    }
    

    public BoardView getNewBoardView(double ratio) {
        currentView = new BoardView(ratio);
        return currentView;
    }

    public BoardView getCurentBoardView(){
        return currentView;
    }

    public class BoardView extends HBox {
        private static final String DECK_STYLE="-fx-background-color: transparent;";
        public BoardView(double ratio) {
            for(Card card : cardList) {
                addCardToBoardView(card, this);
            }
        }
    }

    public void addCardToBoardView(Card card,BoardView View) {

        Image currentIm = new Image(App.class.getResource(Card.cardPathPrefix+card.imageLink).toExternalForm());
        ImageView ImView = new ImageView(currentIm);
        Button btn = new Button();
        btn.setGraphic(ImView);
        if(hand){
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    View.getChildren().remove(btn);
                    Button copyButton = new Button();
                    copyButton.setGraphic(ImView);
                    copyButton.setStyle(BoardView.DECK_STYLE);
                    value += card.value;
                    Boards[card.boardType].getCurentBoardView().getChildren().add(copyButton);
                    Boards[card.boardType].value+= card.value;
                    cardList.remove(card);
                }
            });
        }
        btn.setStyle(BoardView.DECK_STYLE);
        View.getChildren().add(btn);
    }
}
