package gwint;

public class Constants {

    /// BASIC GAME SETTINGS
    public static final Integer defultNumberOfCards = 7;

    public static double ratio;

    public static final int numberOfHearts = 2;


    /// PATHS

    public static final String backgroundSoundPath = "sound/background.mp3";

    public static final String pathToOnHeart = "lifeOn.png";

    public static final String pathToOffHeart = "lifeOff.png";

    public static final String pathToCardsConfig = "cardConfig.json";

    public static final String linkToFontDownload = "https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap";



    /// POSITIONS

    public static final Point positionOfHumanHearts = new Point(35, 92,5,0);

    public static final Point positionOfOpponentHearts = new Point(35,77,5,0);

    public static final Point positionOfHumanPass = new Point(11,119);

    public static final Point positionOfOpponentPass = new Point(11, 46);

    public static final Point positionOfHumanBoards = new Point(72,98,0,24);

    public static final Point positionOfOpponentBoards = new Point(72,70,0,-26);

    public static final Point positionOfHumanHand = new Point(57, 170);

    public static final Point positionOfDeck = new Point(171,110);



    /// STYLES

    
    public static final String PASS_STYLE="-fx-background-color: transparent;";

    public static final String DECK_STYLE="-fx-background-color: transparent;";





    Constants(double ratio) {
        Constants.ratio = ratio;
    }
    
}
