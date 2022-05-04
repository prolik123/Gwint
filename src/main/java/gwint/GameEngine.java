package gwint;

import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
//import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class GameEngine {
    
    /// class for all bot's functions
    public static Player opponent;

    /// class for all player's functions
    public static Player human;

    public static GridPane root;

    public static volatile boolean ablePlayerMove = true;
    //public static double ratio;

    public static Result res;

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
            "-fx-font-size: 20; "
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
            if(!human.looseHeart()){
                endGame();
                return;
            }
        }
        else if(human.myBoardValue > opponent.myBoardValue) {
            if(!opponent.looseHeart()) {
                endGame();
                return;
            }

        }
        else {
            boolean firstLoose = human.looseHeart();
            boolean secLoose = opponent.looseHeart();
            if(!firstLoose || !secLoose){
                endGame();
                return;
            }
        }
        Platform.runLater(()->{
            root.getChildren().remove(res);
            res = null;
            human.preparePlayerForNextRound();
            opponent.preparePlayerForNextRound();
            if(opponent.myPass && human.myPass)
                startNewRoundThred();
            else if(human.myPass)
                opponent.ThreadMove(1000);
        });

    }

    public static void endGame() {
        root.getChildren().remove(res);
        res = null;
        if(human.hasOnHeart())  
            res = new Result("You Won", 60, Color.BLUE);
        else if(opponent.hasOnHeart()) 
            res = new Result("You Loose", 60, Color.RED);
        else 
            res = new Result("It's Draw", 60, Color.WHITE);
        Platform.runLater(()->{root.add(res, Constants.positionOfResult.getX(), Constants.positionOfResult.getY());});
        Button back = new Button("Back to Main Menu");
        back.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent arg0) {
                App.switchScene("MainMenu");
            }
            
        });
        Platform.runLater(()->{root.add(back, Constants.positionOfBackToMainMenu.getX(), Constants.positionOfBackToMainMenu.getY());});
    }

    public static void PrintResult(){
        if(human.myBoardValue < opponent.myBoardValue)  
            res = new Result("Defeat", 60, Color.RED);
        else if(human.myBoardValue > opponent.myBoardValue) 
            res = new Result("Victory", 60, Color.BLUE);
        else 
            res = new Result("Draw", 60, Color.WHITE);
        root.add(res, Constants.positionOfResult.getX(), Constants.positionOfResult.getY());
    }

    public static void startNewRoundThred() {
        new Thread(()->{
            try {
                Platform.runLater(()->{GameEngine.PrintResult();});
                Thread.sleep(2000);
                Platform.runLater(()->{GameEngine.startNewRound();});
            }
            catch (Exception e) {

            }}).start();
    }

    public static class Result extends HBox {
    
        public Result(String Name,int size,Color Col) {
            Text res=new Text();
            res.setFill(Col);
            res.setText(Name);
            res.setFont(Font.font("MedievalSharp",size));

    
            getChildren().add(res);
        }
        
    }
}

