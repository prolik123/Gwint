package gwint;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

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
            setSpacing(Constants.cardDefaultSpacing);
            for(Card card : cardList) {
                addCardToBoardView(card, this);
            }
        }
    }

    /// for given Viev and Card it will add it to the View (some of features are only for hands BoardSlot)
    public void addCardToBoardView(Card card,BoardView View) {
        Button btn = card.genCardView();

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
                    }
                }
            });

            btn.setOnMouseEntered(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    if(!GameEngine.human.myPass) {
                        Animations.scaleTo(btn, 1.2, 1.2, 100);
                    }
                }
            });

            btn.setOnMouseExited(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent arg0) {
                    Animations.scaleTo(btn, 1, 1, 100);
                }
            });
        }
        btn.setStyle(Constants.DECK_STYLE);
        View.getChildren().add(btn);

        //If cards take up more then 3/4 of the screen squize them
        double diff=0.75*Constants.width-(((Constants.height-84.0)/7.0/200.0)*150.0+Constants.cardDefaultSpacing)*View.getChildren().size();
        if(diff<0) View.setSpacing(Constants.cardDefaultSpacing+diff/(double)View.getChildren().size());
        else View.setSpacing(Constants.cardDefaultSpacing);
    }
}
