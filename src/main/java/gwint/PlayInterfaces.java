package gwint;

import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

interface PlayInterface {
    boolean playEffect(Card card,Player player);
}

public class PlayInterfaces {

    public static class SpyClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            if(player == GameEngine.human) 
                GameEngine.opponent.throwCardWithoutInterface(card);
            else
                GameEngine.human.throwCardWithoutInterface(card);
            
            for(int k=0;k<Constants.numberOfDrawnCardBySpy;k++) {
                GameEngine.addCardToHand(player.myCards, player.myCardDeck);
            }
            return true;
        }

    }

    public static class MedicClass implements PlayInterface {
         @Override
         public boolean playEffect(Card card,Player player) {
             if(player == GameEngine.opponent) {
                 for(int k=0;k<Constants.numberOfHealedCardByMedic;k++) {
                     if(!player.deadCards.isEmpty()) {
                         player.myCards.cardList.add(player.deadCards.get(0));
                         player.deadCards.remove(0);
                     }
                 }
                 player.throwCardWithoutInterface(card);
                return true;
             }
             if(player.deadCards.size()!=0) {
                Selector selector = new Selector(player.deadCards,Constants.numberOfHealedCardByMedic,Constants.textOnRevivingCard);
                setAccepttButton(selector,player,player.deadCards,player.myCards.cardList);
                GameEngine.root.getChildren().add(selector);
                GameEngine.ableOponentMove = false;
             } else {
                 //Iunno aabout that solution but, it works
                 alertEffect("You have no cards to revive, so no ");
             }
            player.throwCardWithoutInterface(card);
            return true;
        }

        private void setAccepttButton(Selector selector,Player player,List<Card> Chooselist,List<Card> AddList) {
            Button selectorBtn=new Button("Accept");
            selectorBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent arg0) {
                    for(int k=0;k<Chooselist.size();k++) {
                        if(Chooselist.get(k).selected){
                            Chooselist.get(k).selected = false;
                            AddList.add(Chooselist.get(k));
                            GameEngine.addSelectedCardToHand(player.myCards, Chooselist.get(k));
                            Chooselist.remove(k);
                            k--;
                        }
                    }
                    GameEngine.ableOponentMove = true;
                    GameEngine.root.getChildren().remove(selector);
                }
            });
            selector.getChildren().add(selectorBtn);
        }
    }

    public static class WeatherClearClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            Platform.runLater(()->{alertEffect(card.name);});
            for(int k=0;k<Constants.numberOfBoards;k++)
                GameEngine.BoardWeather[k] = false;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class SnowClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            Platform.runLater(()->{alertEffect(card.name);});
            GameEngine.BoardWeather[0] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class RainClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            Platform.runLater(()->{alertEffect(card.name);});
            GameEngine.BoardWeather[2] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class FogClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            Platform.runLater(()->{alertEffect(card.name);});
            GameEngine.BoardWeather[1] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class BondClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return false;
        };
    }

    public static class DummyClass implements PlayInterface {
        
        @Override
        public boolean playEffect(Card card, Player player) {
            if(player == GameEngine.human) {
                Image cursor = new Image(App.class.getResource("cursor.png").toExternalForm());
                GameEngine.root.setCursor(new ImageCursor(cursor));
                player.setDummy(card);
                GameEngine.ableOponentMove=false;
            }
            return true;
        }
    }

    public static class ScorchClass implements PlayInterface {
        @Override
        public boolean playEffect(Card card, Player player) {
            Platform.runLater(()->{alertEffect(card.name);});
            int maxVal=-1;
            for(int i=0;i<Constants.numberOfBoards;i++) {
                for(Card curr:GameEngine.opponent.myBoard[i].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                }
                for(Card curr:GameEngine.human.myBoard[i].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                }
            }

            GameEngine.ableOponentMove = false;
            for(int j=0;j<Constants.numberOfBoards;j++) {
                killCards(GameEngine.opponent, j, maxVal);
                killCards(GameEngine.human, j, maxVal);

                new Thread(()->{
                    try {
                        Thread.sleep(750);
                        GameEngine.ableOponentMove=true;
                    } catch(Exception e){}
                }).start();
            }

            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class BerserkerClass implements PlayInterface {
        @Override
        public boolean playEffect(Card card, Player player) {
            int maxVal=-1;
            int sum=0;
            GameEngine.ableOponentMove=false;
            if(player == GameEngine.human) {
                for(Card curr:GameEngine.opponent.myBoard[card.boardType].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                    sum+=Integer.valueOf(curr.cardVal.getText());
                }

                if(sum>=10) {
                    killCards(GameEngine.opponent, card.boardType, maxVal);
                    new Thread(()->{
                        try {
                            Thread.sleep(750);
                            GameEngine.ableOponentMove=true;
                        } catch(Exception e){}
                    }).start();
                } else {
                    GameEngine.ableOponentMove=true;
                }
            } else {
                for(Card curr:GameEngine.human.myBoard[card.boardType].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                    sum+=Integer.valueOf(curr.cardVal.getText());
                }

                if(sum>=10) {
                    killCards(GameEngine.human, card.boardType, maxVal);
                    new Thread(()->{
                        try {
                            Thread.sleep(750);
                            GameEngine.ableOponentMove=true;
                        } catch(Exception e){}
                    }).start();
                } else {
                    GameEngine.ableOponentMove=true;
                }
            }

            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return false;
        }
    }

    public static void alertEffect(String effectName) {
        Text sampleText=new Text(effectName+" effect has been played");
        sampleText.setFill(Color.WHITE);
        sampleText.setFont(Font.font("MedievalSharp",22));
        GameEngine.alertBox.getChildren().clear();
        GameEngine.alertBox.getChildren().add(sampleText);
        GameEngine.alertBox.setVisible(true);
        Animations.fadeIn(GameEngine.alertBox, 500);
        new Thread(()->{
            try {
                Thread.sleep(2000);
                Platform.runLater(()->{Animations.fadeOut(GameEngine.alertBox, 500);});
                Thread.sleep(500);
                GameEngine.alertBox.setVisible(false);
            } catch(Exception e){}
        }).start();
    }

    public static void killCards(Player player, int currBoard, int maxVal) {
        for(int i=0;i<player.myBoard[currBoard].cardList.size();i++) {
            Card curr=player.myBoard[currBoard].cardList.get(i);
            if(Integer.valueOf(curr.cardVal.getText())==maxVal) {
                player.deadCards.add(curr);
                new Thread(()->{
                    try {
                        Platform.runLater(()->{curr.setDying();});
                        Thread.sleep(250);
                        Animations.fadeOut(curr.thisButton, 500);
                        Thread.sleep(500);
                        Platform.runLater(()->{player.myBoard[currBoard].getCurentBoardView().getChildren().remove(curr.thisButton);});
                    } catch(Exception e){}
                }).start();
                player.myBoard[currBoard].cardList.remove(curr);
                i=-1;
            }
        }
    }
}
