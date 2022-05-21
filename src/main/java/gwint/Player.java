package gwint;

import java.util.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Player {
    
    /// field for hand
    public BoardSlot myCards;

    /// the remaining cards from decks
    public Stack<Card> myCardStack;

    /// Did the Player pass ?
    public boolean myPass = false;

    /// Current value on board for current player
    public int myBoardValue = 0;

    /// Array of rows of board
    public BoardSlot myBoard[] = new BoardSlot[Constants.numberOfBoards];

    public int startNumberOfCards = Constants.defultNumberOfCards;
    
    public List<Heart> myHearts;

    public Text playerValue;

    public HBox playerPass;

    public HBox playerHeart;

    /// Basic Constructor for each field
    Player() {

        setBasicCardsInfo();

        setBasicBoardsInfo();

        setBasicHeartsInfo();

        playerValue = new Text("0");
        playerValue.setFont(Font.font("MedievalSharp",48));
        playerValue.setFill(Color.WHITE);
        GameEngine.mainValues.getChildren().add(playerValue);
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

    void setBasicCardsInfo() {
        myCards = new BoardSlot();
        myCardStack = new Stack<>();
        myCards.cardList = JsonCardParser.getCardsList();
        getListPermutation(myCards.cardList);
        chooseHandCards(myCards.cardList, myCardStack);
    }

    void setBasicBoardsInfo() {
        myCards.hand = true;
        myCards.getNewBoardView();
        for(int k=0;k<Constants.numberOfBoards;k++)
            myBoard[k] = new BoardSlot();
    }

    void setBasicHeartsInfo() {
        playerHeart=new HBox(5);
        myHearts = new ArrayList<>();
        for(int k=0;k<Constants.numberOfHearts;k++) {
            myHearts.add(new Heart());
            playerHeart.getChildren().add(myHearts.get(k).currentImage);
        }
    }

    /// If player has a card on hand and didn't passed it will throw random card (80%) or pass (20%)
    void move() {
        if(!myCards.cardList.isEmpty() && !myPass) {
            Random makePass = new Random();
            if(makePass.nextInt(Constants.botRandomBounds) == 0)
                getPass();
            else {
                throwCard(myCards.cardList.get(0));
                myCards.cardList.remove(0);
            }
        }
        else if(!myPass)
            getPass();
        if(GameEngine.human.myPass && !GameEngine.opponent.myPass)
            GameEngine.opponent.ThreadMove(1000);
        GameEngine.ablePlayerMove = true;
    }

    /// Function which gets a card and add it to current player View
    void throwCard(Card card) {
        boolean cardThrowed = false;
        for(PlayInterface effect:card.effectArray)
            cardThrowed = cardThrowed || effect.playEffect(card, this);
        if(!cardThrowed)
            throwCardWithoutInterface(card);
        if(myCards.cardList.isEmpty())
            getPass();
    }

    void throwCardWithoutInterface(Card card) {
        Button btn=card.genCardView();
        myBoardValue += card.value;
        Platform.runLater(()->{myBoard[card.boardType].getCurentBoardView().getChildren().add(btn);});
        Platform.runLater(()->{myBoard[card.boardType].getCurentBoardView().setSpacing(-15);});
        updateValue();
    }

    /// Function will pass...
    void getPass(){
        myPass = true;
        if(this.equals(GameEngine.human)) GameEngine.passText.setText("PASSED");
        Platform.runLater(()->{playerPass.setVisible(true);;});

        if(this == GameEngine.human && GameEngine.ablePlayerMove) {
            GameEngine.opponent.ThreadMove(1000);
        }
        if(GameEngine.opponent.myPass && GameEngine.human.myPass) {
            GameEngine.startNewRoundThred();
        }
    }

    void updateValue(){
        playerValue.setText("" +myBoardValue);
    }

    void ThreadMove(long waitTime) {
        new Thread(()->
            {
                try{
                    Thread.sleep(waitTime);
                    move();
                }
                catch(Exception e) {

                }
            }).start();
    }

    boolean loseHeart(){
        for(int k=0;k<myHearts.size();k++) {
            if(myHearts.get(k).on) {
                playerHeart.getChildren().remove(myHearts.get(k).currentImage);
                myHearts.get(k).getHeartOff();
                playerHeart.getChildren().add(myHearts.get(k).currentImage);


                if(k==myHearts.size()-1)
                    return false;
                break;
            }
            else if(k==myHearts.size()-1)
                return false;
        }
        return true;
    }

    boolean hasOnHeart() {
        for(Heart t : myHearts) {
            if(t.on)
                return true;
        }
        return false;
    }

    void clearBoard() {
        for(int i=0;i<Constants.numberOfBoards;i++) {
            Animations.fadeOut(myBoard[i].currentView, Constants.fadeOutDuration);
            myBoard[i].cardList.clear();
        }

        new Thread(()->{
            try {
                Thread.sleep(500);
                Platform.runLater(()->{
                    printNewBoards();
                });
            } catch(Exception e){}
        }).start();
    }

    void preparePlayerForNextRound() {
        clearBoard();

        myBoardValue = 0;
        updateValue();
        if(!myCards.cardList.isEmpty()) {  
            playerPass.setVisible(false);
            //playerPass = null;
            myPass = false;
        }
    }

    void printNewBoards() {
        //My God, why have you forsaken me
        //I know it is a poor solution, but it works and doesn't really have
        //any negative impact on the app, soooooooo it stays for now
        if(this != GameEngine.human) {
            for(int k=Constants.numberOfBoards-1;k>=0;k--) {
                printBoxBoard(k);
            }
        } else {
            for(int k=0;k<Constants.numberOfBoards;k++) {
                printBoxBoard(k);
            }
        }
    }

    void printBoxBoard(int k) {
        HBox secondBox=new HBox();
        HBox.setMargin(secondBox, new Insets(12,12,12,12));

        secondBox=myBoard[k].getNewBoardView();
        secondBox.setMinWidth(Constants.width/1.5);
        secondBox.setMaxWidth(Constants.width/1.5);
        secondBox.setMinHeight((Constants.height-84)/7.0);
        secondBox.setMaxHeight((Constants.height-84)/7.0);
        secondBox.setAlignment(Pos.CENTER);

        GameEngine.centerBox.getChildren().add(secondBox);
    }

    public void makePassView() {
        Text passText=new Text("Passed");
        passText.setFont(Font.font("MedievalSharp",60));
        passText.setFill(Color.WHITE);
        playerPass=new HBox();
        playerPass.getChildren().add(passText);
        playerPass.setMinWidth(Constants.width);
        playerPass.setMinHeight((Constants.height-84.0)/7.0);
        playerPass.setMaxHeight((Constants.height-84.0)/7.0);
        playerPass.setVisible(false);
        playerPass.setAlignment(Pos.CENTER);
        
    }

    void makeHeartView() {
        playerHeart.setAlignment(Pos.CENTER);
        playerHeart.setMinWidth(100);
        playerHeart.setMinHeight(50);
        playerHeart.setMaxHeight(50);
    }

    public static class Heart {
        boolean on = true;
        ImageView currentImage;

        Heart() {
            currentImage = new ImageView(new Image(App.class.getResource(Constants.pathToOnHeart).toExternalForm()));
        }

        void getHeartOff(){
            on = false;
            currentImage = new ImageView(new Image(App.class.getResource(Constants.pathToOffHeart).toExternalForm()));
        }
    }
    
}
