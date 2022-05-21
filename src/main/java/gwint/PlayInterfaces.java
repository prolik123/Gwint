package gwint;

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
                GameEngine.addCardToHand(player.myCards, player.myCardStack);
            }
            return true;
        }

    }
}
