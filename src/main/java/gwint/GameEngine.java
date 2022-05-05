package gwint;

import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Pos;


public class GameEngine {
    
    //Class for all bot's functions
    public static Player opponent;

    //Class for all player's functions
    public static Player human;

    //This here is the heart of the game, it keeps everything together
    public static StackPane root;

    //Can you move?
    public static volatile boolean ablePlayerMove = true;

    //Whether you have won, lost or drew
    public static Result res;

    //Following objects are the side effect of my i̶n̶c̶o̶m̶p̶e̶t̶a̶n̶c̶e̶ love for simple solutions
    public static VBox centerBox;

    public static HBox playerHeart;

    public static HBox enemyHeart;

    public static VBox mainValues;

    public static HBox playerPass;

    public static HBox enemyPass;


    //Constructor 
    //Let's get rollin' 🏎
    GameEngine(StackPane root) {
        this.root = root;

        //This here sets up the background, font and the general settings for the scene
        
        String image = App.class.getResource("background.jpg").toExternalForm();
        root.setStyle(
            "-fx-background-image: url('" + image + "'); " +
            "-fx-background-position: center;" + 
            "-fx-background-repeat: no-repeat; " +
            "-fx-background-size: auto 100%; " +
            "-fx-font-family: MedievalSharp; " +
            "-fx-font-size: 20; "
        );

        //Lets create the UI. Do note, that we create objects from the "furthest" to the "nearest".

        //TODO: Change this for something better then BorderPane
        //For now it does it's job, but is it the best option?
        //Center Pane is the most important object here, as it contains the cards on the table
        //and the players hand
        BorderPane centerPane=new BorderPane();

        //This here changes layout so that the cards don't overlap
        //centerPane.setMaxHeight(Constants.height-Constants.height/7.0-84.0);

        //So we begin creating the layout
        //It goes something like this:
        //centerBox rightBox
        //centerBox rightBox
        //bottomBox rightBox

        //CenterBox contains the cards that are on the table (six lanes)
        centerBox=new VBox(10);
        centerBox.setMinWidth(Constants.width/1.5);
        centerBox.setMaxWidth(Constants.width/1.5);
        centerBox.setMaxHeight(Constants.height-Constants.height/7.0-84.0);

        //It may seem that this part is a mess, and it is true. However, it is makes sense if
        //you read through it carefully. We create those objects like centerBox hearts etc. so
        //that we can use them while creating the players, and have the correct references without
        //having to worry about it later. It's quite smart if you think about it.

        enemyHeart=new HBox(5);
        playerHeart=new HBox(5);
        mainValues=new VBox(5);

        //The pass button is between the opponent and player so it is in correct spot in right-hand
        //side "menu" without having to add extra code and static objects
        opponent = new Player(false);

        Button passBtn=new Button();
        mainValues.getChildren().add(passBtn);

        human = new Player(true);

        centerPane.setCenter(centerBox);
        StackPane.setAlignment(centerPane, Pos.TOP_CENTER);
        root.getChildren().add(centerPane);

        //Here we create the bottomBox, which stores the cards that player has on hand
        HBox bottomBox=new HBox();
        bottomBox=GameEngine.human.myCards.getNewBoardView();
        bottomBox.setMinWidth(Constants.width/1.5);
        bottomBox.setMaxWidth(Constants.width/1.5);
        bottomBox.setMinHeight((Constants.height-84.0)/7.0);
        bottomBox.setMaxHeight((Constants.height-84.0)/7.0);
        bottomBox.setAlignment(Pos.CENTER);

        root.getChildren().add(bottomBox);
        StackPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);

        //This abomination here is the transformation that makes the playing field feel 3D
        //while being in 2D
        PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
        perspectiveTrasform.setUlx(Constants.width/14.0);                       //upper left x
        perspectiveTrasform.setUly(0.0);                                        //upper left y
        perspectiveTrasform.setUrx(Constants.width/1.5-Constants.width/14.0);   //upper right x
        perspectiveTrasform.setUry(0.0);                                        //upper right y
        perspectiveTrasform.setLrx(Constants.width/1.5);                        //lower left x
        perspectiveTrasform.setLry(Constants.height-(Constants.height/7.0)-24); //lower left y
        perspectiveTrasform.setLlx(0.0);                                        //lower right x
        perspectiveTrasform.setLly(Constants.height-(Constants.height/7.0)-24); //lower right y

