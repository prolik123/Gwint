package gwint;

import java.util.*;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

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

    public Point positionOfHearts;

    public Pass playerPass;

    public Text playerValue;

    public Point positionOfPass;

    public Point positionOfBoards;


    /// Basic Constructor for each field
    Player(Point positionOfHearts, Point positionOfPass,Point positionOfBoards) {

        this.positionOfHearts = positionOfHearts;
        this.positionOfPass = positionOfPass;
        this.positionOfBoards = positionOfBoards;

        setBasicCardsInfo();

        setBasicBoardsInfo();

        setBasicHeartsInfo();

        playerValue = new Text("0");
        playerValue.setFill(Color.WHITE);

        printNewBoards();
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
        for(int k=0;k<Constants.numberOfBoards;k++)
            myBoard[k] = new BoardSlot();
    }

    void setBasicHeartsInfo() {
        myHearts = new ArrayList<>();
        for(int k=0;k<Constants.numberOfHearts;k++) {
            myHearts.add(new Heart());
            GameEngine.root.add(myHearts.get(k).currentImage, positionOfHearts.getX()+positionOfHearts.getModyfierX()*k, positionOfHearts.getY());
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
        Platform.runLater(()->{myBoard[card.boardType].getCurentBoardView().getChildren().add(btn);});
        btnTrans.play();
        Platform.runLater(()->{myBoard[card.boardType].getCurentBoardView().setSpacing(1/Constants.ratio);});
        updateValue();
    }

    /// Function will pass...
    void getPass(){
        myPass = true;
        playerPass = new Pass(Constants.ratio);
        Platform.runLater(()->{GameEngine.root.add(playerPass,positionOfPass.getX(),positionOfPass.getY());});

        if(this == GameEngine.human) {
            GameEngine.opponent.ThreadMove(1000);
        }
        if(GameEngine.opponent.myPass && GameEngine.human.myPass) {
            new Thread(()->{
                try {
                    Platform.runLater(()->{GameEngine.PrintResult();});
                    Thread.sleep(2000);
                    Platform.runLater(()->{GameEngine.startNewRound();});
                }
                catch (Exception e) {

                }}).start();
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

    boolean looseHeart(){
        for(int k=0;k<myHearts.size();k++) {
            if(myHearts.get(k).on) {
                GameEngine.root.getChildren().remove(myHearts.get(k).currentImage);
                myHearts.get(k).getHeartOff();
                GameEngine.root.add(
                    myHearts.get(k).currentImage,
                        positionOfHearts.getX()+positionOfHearts.getModyfierX()*k,
                            positionOfHearts.getY()+ positionOfHearts.getModyfierY()*k
                );
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
            FadeTransition btnTrans=new FadeTransition(Duration.millis(500),myBoard[i].currentView);
            btnTrans.setFromValue(1.0);
            btnTrans.setToValue(0.0);
            btnTrans.play();
            //Not deleting the row may couse bugs later on
            //Keep an eye for it
            //root.getChildren().remove(opponent.myBoard[2-i].currentView);
            myBoard[i].cardList.clear();
        }
        printNewBoards();
    }

    void preparePlayerForNextRound() {
        clearBoard();
        GameEngine.root.getChildren().remove(playerPass);
        playerPass = null;
        myBoardValue = 0;
        updateValue();
        myPass = false;
    }

    void printNewBoards() {
        for(int k=0;k<Constants.numberOfBoards;k++) {
            GameEngine.root.add(
                myBoard[k].getNewBoardView(Constants.ratio),
                    positionOfBoards.getX()+k*positionOfBoards.getModyfierX(),
                            positionOfBoards.getY()+positionOfBoards.getModyfierY()*k
                        );
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
    
}
