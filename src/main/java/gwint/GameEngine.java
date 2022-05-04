package gwint;

import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
//import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.PerspectiveTransform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;


public class GameEngine {
    
    /// class for all bot's functions
    public static Player opponent;

    /// class for all player's functions
    public static Player human;

    public static StackPane root;

    public static volatile boolean ablePlayerMove = true;
    //public static double ratio;

    public static Result res;

    public static VBox centerBox;

    /// Constructor 
    GameEngine(StackPane root) {

        GameEngine.root = root;
        
        String image = App.class.getResource("sth.jpg").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: auto 100%; " +
            "-fx-font-family: MedievalSharp; " +
            "-fx-font-size: 20 "
        );

        //ZMIEN BORDERPANE NA COS LEPSZEGO
        BorderPane centerPane=new BorderPane();

        //TO ROBI ZEBY KARTY BYLY PRZESUNIETE TROCHE W GORE
        //centerPane.setMaxHeight(Constants.height-Constants.height/7.0-84.0);

        centerBox=new VBox(10);
        centerBox.setMinWidth(Constants.width/1.5);
        centerBox.setMaxWidth(Constants.width/1.5);
        //centerBox.setMinHeight(700);
        centerBox.setMaxHeight(Constants.height-Constants.height/7.0-84.0);

        opponent = new Player(Constants.positionOfOpponentHearts,Constants.positionOfOpponentPass,Constants.positionOfOpponentBoards);
        human = new Player(Constants.positionOfHumanHearts,Constants.positionOfHumanPass,Constants.positionOfHumanBoards);

        centerPane.setCenter(centerBox);
        StackPane.setAlignment(centerPane, Pos.TOP_CENTER);
        root.getChildren().add(centerPane);
        //BorderPane.setAlignment(centerBox, Pos.CENTER);

        HBox bottomBox=new HBox();
        bottomBox=GameEngine.human.myCards.getNewBoardView(Constants.ratio);
        //bottomBox.setBackground(new Background(new BackgroundFill(Color.BLUE,null,null)));
        bottomBox.setMinWidth(Constants.width/1.5);
        bottomBox.setMaxWidth(Constants.width/1.5);
        bottomBox.setMinHeight((Constants.height-84.0)/7.0);
        bottomBox.setMaxHeight((Constants.height-84.0)/7.0);
        bottomBox.setAlignment(Pos.CENTER);

        //BorderPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);
        //centerPane.setBottom(bottomBox);
        root.getChildren().add(bottomBox);
        StackPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);

        //System.out.println(Constants.width+" "+Constants.height);

        PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
        perspectiveTrasform.setUlx(Constants.width/14.0); //gora lewy
        perspectiveTrasform.setUly(0.0); //gora lewy
        perspectiveTrasform.setUrx(Constants.width/1.5-Constants.width/14.0); //gora prawy
        perspectiveTrasform.setUry(0.0); //gora prawy
        perspectiveTrasform.setLrx(Constants.width/1.5); //dol prawy
        perspectiveTrasform.setLry(Constants.height-(Constants.height/7.0)-24); //dol prawy
        perspectiveTrasform.setLlx(0.0); //dol lewy
        perspectiveTrasform.setLly(Constants.height-(Constants.height/7.0)-24); //dol lewy

        centerBox.setEffect(perspectiveTrasform);

        VBox rightBox=new VBox();
        rightBox.setBackground(new Background(new BackgroundFill(Color.RED,null,null)));
        rightBox.setMinWidth(100);
        rightBox.setMaxWidth(100);
        rightBox.setMinHeight(Constants.height);
        rightBox.setMaxHeight(Constants.height);

        root.getChildren().addAll(rightBox);
        StackPane.setAlignment(rightBox, Pos.CENTER_RIGHT);

        /*VBox leftBox=new VBox();
        leftBox.setMinWidth(100);
        leftBox.setMaxWidth(100);
        leftBox.setMinHeight(Constants.height);
        leftBox.setMaxHeight(Constants.height);*/
            
        /*Add more elements here*/
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                if(!GameEngine.human.myPass)
                    GameEngine.human.getPass();
            }
        });
        ///root.add(human.playerValue, 20, 90);
        ///root.add(opponent.playerValue,20,75);
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
            if(!human.loseHeart()){
                endGame();
                return;
            }
        }
        else if(human.myBoardValue > opponent.myBoardValue) {
            if(!opponent.loseHeart()) {
                endGame();
                return;
            }

        }
        else {
            boolean firstlose = human.loseHeart();
            boolean seclose = opponent.loseHeart();
            if(!firstlose || !seclose){
                endGame();
                return;
            }
        }
        root.getChildren().remove(res);
        res = null;
        GameEngine.centerBox.getChildren().clear();
        opponent.preparePlayerForNextRound();
        human.preparePlayerForNextRound();
    }

    public static void endGame() {
        root.getChildren().remove(res);
        res = null;
        if(human.hasOnHeart())  
            res = new Result("Victory is ours!", 60, Color.BLUE);
        else if(opponent.hasOnHeart()) 
            res = new Result("Defeat is upon us", 60, Color.RED);
        else 
            res = new Result("It is a draw", 60, Color.WHITE);
        StackPane.setAlignment(res, Pos.CENTER);
        Platform.runLater(()->{root.getChildren().add(res);});
        Button back = new Button("Back to Main Menu");
        back.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                App.switchScene("MainMenu.fxml");
            }
            
        });
        res.getChildren().add(back);
        //Platform.runLater(()->{root.getChildren().add(back);});
    }

    public static void PrintResult(){
        if(human.myBoardValue < opponent.myBoardValue)  
            res = new Result("You lost the round!", 60, Color.RED);
        else if(human.myBoardValue > opponent.myBoardValue) 
            res = new Result("You won the round!", 60, Color.BLUE);
        else 
            res = new Result("You drew the round!", 60, Color.WHITE);
        //root.add(res, Constants.positionOfResult.getX(), Constants.positionOfResult.getY());
        StackPane.setAlignment(res, Pos.CENTER);
        root.getChildren().add(res);
    }

    public static class Result extends VBox {
    
        public Result(String Name,int size,Color Col) {
            Text res=new Text();
            res.setFill(Col);
            res.setText(Name);
            res.setFont(Font.font("MedievalSharp",size));
            
            setStyle("-fx-background-color: linear-gradient(to bottom, rgba(0,0,0,0) 20%,rgba(0,0,0,0.9) 40%,rgba(0,0,0,0.9) 60%,rgba(0,0,0,0)) 80%");

            setAlignment(Pos.CENTER);
            setMaxHeight(Constants.height/2);
            getChildren().add(res);
        }
        
    }
}

