package gwint;

import java.util.*;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player {
    
    public BoardSlot myCards;
    public Stack<Card> myCardStack;
    boolean myPass = false;
    int myBoardValue = 0;
    public BoardSlot myBoard[] = new BoardSlot[3];
    int startNumberOfCards = GameEngine.defultNumberOfCards;


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

    void move() {
        if(myCards.cardList.size()!=0 && !myPass) {
            Random makePass = new Random();
            if(makePass.nextInt(5) == 0)
                getPass();
            else {
                throwCard(myCards.cardList.get(0));
                myCards.cardList.remove(0);
            }
        }
    }

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

    void getPass(){
        myPass = true;
        /// ... wypisz odpowiednio passa 
    }
    
}
