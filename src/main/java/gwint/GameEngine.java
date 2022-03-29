package gwint;

import java.util.*;

public class GameEngine {
    
    public List<Card> myCards;
    public List<Card> opponentCards;
    public Stack<Card> myCardStack;
    public Stack<Card> opponentCardStack;
    boolean myPass = false;
    boolean opponentPass = false;
    int myBoradValue = 0;
    int opponentBoardValue = 0;
    int startNumberOfCards = 7;

    public Board myBoard;
    public Board opponentBoard;

    GameEngine() {
        myCards = JsonCardParser.getCardsList();

    }
}
