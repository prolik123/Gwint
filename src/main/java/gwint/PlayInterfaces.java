package gwint;

import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

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
            Selector selector = new Selector(player.deadCards,Constants.numberOfHealedCardByMedic,Constants.textOnRevivingCard);
            setAccepttButton(selector,player,player.deadCards,player.myCards.cardList);
            GameEngine.root.getChildren().add(selector);
            GameEngine.ableOponentMove = false;
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
            GameEngine.BoardWeather[0] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class RainClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            GameEngine.BoardWeather[1] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class FogClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            GameEngine.BoardWeather[2] = true;
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
            }
            return true;
        }
    }

    public static class ScorchClass implements PlayInterface {
        @Override
        public boolean playEffect(Card card, Player player) {
            int maxVal=-1;
            for(int i=0;i<Constants.numberOfBoards;i++) {
                for(Card curr:GameEngine.opponent.myBoard[i].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                }
                for(Card curr:GameEngine.human.myBoard[i].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                }
            }

            for(int j=0;j<Constants.numberOfBoards;j++) {
                for(int i=0;i<GameEngine.human.myBoard[j].cardList.size();i++) {
                    Card curr=GameEngine.human.myBoard[j].cardList.get(i);
                    if(Integer.valueOf(curr.cardVal.getText())==maxVal) {
                        int currBoard=j;
                        GameEngine.human.deadCards.add(curr);
                        Platform.runLater(()->{GameEngine.human.myBoard[currBoard].getCurentBoardView().getChildren().remove(curr.thisButton);});
                        GameEngine.human.myBoard[j].cardList.remove(curr);
                        i=-1;
                    }
                }

                for(int i=0;i<GameEngine.opponent.myBoard[j].cardList.size();i++) {
                    Card curr=GameEngine.opponent.myBoard[j].cardList.get(i);
                    if(Integer.valueOf(curr.cardVal.getText())==maxVal) {
                        int currBoard=j;
                        GameEngine.opponent.deadCards.add(curr);
                        Platform.runLater(()->{GameEngine.opponent.myBoard[currBoard].getCurentBoardView().getChildren().remove(curr.thisButton);});
                        GameEngine.opponent.myBoard[j].cardList.remove(curr);
                        i=-1;
                    }
                }
            }
            return true;
        }
    }

    public static class BerserkerClass implements PlayInterface {
        @Override
        public boolean playEffect(Card card, Player player) {
            int maxVal=-1;
            int sum=0;
            if(player == GameEngine.human) {
                for(Card curr:GameEngine.opponent.myBoard[card.boardType].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                    sum+=Integer.valueOf(curr.cardVal.getText());
                }

                if(sum>=10) {
                    for(int i=0;i<GameEngine.opponent.myBoard[card.boardType].cardList.size();i++) {
                        Card curr=GameEngine.opponent.myBoard[card.boardType].cardList.get(i);
                        if(Integer.valueOf(curr.cardVal.getText())==maxVal) {
                            GameEngine.opponent.deadCards.add(curr);
                            Platform.runLater(()->{GameEngine.opponent.myBoard[card.boardType].getCurentBoardView().getChildren().remove(curr.thisButton);});
                            GameEngine.opponent.myBoard[card.boardType].cardList.remove(curr);
                            i=-1;
                        }
                    }
                }
            } else {
                for(Card curr:GameEngine.human.myBoard[card.boardType].cardList) {
                    if(Integer.valueOf(curr.cardVal.getText())>maxVal) maxVal=Integer.valueOf(curr.cardVal.getText());
                    sum+=Integer.valueOf(curr.cardVal.getText());
                }

                if(sum>=10) {
                    for(int i=0;i<GameEngine.human.myBoard[card.boardType].cardList.size();i++) {
                        Card curr=GameEngine.human.myBoard[card.boardType].cardList.get(i);
                        if(Integer.valueOf(curr.cardVal.getText())==maxVal) {
                            GameEngine.human.deadCards.add(curr);
                            Platform.runLater(()->{GameEngine.human.myBoard[card.boardType].getCurentBoardView().getChildren().remove(curr.thisButton);});
                            GameEngine.human.myBoard[card.boardType].cardList.remove(curr);
                            i=-1;
                        }
                    }
                }
            }

            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return false;
        }
    }
}
