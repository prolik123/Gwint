package gwint;

public class Constants {

    /// How many cards Players gets on hand after starting the game
    public static final Integer defultNumberOfCards = 7;

    public static final String pathToCardsConfig = "cardConfig.json";

    public static double ratio;

    public static final String PASS_STYLE="-fx-background-color: transparent;";

    public static final String DECK_STYLE="-fx-background-color: transparent;";

    public static final int numberOfHearts = 2;

    Constants(double ratio) {
        Constants.ratio = ratio;
    }
    
}
