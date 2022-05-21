package gwint;
import java.util.*;

import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class Card {
    public String imageLink;
    public int value;
    public String name;
    public int boardType;
    public boolean selected = false;

    Text cardVal;

    //He is shouting some java nonsense here, so I muted him
    @SuppressWarnings("all")
    public ArrayList<PlayInterface> effectArray = new ArrayList<>();

    //Debug mainly
    @Override
    public String toString() {
        return imageLink + " " + name + " "+ value + " " + boardType;
    }

    //Generates the card graphics 
    public Button genCardView() {
        //Gets card class lol
        String cardClass=getCardClass();

        //The following objects are parts of the cards final look
        //It consits of the background(cardView), border(borderView) and icons
        Image cardImage = new Image(App.class.getResource(Constants.cardPathPrefix+imageLink).toExternalForm());
        ImageView cardView = new ImageView(cardImage);

        Image borderImage = new Image(App.class.getResource("cardParts/border.png").toExternalForm());
        ImageView borderView = new ImageView(borderImage);

        Image shieldImage = new Image(App.class.getResource("cardParts/shield.png").toExternalForm());
        ImageView shieldView = new ImageView(shieldImage);

        Image lineImage = new Image(App.class.getResource("cardParts/"+String.valueOf(boardType)+".png").toExternalForm());
        ImageView lineView = new ImageView(lineImage);

        cardVal=new Text(String.valueOf(value));
        cardVal.setFont((Font.font("MedievalSharp",16)));

        StackPane shieldVal=new StackPane(shieldView,cardVal);

        VBox icons=new VBox(5);
        icons.setMaxWidth(shieldImage.getWidth());

        if(!Arrays.asList("FogClass","RainClass","SnowClass","WeatherClearClass").contains(cardClass)){
            icons.getChildren().addAll(shieldVal,lineView);
        }

        if(cardClass!=null) {
            Image classImage = new Image(App.class.getResource("cardParts/"+cardClass+".png").toExternalForm());
            ImageView classView = new ImageView(classImage);
            icons.getChildren().add(classView);
        }

        double newWidth=((Constants.height-84.0)/7.0/200.0)*150.0;
        double newHeight=(Constants.height-84.0)/7.0;

        borderView.setFitHeight(newHeight);
        borderView.setFitWidth(newWidth);
        cardView.setFitHeight(newHeight);
        cardView.setFitWidth(newWidth);

        StackPane cardPane=new StackPane(cardView,icons,borderView);
        StackPane.setMargin(icons, new Insets(5));
        cardPane.setAlignment(Pos.TOP_LEFT);

        Button btn = new Button();

        btn.setGraphic(cardPane);
        btn.setStyle(Constants.DECK_STYLE);

        Animations.fadeIn(btn, Constants.fadeInDuration);

        return btn;
    }

    public String getCardClass() {
        if(effectArray.size()==0) return null;
        return effectArray.get(0).getClass().getSimpleName().toString();
    }

    public void badEffect() {
        cardVal.setText("1");
        cardVal.setFill(Color.valueOf(Constants.red));
    }

    public void goodEffect(int newVal) {
        cardVal.setText(String.valueOf(newVal));
        if(!cardVal.getFill().equals(Color.valueOf(Constants.red))) cardVal.setFill(Color.valueOf(Constants.green));
    }

    public void removeEffect() {
        cardVal.setText(String.valueOf(value));
        cardVal.setFill(Color.BLACK);
    }
}
