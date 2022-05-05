package gwint;

import java.util.*;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Duration;

public class BoardSlot {

    /// List of Cards representing Cards on hand ( else null )
    public List<Card> cardList;

    /// does this Boards Slot representing hand
    boolean hand = false;

    /// View for current BoardSlot (it's null while you dont invoke getNewBoardView )
    BoardView currentView;

    BoardSlot() {
        cardList = new ArrayList<>();
    }

    /// Create and return new BoardView for current BoardSlot, (ratio for images scalling )
    public BoardView getNewBoardView() {
        currentView = new BoardView();
        return currentView;
    }

    /// returns Current BoardView ( if eariler function getNewBoardView wasn't invoke it's return null)
    public BoardView getCurentBoardView(){
        return currentView;
    }

    /// BoardView Class
    public class BoardView extends HBox {

        /// Constructor (each card in cardList is added to View)
        public BoardView() {
            for(Card card : cardList) {
                addCardToBoardView(card, this);
            }
        }
    }

    /// for given Viev and Card it will add it to the View (some of features are only for hands BoardSlot)
    public void addCardToBoardView(Card card,BoardView View) {
        Image current = new Image(App.class.getResource(Constants.cardPathPrefix+card.imageLink).toExternalForm());
        ImageView ImView = new ImageView(current);
        Button btn = new Button();
        btn.setGraphic(ImView);
        ImView.setFitHeight((Constants.height-84.0)/7.0);
        ImView.setFitWidth(((Constants.height-84.0)/7.0/200.0)*150.0);
        ImView.maxHeight((Constants.height-84.0)/7.0);
        ImView.maxWidth(((Constants.height-84.0)/7.0/200.0)*150.0);

        /// if its hand give the Button actions and hovers
        if(hand){
            btn.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(!GameEngine.human.myPass && GameEngine.ablePlayerMove) {
                        View.getChildren().remove(btn);
                        cardList.remove(card);
                        GameEngine.human.throwCard(card);
                        GameEngine.ablePlayerMove = false;            
                        GameEngine.opponent.ThreadMove(1000);
                    }
                }
            });

            btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    if(!GameEngine.human.myPass) {
                        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), btn);
                        btnTrans.setToX(1.2);
                        btnTrans.setToY(1.2);
                        btnTrans.play();
                    }
                }
            });

            btn.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), btn);
                    btnTrans.setToX(1);
                    btnTrans.setToY(1);
                    btnTrans.play();
                }
            });
        }
        btn.setStyle(Constants.DECK_STYLE);
        View.getChildren().add(btn);
        View.setSpacing(-15);
    }
}
