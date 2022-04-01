package gwint;

import java.util.*;


public class GameEngine {
    
    public BoardSlot myCards;
    public BoardSlot opponentCards;
    public Stack<Card> myCardStack;
    public Stack<Card> opponentCardStack;
    boolean myPass = false;
    boolean opponentPass = false;
    int myBoradValue = 0;
    int opponentBoardValue = 0;
    int startNumberOfCards = 7;

    public BoardSlot myBoard[] = new BoardSlot[3];
    public BoardSlot opponentBoard[] = new BoardSlot[3];

    GameEngine() {
        myCards = new BoardSlot();
        myCardStack = new Stack<>();
        myCards.cardList = JsonCardParser.getCardsList();
        getListPermutation(myCards.cardList);
        chooseHandCards(myCards.cardList, myCardStack);
        myCards.hand = true;
        for(int k=0;k<3;k++)
            myBoard[k] = new BoardSlot();
        myCards.setBoards(myBoard);

        opponentCards = new BoardSlot();
        opponentCardStack = new Stack<>();
        opponentCards.cardList = JsonCardParser.getCardsList();
        getListPermutation(opponentCards.cardList);
        chooseHandCards(opponentCards.cardList, opponentCardStack);

    }

    /// getting startNumberOfCards to list and the rest lies on Stack
    private void chooseHandCards(List<Card> currList, Stack<Card> currStack){
        while(currList.size() > startNumberOfCards) {
            currStack.add(currList.get(currList.size()-1));
            currList.remove(currList.size()-1);
        }
    }

    /// Get a random permutation of the list
    public void getListPermutation(List<?> currList){
        Collections.shuffle(currList);
    }

    public void addCardToHand(BoardSlot Board,Stack<Card> Stack){
        if(Stack.empty())
            return;
        Card New = Stack.lastElement();
        Stack.pop();
        Board.cardList.add(New);
        Board.addCardToBoardView(New, Board.currentView);
    }

}