        centerBox.setEffect(perspectiveTrasform);

        //Here is the pass, which is the worst part of this whole app. This should be a class I know.
        //However, Java didn't want to cooperate and so, it is bruteforced here. What you gonna do?
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

        playerPass.setStyle("-fx-background-color: linear-gradient(to top, rgba(0,0,0,1) 0%, rgba(0,0,0,0.4) 80%, rgba(255,255,255,0) 100%)");
        StackPane.setAlignment(playerPass, Pos.BOTTOM_CENTER);
        
        Text passText2=new Text("Passed");
        passText2.setFont(Font.font("MedievalSharp",60));
        passText2.setFill(Color.WHITE);
        
        enemyPass=new HBox();
        enemyPass.getChildren().add(passText2);
        enemyPass.setMinWidth(Constants.width);
        enemyPass.setMinHeight((Constants.height-84.0)/7.0);
        enemyPass.setMaxHeight((Constants.height-84.0)/7.0);
        enemyPass.setVisible(false);
        enemyPass.setAlignment(Pos.CENTER);

        enemyPass.setStyle("-fx-background-color: linear-gradient(to bottom, rgba(0,0,0,1) 0%, rgba(0,0,0,0.4) 80%, rgba(255,255,255,0) 100%)");
        StackPane.setAlignment(enemyPass, Pos.TOP_CENTER);
        root.getChildren().addAll(playerPass,enemyPass);

        //Wow, that was a trainwreck. Glad that is done.
        //rightBox contains everything that appears on the "menu" in the right. So button to pass,
        //values on the table number of hearts and so forth

        VBox rightBox=new VBox();
        rightBox.setMinWidth(100);
        rightBox.setMaxWidth(100);
        rightBox.setMinHeight(Constants.height);
        rightBox.setMaxHeight(Constants.height);

        enemyHeart.setAlignment(Pos.CENTER);
        enemyHeart.setMinWidth(100);
        enemyHeart.setMinHeight(50);
        enemyHeart.setMaxHeight(50);
        
        mainValues.setMinWidth(100);
        mainValues.setMinHeight(Constants.height-100);
        mainValues.setMaxHeight(Constants.height-100);
        mainValues.setAlignment(Pos.CENTER);

        ImageView imView=new ImageView(new Image(App.class.getResource("coin.png").toExternalForm()));
        imView.setFitHeight(100);
        imView.setFitWidth(100);
        passBtn.setGraphic(imView);
        passBtn.setStyle(Constants.DECK_STYLE);
        
        playerHeart.setAlignment(Pos.CENTER);
        playerHeart.setMinWidth(100);
        playerHeart.setMinHeight(50);
        playerHeart.setMaxHeight(50);
        
        rightBox.getChildren().addAll(enemyHeart,mainValues,playerHeart);

        root.getChildren().add(rightBox);
        StackPane.setAlignment(rightBox, Pos.CENTER_RIGHT);
            
        //This thing enables you to press shift to pass
        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.SHIFT) {
                if(!GameEngine.human.myPass)
                    GameEngine.human.getPass();
            }
        });
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
                App.switchScene("MainMenu");
            }
            
        });
        res.getChildren().add(back);
    }

    public static void PrintResult(){
        if(human.myBoardValue < opponent.myBoardValue)  
            res = new Result("You lost the round!", 60, Color.RED);
        else if(human.myBoardValue > opponent.myBoardValue) 
            res = new Result("You won the round!", 60, Color.BLUE);
        else 
            res = new Result("You drew the round!", 60, Color.WHITE);
        StackPane.setAlignment(res, Pos.CENTER);
        root.getChildren().add(res);
    }

    public static class Result extends VBox {
    
        public Result(String Name,int size,Color Col) {
            Text res=new Text();
            res.setFill(Col);
            res.setText(Name);
            res.setFont(Font.font("MedievalSharp",size));
            
            setStyle("-fx-background-color: linear-gradient(to bottom, rgba(0,0,0,0) 20%,rgba(0,0,0,0.9) 40%,rgba(0,0,0,0.9) 60%,rgba(0,0,0,0) 80%)");

            setAlignment(Pos.CENTER);
            setMaxHeight(Constants.height/2);
            getChildren().add(res);
        }
        
    }
}

