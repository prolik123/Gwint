package gwint;

import java.util.*;


public class GameEngine {
    
    public static Player opponent;
    public static Player human;
    public static Integer defultNumberOfCards = 7;

    GameEngine() {
        human = new Player();
        opponent = new Player();

    }

    public static void addCardToHand(BoardSlot Board,Stack<Card> Stack){
        if(Stack.empty())
            return;
        Card New = Stack.lastElement();
        Stack.pop();
        Board.cardList.add(New);
        Board.addCardToBoardView(New, Board.currentView);
    }

}

