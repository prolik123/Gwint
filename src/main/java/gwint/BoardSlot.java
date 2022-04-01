package gwint;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;

public class BoardSlot {
    public List<Card> cardList;
    public int value = 0;
    boolean hand = false;
    BoardView currentView;
    BoardSlot Boards[];
    static double ratio;

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
            BoardSlot.ratio = ratio;
            for(Card card : cardList) {
                addCardToBoardView(card, this);
            }
        }
    }

    public void addCardToBoardView(Card card,BoardView View) {

        Image current = new Image(App.class.getResource(Card.cardPathPrefix+card.imageLink).toExternalForm());
        ImageView ImView = new ImageView(current);
        Button btn = new Button();
        btn.setGraphic(ImView);
        ImView.setFitHeight(200*ratio);
        ImView.setFitWidth(150*ratio);
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
                    Boards[card.boardType].getCurentBoardView().setSpacing(1/ratio);
                    Boards[card.boardType].value+= card.value;
                    cardList.remove(card);
                }
            });

            btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    //Niech ktos to zanimuje prosze
                    btn.setScaleX(1.2);
                    btn.setScaleY(1.2);
                }
            });

            btn.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    btn.setScaleX(1);
                    btn.setScaleY(1);
                }
            });
        }
        btn.setStyle(BoardView.DECK_STYLE);
        View.getChildren().add(btn);
        View.setSpacing((1/ratio));
    }
}
