package gwint;

import java.util.ArrayList;
import java.util.List;

public class DeckLoader {
    public static List<Card> loadAvailableDeck(){
        List<Card> listOfAllCards = JsonCardParser.getCardsList();
        List<Integer> listOfIndexes = JsonCardParser.getDeckConfigList();
        List<Card> result = new ArrayList<>();

        for (Integer i : listOfIndexes)
            result.add(listOfAllCards.get(i));

        return result;
    }
}
