package gwint;

import java.util.*;


public class GameEngine {
    
    /// class for all bot's functions
    public static Player opponent;

    /// class for all player's functions
    public static Player human;

    /// How many cards Players gets on hand after starting the game
    public static Integer defultNumberOfCards = 7;

    /// Constructor 
    GameEngine() {
        human = new Player();
        opponent = new Player();

    }

    /// Function which gets hand, stack and add new card to hand ( and View ) 
    public static void addCardToHand(BoardSlot Board,Stack<Card> Stack){
        if(Stack.empty())
            return;
        Card New = Stack.lastElement();
        Stack.pop();
        Board.cardList.add(New);
        Board.addCardToBoardView(New, Board.currentView);
    }

}

