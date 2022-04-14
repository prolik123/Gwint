package gwint;

import java.util.*;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class GameEngine {
    
    /// class for all bot's functions
    public static Player opponent;

    /// class for all player's functions
    public static Player human;

    public static Pass playerPass;
    public static Pass opponentPass;
    public static GridPane root;
    public static Text humanValue;
    public static Text opponentValue;
    //public static double ratio;

    /// Constructor 
    GameEngine(GridPane root) {

        human = new Player();
        opponent = new Player();
        GameEngine.root = root;
        //GameEngine.ratio = ratio;

        //Set background
        String image = App.class.getResource("plansza.png").toExternalForm();
        
        root.getStylesheets().add("https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap");

        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: right center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: contain; " +
            "-fx-font-family: MedievalSharp; " +
            "-fx-font-size: 20 "
        );

        //Initialize the Game Engine
        //Add elements to grid
        DeckView deckView=new DeckView(Constants.ratio);
        root.add(deckView, 171, 110);

        //Adds lines for Cards (Player)
        for(int k=0;k<3;k++) 
            root.add(GameEngine.human.myBoard[k].getNewBoardView(Constants.ratio),72,98+24*k);

        //Add Cards hand
        root.add(GameEngine.human.myCards.getNewBoardView(Constants.ratio),57,170);

        //Adds lines for Cards (Bot)
        for(int k=0;k<3;k++)
            root.add(GameEngine.opponent.myBoard[2-k].getNewBoardView(Constants.ratio),72,18+26*k);
            
        /*Add more elements here*/
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                if(!GameEngine.human.myPass)
                    GameEngine.human.getPass();
            }
        });
        humanValue = new Text("0");
        humanValue.setFill(Color.WHITE);

        root.add(humanValue, 20, 90);
        opponentValue = new Text("0");
        opponentValue.setFill(Color.WHITE);
        root.add(opponentValue,20,75);
    }

    /// Function which gets hand, stack and add new card to hand ( and View ) 
    public static Boolean addCardToHand(BoardSlot Board,Stack<Card> Stack){
        if(Stack.empty()) return false;
        Card New = Stack.lastElement();
        Stack.pop();
        Board.cardList.add(New);
        Board.addCardToBoardView(New, Board.currentView);
        if(Stack.empty()) return false;
        return true;
    }

    public static void startNewRound() {
        for(int i=0;i<3;i++) {
            FadeTransition btnTrans=new FadeTransition(Duration.millis(500),human.myBoard[i].currentView);
            btnTrans.setFromValue(1.0);
            btnTrans.setToValue(0.0);
            btnTrans.play();
            //Not deleting the row may couse bugs later on
            //Keep an eye for it
            //root.getChildren().remove(human.myBoard[i].currentView);
            human.myBoard[i].cardList.clear();
            root.add(human.myBoard[i].getNewBoardView(Constants.ratio),72,98+24*i);
        }
        for(int i=0;i<3;i++) {
            FadeTransition btnTrans=new FadeTransition(Duration.millis(500),opponent.myBoard[2-i].currentView);
            btnTrans.setFromValue(1.0);
            btnTrans.setToValue(0.0);
            btnTrans.play();
            //Not deleting the row may couse bugs later on
            //Keep an eye for it
            //root.getChildren().remove(opponent.myBoard[2-i].currentView);
            opponent.myBoard[2-i].cardList.clear();
            root.add(opponent.myBoard[2-i].getNewBoardView(Constants.ratio),72,18+26*i);
        }
        root.getChildren().remove(playerPass);
        playerPass = null;
        root.getChildren().remove(opponentPass);
        opponentPass = null;
        opponent.myBoardValue = 0;
        opponent.updateValue();
        human.myBoardValue = 0;
        human.updateValue();
        human.myPass = false;
        opponent.myPass = false;
    }

}

