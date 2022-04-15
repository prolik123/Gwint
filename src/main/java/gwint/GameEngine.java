package gwint;

import java.util.*;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;


public class GameEngine {
    
    /// class for all bot's functions
    public static Player opponent;

    /// class for all player's functions
    public static Player human;

    public static GridPane root;
    //public static double ratio;

    /// Constructor 
    GameEngine(GridPane root) {

        GameEngine.root = root;
        human = new Player(Constants.positionOfHumanHearts,Constants.positionOfHumanPass,Constants.positionOfHumanBoards);
        opponent = new Player(Constants.positionOfOpponentHearts,Constants.positionOfOpponentPass,Constants.positionOfOpponentBoards);
        //GameEngine.ratio = ratio;

        //Set background
        String image = App.class.getResource("plansza.png").toExternalForm();
        
        root.getStylesheets().add(Constants.linkToFontDownload);

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
        root.add(deckView, Constants.positionOfDeck.getX(),Constants.positionOfDeck.getY());

        //Add Cards hand
        root.add(GameEngine.human.myCards.getNewBoardView(Constants.ratio),Constants.positionOfHumanHand.getX(),Constants.positionOfHumanHand.getY());
            
        /*Add more elements here*/
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                if(!GameEngine.human.myPass)
                    GameEngine.human.getPass();
            }
        });
        root.add(human.playerValue, 20, 90);
        root.add(opponent.playerValue,20,75);
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
        if(human.myBoardValue < opponent.myBoardValue) { 
            if(!human.looseHeart())
                return;
        }
        else if(human.myBoardValue > opponent.myBoardValue) {
            if(!opponent.looseHeart())
                return;

        }
        else {
            boolean firstLoose = human.looseHeart();
            boolean secLoose = opponent.looseHeart();
            if(!firstLoose || !secLoose)
                return;
        }
        human.preparePlayerForNextRound();
        opponent.preparePlayerForNextRound();

    }

}

