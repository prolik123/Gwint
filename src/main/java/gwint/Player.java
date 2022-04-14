package gwint;

import java.util.*;
import java.util.concurrent.TimeUnit;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;

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
    int startNumberOfCards = Constants.defultNumberOfCards;
    
    public List<Heart> myHearts;

    int yOfHearts;


    /// Basic Constructor for each field
    Player(int locationOfHearts) {
        yOfHearts = locationOfHearts;
        myCards = new BoardSlot();
        myCardStack = new Stack<>();
        myCards.cardList = JsonCardParser.getCardsList();
        getListPermutation(myCards.cardList);
        chooseHandCards(myCards.cardList, myCardStack);
        myCards.hand = true;
        for(int k=0;k<3;k++)
            myBoard[k] = new BoardSlot();
        myCards.Boards = myBoard;
        myHearts = new ArrayList<>();
        for(int k=0;k<Constants.numberOfHearts;k++) {
            myHearts.add(new Heart());
            GameEngine.root.add(myHearts.get(k).currentImage, 35+5*k, locationOfHearts);
        }
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
        FadeTransition btnTrans=new FadeTransition(Duration.millis(500), btn);
        btnTrans.setFromValue(0.0);
        btnTrans.setToValue(1.0);
        btn.setGraphic(ImView);
        ImView.setFitHeight(200*Constants.ratio);
        ImView.setFitWidth(150*Constants.ratio);
        btn.setStyle(Constants.DECK_STYLE);
        myBoardValue += card.value;
        myBoard[card.boardType].getCurentBoardView().getChildren().add(btn);
        btnTrans.play();
        myBoard[card.boardType].getCurentBoardView().setSpacing(1/Constants.ratio);
        myBoard[card.boardType].value+= card.value;
        updateValue();
    }

    /// Function will pass...
    void getPass(){
        myPass = true;
        /// ... wypisz odpowiednio passa 
        if(this == GameEngine.human) {
            GameEngine.playerPass = new Pass(Constants.ratio);
            GameEngine.root.add(GameEngine.playerPass,11,119);
        }
        else if(this == GameEngine.opponent) {
            GameEngine.opponentPass = new Pass(Constants.ratio);
            GameEngine.root.add(GameEngine.opponentPass,11,46);
        }

        if(this == GameEngine.human)
            GameEngine.opponent.move();
        if(GameEngine.opponent.myPass && GameEngine.human.myPass) {
            GameEngine.startNewRound();
        }
    }

    void updateValue(){
        if(this == GameEngine.human) {
            GameEngine.humanValue.setText("" +myBoardValue);
        }
        else 
            GameEngine.opponentValue.setText("" + myBoardValue);
    }

    boolean looseHeart(){
        for(int k=0;k<myHearts.size();k++) {
            if(myHearts.get(k).on) {
                GameEngine.root.getChildren().remove(myHearts.get(k).currentImage);
                myHearts.get(k).getHeartOff();
                GameEngine.root.add(myHearts.get(k).currentImage,35+5*k,yOfHearts);
                if(k==myHearts.size()-1)
                    return false;
                break;
            }
            else if(k==myHearts.size()-1)
                return false;
        }
        return true;
    }

    class Heart {
        boolean on = true;
        ImageView currentImage;

        Heart() {
            currentImage = new ImageView(new Image(App.class.getResource("lifeOn.png").toExternalForm()));
        }

        void getHeartOff(){
            on = false;
            currentImage = new ImageView(new Image(App.class.getResource("lifeOff.png").toExternalForm()));
        }
    }
    
}
