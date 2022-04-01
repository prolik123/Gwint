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

    /// List of Cards representing Cards on hand ( else null )
    public List<Card> cardList;

    /// value of all boards for hand
    public int value = 0;

    /// does this Boards Slot representing hand
    boolean hand = false;

    /// View for current BoardSlot (it's null while you dont invoke getNewBoardView )
    BoardView currentView;

    /// If current boards slot is hand it represend the rows on boards
    BoardSlot Boards[];

    /// ratio for scaling images
    static double ratio;

    /// Constant for style
    public static final String DECK_STYLE="-fx-background-color: transparent;";

    BoardSlot() {
        cardList = new ArrayList<>();
        value = 0;
    }

    /// Create and return new BoardView for current BoardSlot, (ratio for images scalling )
    public BoardView getNewBoardView(double ratio) {
        currentView = new BoardView(ratio);
        return currentView;
    }

    /// returns Current BoardView ( if eariler function getNewBoardView wasn't invoke it's return null)
    public BoardView getCurentBoardView(){
        return currentView;
    }

    /// BoardView Class
    public class BoardView extends HBox {

        /// Constructor (each card in cardList is added to View)
        public BoardView(double ratio) {
            BoardSlot.ratio = ratio;
            for(Card card : cardList) {
                addCardToBoardView(card, this);
            }
        }
    }

    /// for given Viev and Card it will add it to the View (some of features are only for hands BoardSlot)
    public void addCardToBoardView(Card card,BoardView View) {

        Image current = new Image(App.class.getResource(Card.cardPathPrefix+card.imageLink).toExternalForm());
        ImageView ImView = new ImageView(current);
        Button btn = new Button();
        btn.setGraphic(ImView);
        ImView.setFitHeight(200*ratio);
        ImView.setFitWidth(150*ratio);
        /// if its hand give the Button actions and hovers
        if(hand){
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(!GameEngine.human.myPass) {
                        View.getChildren().remove(btn);
                        Button copyButton = new Button();
                        copyButton.setGraphic(ImView);
                        copyButton.setStyle(BoardSlot.DECK_STYLE);
                        value += card.value;
                        Boards[card.boardType].getCurentBoardView().getChildren().add(copyButton);
                        Boards[card.boardType].getCurentBoardView().setSpacing(1/ratio);
                        Boards[card.boardType].value+= card.value;
                        cardList.remove(card);
                        if(cardList.isEmpty()) {
                            GameEngine.human.getPass();
                        }
                        GameEngine.opponent.move();
                    }
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
        btn.setStyle(BoardSlot.DECK_STYLE);
        View.getChildren().add(btn);
        View.setSpacing((1/ratio));
    }
}
