package gwint;

import java.util.*;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
    public List<Card> myCardDeck;

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

    public List<Card> deadCards;

    public Card dummy=null;

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
    private void chooseHandCards(List<Card> currList, List<Card> currStack){
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
        myCardDeck = new ArrayList<>();
        deadCards = new ArrayList<>();
        //myCards.cardList = JsonCardParser.getCardsList();
        myCards.cardList = DeckLoader.loadAvailableDeck();
        getListPermutation(myCards.cardList);
        chooseHandCards(myCards.cardList, myCardDeck);
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
        if(!GameEngine.ableOponentMove && this == GameEngine.opponent) {
            this.ThreadMove(1000);
            return;
        }
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
        Sounds.playSoundEffect("cardThrow");
        boolean cardThrew = false;
        for(PlayInterface effect:card.effectArray)
            cardThrew = cardThrew || effect.playEffect(card, this);
        if(!cardThrew)
            throwCardWithoutInterface(card);
        if(myCards.cardList.isEmpty())
            getPass();
        if(this == GameEngine.human)
            GameEngine.opponent.ThreadMove(1000);
    }

    void throwCardWithoutInterface(Card card) {
        Button btn=card.genCardView();
        myBoardValue += card.value;
        myBoard[card.boardType].cardList.add(card);
        Platform.runLater(()->{myBoard[card.boardType].getCurentBoardView().getChildren().add(btn);});
        Platform.runLater(()->{myBoard[card.boardType].getCurentBoardView().setSpacing(-15);});
        updateValue();
    }

    /// Function will pass...
    void getPass(){
        myPass = true;
        if(this.equals(GameEngine.human)) GameEngine.passText.setText("PASSED");
        Platform.runLater(()->{
            playerPass.setVisible(true);
            Animations.fadeIn(playerPass, 250);
        });

        if(this == GameEngine.human && GameEngine.ablePlayerMove) {
            GameEngine.opponent.ThreadMove(1000);
        }
        if(GameEngine.opponent.myPass && GameEngine.human.myPass) {
            GameEngine.startNewRoundThred();
        }
    }

    void updateValue(){
        int tempValue = 0;
        for(int k=0;k<Constants.numberOfBoards;k++) {
            //We create a hash map that stores our cards with bond class
            HashMap<String,Integer> cntBonds=new HashMap<String,Integer>();
            for(Card card:myBoard[k].cardList) {
                if(card.name.equals("dummy")) continue;
                if(GameEngine.BoardWeather[k]) {
                    card.badEffect();
                    tempValue +=1;
                } else {
                    card.removeEffect();
                    tempValue += card.value;
                }

                if(card.getCardClass()!=null && card.getCardClass().equals("BondClass")) {
                    if(!cntBonds.containsKey(card.name)) {
                        cntBonds.put(card.name, 1);
                    } else {
                        cntBonds.replace(card.name, cntBonds.get(card.name), cntBonds.get(card.name)+1);
                    }
                }
            }
            //And now, if there are at least two same cards with bond class, we multiply their worth
            for(String name:cntBonds.keySet()) {
                int cnt=cntBonds.get(name);
                if(cnt>1) {
                    for(Card cardFromMap:myBoard[k].cardList) {
                        if(cardFromMap.name.equals(name)) {
                            int newVal;
                            if(GameEngine.BoardWeather[k]) newVal=Integer.valueOf((int)Math.pow(2,cnt-1));
                            else newVal=Integer.valueOf(cardFromMap.value*(int)Math.pow(2,cnt-1));
                        
                            cardFromMap.goodEffect(newVal);
                            
                            if(GameEngine.BoardWeather[k]) {
                                tempValue+=newVal-1;
                            } else {
                                tempValue+=newVal-cardFromMap.value;
                            }
                        }
                    }
                }
            }
        }
        myBoardValue = tempValue;
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
            for(Node curr:myBoard[i].currentView.getChildren()) {
                Animations.fadeOut(curr, Constants.fadeOutDuration);
            }
            myBoard[i].cardList.clear();
        }

        new Thread(()->{
            try {
                Thread.sleep(500);
                Platform.runLater(()->{
                    for(int k=0;k<Constants.numberOfBoards;k++) {
                        myBoard[k].getCurentBoardView().getChildren().clear();
                    }
                });
            } catch(Exception e){}
        }).start();
    }

    void preparePlayerForNextRound() {
        myBoardValue = 0;
        clearBoard();
        updateValue();
        if(!myCards.cardList.isEmpty()) {  
            Animations.fadeOut(playerPass, 250);
            new Thread(()->{
                try {
                    Thread.sleep(250);
                    playerPass.setVisible(false);
                } catch(Exception e){}
            }).start();
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
        playerPass.setOpacity(0.0);
        playerPass.setAlignment(Pos.CENTER);
        
    }

    void makeHeartView() {
        playerHeart.setAlignment(Pos.CENTER);
        playerHeart.setMinWidth(100);
        playerHeart.setMinHeight(50);
        playerHeart.setMaxHeight(50);
    }

    public void addCardsFromBoardToDeadDeck(){
        for(int k=0;k<Constants.numberOfBoards;k++) {
            for(Card card:myBoard[k].cardList)
                if(card.getCardClass()==null || !card.getCardClass().equals("DummyClass")) deadCards.add(card);
        }
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

    public void setDummy(Card inDummy) {
        dummy=inDummy;
    }
    
    public Card getDummy() {
        return dummy;
    }

    public void removeDummy() {
        dummy=null;
    }
}
