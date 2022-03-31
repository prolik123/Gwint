package gwint;

import java.util.*;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class GameEngine {
    
    public BoardSlot myCards;
    public BoardSlot opponentCards;
    public Stack<Card> myCardStack;
    public Stack<Card> opponentCardStack;
    boolean myPass = false;
    boolean opponentPass = false;
    int myBoradValue = 0;
    int opponentBoardValue = 0;
    int startNumberOfCards = 7;
    private static final String DECK_STYLE="-fx-background-color: transparent;";
    final String cardPathPrefix = "Cards/";

    public BoardSlot myBoard[] = new BoardSlot[3];
    public BoardSlot opponentBoard[] = new BoardSlot[3];

    GameEngine() {
        
        myCards = new BoardSlot();
        myCardStack = new Stack<>();
        myCards.cardList = JsonCardParser.getCardsList();
        getListPermutation(myCards.cardList);
        chooseHandCards(myCards.cardList, myCardStack);
        myCards.hand = true;
        for(int k=0;k<3;k++)
            myBoard[k] = new BoardSlot();
        myCards.setBords(myBoard);

        opponentCards = new BoardSlot();
        opponentCardStack = new Stack<>();
        opponentCards.cardList = JsonCardParser.getCardsList();
        getListPermutation(opponentCards.cardList);
        chooseHandCards(opponentCards.cardList, opponentCardStack);

    }

    /// getting startNumberOfCards to list and the rest lies on Stack
    private void chooseHandCards(List<Card> currList, Stack<Card> currStack){
        while(currList.size() > startNumberOfCards) {
            currStack.add(currList.get(currList.size()-1));
            currList.remove(currList.size()-1);
        }
    }

    /// Get a random permutation of the list
    public void getListPermutation(List<?> currList){
        Collections.shuffle(currList);
    }

    public void addCardToHand(BoardSlot Board,Stack<Card> Stack){
        if(Stack.empty())
            return;
        Card New = Stack.lastElement();
        Stack.pop();
        Board.cardList.add(New);
        Image current = new Image(App.class.getResource(cardPathPrefix+New.imageLink).toExternalForm());
        ImageView ImView = new ImageView(current);
        Button btn = new Button();
        btn.setGraphic(ImView);
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                Board.curentView.getChildren().remove(btn);
                Button copyButton = new Button();
                copyButton.setGraphic(ImView);
                copyButton.setStyle(DECK_STYLE);
                Board.value += New.value;
                Board.Bords[New.boardType].getCurentBoardView().getChildren().add(copyButton);
                Board.Bords[New.boardType].value+= New.value;
                Board.cardList.remove(New);
            }
        });
        btn.setStyle(DECK_STYLE);
        Board.curentView.getChildren().add(btn);
    }

}

