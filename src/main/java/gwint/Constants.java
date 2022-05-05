package gwint;

public class Constants {

    /// BASIC GAME SETTINGS
    public static final Integer defultNumberOfCards = 7;

    public static double width;

    public static double height;

    public static final int numberOfHearts = 2;

    public static final int botRandomBounds = 5;

    public static final int numberOfBoards = 3;


    /// PATHS

    public static final String backgroundSoundPath = "sound/background.mp3";

    public static final String pathToOnHeart = "lifeOn.png";

    public static final String pathToOffHeart = "lifeOff.png";

    public static final String pathToCardsConfig = "cardConfig.json";

    public static final String linkToFontDownload = "https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap";

    
    /// STYLES

    
    public static final String PASS_STYLE="-fx-background-color: transparent;";

    public static final String DECK_STYLE="-fx-background-color: transparent;";





    Constants(double width, double height) {
        Constants.width = width;
        Constants.height = height;
    }
    
}
