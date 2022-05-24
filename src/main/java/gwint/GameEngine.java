package gwint;

import java.util.*;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.effect.PerspectiveTransform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Insets;
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

    public static VBox mainValues;

    public static Text passText;

    public static DeckView deckView;

    public static DeckView deadView;

    public static HBox alertBox;

    public static volatile boolean ableOponentMove = true;

    public static boolean[] BoardWeather;


    //Constructor 
    //Let's get rollin' 🏎
    GameEngine(StackPane root) {
        GameEngine.root = root;

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

        //This thing enables you to press shift to pass

        root.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                if(!GameEngine.human.myPass)
                    GameEngine.human.getPass();
            }
        });

        //Lets create the UI. Do note, that we create objects from the "furthest" to the "nearest".

        //Center Pane is the most important object here, as it contains the cards on the table
        //and the players hand
        StackPane centerPane=new StackPane();

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

        mainValues=new VBox(5);

        //The pass button is between the opponent and player so it is in correct spot in right-hand
        //side "menu" without having to add extra code and static objects
        opponent = new Player();

        Button passBtn=new Button();
        mainValues.getChildren().add(passBtn);

        human = new Player();
        opponent.printNewBoards();
        human.printNewBoards();
        centerPane.getChildren().add(centerBox);
        //This here changes layout so that the cards don't overlap
        //StackPane.setAlignment(centerBox, Pos.TOP_CENTER);
        centerPane.setMinWidth(Constants.width);
        centerPane.setMinHeight(Constants.height);
        root.getChildren().add(centerPane);

        deckView=new DeckView();
        Button deck=deckView.genDeckView();
        centerPane.getChildren().add(deck);
        StackPane.setAlignment(deck, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(deck, new Insets(0,Constants.width/10.0,0,0));
        deckView.changeDeadVal(GameEngine.human.myCardDeck.size());

        deadView=new DeckView();
        Button dead=deadView.genDeadView();
        centerPane.getChildren().add(dead);
        StackPane.setAlignment(dead, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(dead, new Insets(0,Constants.width/10.0+5.0+deckView.width,0,0));

        //Here we create the bottomBox, which stores the cards that player has on hand
        setPlayerHand(GameEngine.human);

        //This abomination here is the transformation that makes the playing field feel 3D
        //while being in 2D
        PerspectiveTransform perspectiveTrasform = new PerspectiveTransform();
        perspectiveTrasform.setUlx(Constants.width/14.0);                       //upper left x
        perspectiveTrasform.setUly(0.0);                                  //upper left y
        perspectiveTrasform.setUrx(Constants.width-Constants.width/14.0);       //upper right x
        perspectiveTrasform.setUry(0.0);                                  //upper right y
        perspectiveTrasform.setLrx(Constants.width);                            //lower right x
        perspectiveTrasform.setLry(Constants.height);                           //lower right y
        perspectiveTrasform.setLlx(0.0);                                  //lower left x
        perspectiveTrasform.setLly(Constants.height);                           //lower left y

        centerPane.setEffect(perspectiveTrasform);

        //Here is the pass, which is the worst part of this whole app. This should be a class I know.
        //However, Java didn't want to cooperate and so, it is bruteforced here. What you gonna do?
        human.makePassView();
        human.playerPass.setStyle(Constants.GRIADENT_BOTTOM_UP);
        StackPane.setAlignment(human.playerPass, Pos.BOTTOM_CENTER);
        opponent.makePassView();
        opponent.playerPass.setStyle(Constants.GRIADENT_TOP_DOWN);
        StackPane.setAlignment(opponent.playerPass, Pos.TOP_CENTER);
        root.getChildren().addAll(opponent.playerPass,human.playerPass);

        //Wow, that was a trainwreck. Glad that is done.
        //rightBox contains everything that appears on the "menu" in the right. So button to pass,
        //values on the table number of hearts and so forth

        VBox rightBox=new VBox();
        rightBox.setMinWidth(100);
        rightBox.setMaxWidth(100);
        rightBox.setMinHeight(Constants.height);
        rightBox.setMaxHeight(Constants.height);

        opponent.makeHeartView();
        
        mainValues.setMinWidth(100);
        mainValues.setMinHeight(Constants.height-100);
        mainValues.setMaxHeight(Constants.height-100);
        mainValues.setAlignment(Pos.CENTER);

        ImageView imView=new ImageView(new Image(App.class.getResource("coin.png").toExternalForm()));
        imView.setFitHeight(100);
        imView.setFitWidth(100);

        passText=new Text("PASS");
        passText.setFont((Font.font("MedievalSharp",22)));
        passText.setFill(Color.WHITE);

        StackPane passBtnGraphic=new StackPane(imView,passText);

        passBtn.setGraphic(passBtnGraphic);
        passBtn.setStyle(Constants.DECK_STYLE);


        passBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                GameEngine.human.getPass();
            }
        });

        passBtn.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent arg0) {
                if(!human.myPass)Animations.rotateTo(passBtn, 360,1000);
            }
        });

        human.makeHeartView();
        
        rightBox.getChildren().addAll(opponent.playerHeart,mainValues,human.playerHeart);

        root.getChildren().add(rightBox);
        StackPane.setAlignment(rightBox, Pos.CENTER_RIGHT);

        BoardWeather = new boolean[Constants.numberOfBoards];
        for(int k=0;k<Constants.numberOfBoards;k++)
            BoardWeather[k] = false;

        alertBox=new HBox();
        alertBox.setMinWidth(Constants.width);
        alertBox.setMinHeight(80);
        alertBox.setMaxHeight(80);
        alertBox.setStyle(Constants.GRIADENT_TOP_DOWN);
        alertBox.setAlignment(Pos.CENTER);
        alertBox.setVisible(false);
        root.getChildren().add(alertBox);
        StackPane.setAlignment(alertBox, Pos.TOP_CENTER);

        human.myCards.currentView.setVisible(false);
        Selector selector=new Selector(human.myCards.cardList,Constants.numberOfDiscardAfterStart,Constants.textOnDiscardingCard);
        selector.setAcceptDiscardButton(human.myCards.cardList,human);
        root.getChildren().addAll(selector);
        StackPane.setAlignment(selector, Pos.CENTER);
    }

    /// Function which gets hand, stack and add new card to hand ( and View ) 
    public static synchronized Boolean addCardToHand(BoardSlot Board,List<Card> list){
        if(list.isEmpty()) return false;
        Card New = list.get(0);
        list.remove(New);
        Board.cardList.add(New);
        Board.addCardToBoardView(New, Board.currentView);
        deckView.changeDeckVal(list.size());
        if(list.isEmpty()) return false;
        return true;
    }

    public static boolean addSelectedCardToHand(BoardSlot Board,Card card) {
        Board.cardList.add(card);
        Board.addCardToBoardView(card, Board.currentView);
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

        passText.setText("PASS");

        human.addCardsFromBoardToDeadDeck();
        opponent.addCardsFromBoardToDeadDeck();
        deadView.changeDeadVal(human.deadCards.size());

        Platform.runLater(()->{
            Animations.fadeOut(res, 250);
            new Thread(()->{
                try {
                    Thread.sleep(250);
                } catch(Exception e){}
                Platform.runLater(()->{root.getChildren().remove(res);});
            }).start();
            opponent.preparePlayerForNextRound();
            human.preparePlayerForNextRound();
            for(int k=0;k<Constants.numberOfBoards;k++) BoardWeather[k] = false;
            if(opponent.myPass && human.myPass)
                startNewRoundThred();
            else if(human.myPass)
                opponent.ThreadMove(1000);
        });

        /*new Thread(()->{
            try {
                Thread.sleep(500);
                Platform.runLater(()->{
                    centerBox.getChildren().clear();
                });
            } catch(Exception e){}
        }).start();*/
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

    public static void endGame() {
        root.getChildren().remove(res);
        res = null;
        if(human.hasOnHeart())  
            res = new Result("Victory is ours!", 60, Color.valueOf(Constants.blue));
        else if(opponent.hasOnHeart()) 
            res = new Result("Defeat is upon us", 60, Color.valueOf(Constants.red));
        else 
            res = new Result("It is a draw", 60, Color.WHITE);

        StackPane.setAlignment(res, Pos.CENTER);
        res.setOpacity(0.0);
        Platform.runLater(()->{root.getChildren().add(res);});
        Animations.fadeIn(res, 250);
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
            res = new Result("You lost the round!", 60, Color.valueOf(Constants.red));
        else if(human.myBoardValue > opponent.myBoardValue) 
            res = new Result("You won the round!", 60, Color.valueOf(Constants.blue));
        else 
            res = new Result("You drew the round!", 60, Color.WHITE);
        StackPane.setAlignment(res, Pos.CENTER);
        res.setOpacity(0.0);
        root.getChildren().add(res);
        Animations.fadeIn(res, 250);
    }

    public static class Result extends VBox {
        public Result(String Name,int size,Color Col) {
            setSpacing(20);
            Text res=new Text();
            res.setFill(Col);
            res.setText(Name);
            res.setFont(Font.font("MedievalSharp",size));
            
            setStyle("-fx-background-color: linear-gradient(to bottom, rgba(0,0,0,0) 20%,rgba(0,0,0,0.9) 35%,rgba(0,0,0,0.9) 65%,rgba(0,0,0,0) 80%)");

            setAlignment(Pos.CENTER);
            setMaxHeight(Constants.height/2);
            getChildren().add(res);
        }
        
    }

    public static void setPlayerHand(Player player) {
        HBox bottomBox=new HBox();
        bottomBox=player.myCards.getNewBoardView();
        bottomBox.setMinWidth(Constants.width/1.5);
        bottomBox.setMaxWidth(Constants.width/1.5);
        bottomBox.setMinHeight((Constants.height-84.0)/7.0);
        bottomBox.setMaxHeight((Constants.height-84.0)/7.0);
        bottomBox.setAlignment(Pos.CENTER);

        root.getChildren().add(bottomBox);
        StackPane.setAlignment(bottomBox, Pos.BOTTOM_CENTER);
    }
}

