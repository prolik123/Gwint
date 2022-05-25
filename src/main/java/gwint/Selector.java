package gwint;

import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class Selector extends VBox{
    int howManySelected = 0;
    public Selector(List<Card> list,int howMany,String text) {
        setSpacing(20);
        setMinHeight(Constants.height);
        setMinWidth(Constants.width);
        setMaxHeight(Constants.height);
        setMaxWidth(Constants.width);
        setBackground(new Background(new BackgroundFill(Color.rgb(0, 0, 0, 0.75),null,null)));
        setAlignment(Pos.CENTER);

        Text selectorText=new Text(text);
        selectorText.setFont((Font.font("MedievalSharp",32)));
        selectorText.setFill(Color.WHITE);


        HBox selectorCards=new HBox(Constants.cardDefaultSpacing);
        selectorCards.setAlignment(Pos.CENTER);
        int i=1;
        for(Card card:list) {
            Button currentButton = card.genCardView();
            currentButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(!card.selected && howManySelected < howMany) {
                        card.selected = true;
                        howManySelected++;
                        Animations.scaleTo(currentButton, 1.2, 1.2, 100);
                    }
                    else if(card.selected) {
                        card.selected = false;
                        howManySelected--;
                        Animations.scaleTo(currentButton, 1, 1, 100);
                    }
                }
            });
            
            currentButton.setOpacity(0.0);
            currentButton.setVisible(false);
            selectorCards.getChildren().add(currentButton);

            //I hate threads.
            int curri=i;
            new Thread(()->{
                try {
                    Thread.sleep(curri*350);
                    currentButton.setOpacity(0.0);
                    currentButton.setVisible(true);
                    Animations.fadeIn(currentButton, 350);
                } catch(Exception e){}
            }).start();
            i++;
        }
        getChildren().addAll(selectorText,selectorCards);
    }

    public void setAcceptDiscardButton(List<Card> list,Player player) {
        Button selectorBtn=new Button("Accept");
        selectorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent arg0) {
                for(int k=0;k<list.size();k++) {
                    if(list.get(k).selected){
                        list.get(k).selected = false;
                        player.myCardDeck.add(list.get(k));
                        list.remove(k);
                        k--;
                    }
                }
                for(int k=0;k<howManySelected;k++) {
                    if(player.myCardDeck.isEmpty())
                        break;
                    list.add(player.myCardDeck.get(0));
                    player.myCardDeck.remove(0);
                }
                Animations.fadeOut(Selector.this, 250);
                new Thread(()->{
                    try {
                        Thread.sleep(250);
                        Platform.runLater(()->{GameEngine.root.getChildren().remove(Selector.this);});
                    } catch(Exception e){}
                }).start();
                GameEngine.setPlayerHand(player);
                GameEngine.human.playerPass.toFront();
            }
        });
        getChildren().add(selectorBtn);
    }
}
