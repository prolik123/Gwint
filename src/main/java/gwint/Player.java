package gwint;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    
    /// field for hand
    public BoardSlot myCards;

    /// the remaining cards from decks
    public Stack<Card> myCardStack;

    /// Did the Player pass ?
    boolean myPass = false;

    /// Current value on board for current player
    int myBoardValue = 0;

    /// Array of rows of board
    public BoardSlot myBoard[] = new BoardSlot[3];
    int startNumberOfCards = GameEngine.defultNumberOfCards;


    /// Basic Constructor for each field
    Player() {
        myCards = new BoardSlot();
        myCardStack = new Stack<>();
        myCards.cardList = JsonCardParser.getCardsList();
        getListPermutation(myCards.cardList);
        chooseHandCards(myCards.cardList, myCardStack);
        myCards.hand = true;
        for(int k=0;k<3;k++)
            myBoard[k] = new BoardSlot();
        myCards.Boards = myBoard;
    }

    /// Function which excess( more than the start number of cards ) cards adds on Stack 
    private void chooseHandCards(List<Card> currList, Stack<Card> currStack){
        while(currList.size() > startNumberOfCards) {
            currStack.add(currList.get(currList.size()-1));
            currList.remove(currList.size()-1);
        }
    }

    /// Get a random permutation of the list
    public static void getListPermutation(List<?> currList){
        Collections.shuffle(currList);
    }

    /// If player has a card on hand and didn't passed it will throw random card (80%) or pass (20%)
    void move() {

        if(myCards.cardList.size()!=0 && !myPass) {
            /*if(GameEngine.human.myPass) {
                try {
                    TimeUnit.SECONDS.sleep(1);
                }
                catch (Exception e) {
                    
                }
            }*/
            Random makePass = new Random();
            if(makePass.nextInt(5) == 0)
                getPass();
            else {
                throwCard(myCards.cardList.get(0));
                myCards.cardList.remove(0);
            }
        }
        else if(!myPass)
            getPass();
        if(GameEngine.human.myPass && !GameEngine.opponent.myPass)
            move();
    }

    /// Function which gets a card and add it to current player View
    void throwCard(Card card) {
        Image current = new Image(App.class.getResource(Card.cardPathPrefix+card.imageLink).toExternalForm());
        ImageView ImView = new ImageView(current);
        Button btn = new Button();
        btn.setGraphic(ImView);
        ImView.setFitHeight(200*BoardSlot.ratio);
        ImView.setFitWidth(150*BoardSlot.ratio);
        btn.setStyle(BoardSlot.DECK_STYLE);
        myBoardValue += card.value;
        myBoard[card.boardType].getCurentBoardView().getChildren().add(btn);
        myBoard[card.boardType].getCurentBoardView().setSpacing(1/BoardSlot.ratio);
        myBoard[card.boardType].value+= card.value;
    }

    /// Function will pass...
    void getPass(){
        myPass = true;
        /// ... wypisz odpowiednio passa 


        if(this == GameEngine.human)
            GameEngine.opponent.move();
    }
    
}
