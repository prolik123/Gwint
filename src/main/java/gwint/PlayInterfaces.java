package gwint;

import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

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

    public static class WeatherFirstLineClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            GameEngine.BoardWeather[0] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class WeatherSecondLineClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            GameEngine.BoardWeather[1] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }

    public static class WeatherThirdLineClass implements PlayInterface {

        @Override
        public boolean playEffect(Card card, Player player) {
            GameEngine.BoardWeather[2] = true;
            GameEngine.human.updateValue();
            GameEngine.opponent.updateValue();
            return true;
        }
    }
}
