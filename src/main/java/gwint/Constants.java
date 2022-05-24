package gwint;

public class Constants {

    /// BASIC GAME SETTINGS
    public static final Integer defultNumberOfCards = 7;

    public static double width;

    public static double height;

    public static final int numberOfHearts = 2;

    public static final int botRandomBounds = 5;

    public static final int numberOfBoards = 3;

    public static final int numberOfDrawnCardBySpy = 2;

    public static final int  numberOfAllCards = 12;

    public static final int numberOfDiscardAfterStart = 2;

    public static final int numberOfHealedCardByMedic = 1;

    public static final String textOnDiscardingCard = "Select " + numberOfDiscardAfterStart + " cards to be discarded";

    public static final String textOnRevivingCard = "Select " + numberOfHealedCardByMedic + " card to be revieved";

    /// PATHS

    
    public static final String cardPathPrefix = "Cards/";

    public static final String cardPartsPathPrefix = "cardParts/";

    public static final String backgroundSoundPath = "src/main/resources/gwint/sound/";

    public static final String pathToOnHeart = "lifeOn.png";

    public static final String pathToOffHeart = "lifeOff.png";

    public static final String pathToCardsConfig = "cardConfig.json";

    public static final String linkToFontDownload = "https://fonts.googleapis.com/css2?family=MedievalSharp&display=swap";

    
    /// STYLES

    
    public static final String PASS_STYLE="-fx-background-color: transparent;";

    public static final String DECK_STYLE="-fx-background-color: transparent;";

    public static final String GRIADENT_TOP_DOWN = "-fx-background-color: linear-gradient(to bottom, rgba(0,0,0,1) 0%, rgba(0,0,0,0.4) 80%, rgba(255,255,255,0) 100%)";

    public static final String GRIADENT_BOTTOM_UP = "-fx-background-color: linear-gradient(to top, rgba(0,0,0,1) 0%, rgba(0,0,0,0.4) 80%, rgba(255,255,255,0) 100%)";

    /// CARDS EFFECTS

    public static final String[] effectName = {"isSpy","isMedic","isSnow","isRain","isFog","isClear","isBond","isDummy","isScorch","isBerserker"};

    public static final String[] effectClassNames = {"gwint.PlayInterfaces$SpyClass",
                                                     "gwint.PlayInterfaces$MedicClass",
                                                     "gwint.PlayInterfaces$SnowClass",
                                                     "gwint.PlayInterfaces$RainClass",
                                                     "gwint.PlayInterfaces$FogClass",
                                                     "gwint.PlayInterfaces$WeatherClearClass",
                                                     "gwint.PlayInterfaces$BondClass",
                                                     "gwint.PlayInterfaces$DummyClass",
                                                     "gwint.PlayInterfaces$ScorchClass",
                                                     "gwint.PlayInterfaces$BerserkerClass"};

    // SPECIAL EFFECTS

    public static final long fadeInDuration = 500;

    public static final long fadeOutDuration = 500;

    // COLOR PALETTE

    public static final String blue = "#2980b9";

    public static final String red = "#c0392b";

    public static final String TCSian = "#D4AF37";

    public static final String green = "#005005";

    Constants(double width, double height) {
        Constants.width = width;
        Constants.height = height;
    }
    
}
