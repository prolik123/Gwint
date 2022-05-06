package gwint;
import java.util.*;

import javafx.animation.FadeTransition;
import javafx.geometry.*;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javafx.util.Duration;

public class Card {
    public String imageLink;
    public int value;
    public String name;
    public int boardType;
    public ArrayList<PlayInterface> effectArray = new ArrayList<>();

    //Debug mainly
    @Override
    public String toString() {
        return imageLink + " " + name + " "+ value + " " + boardType;
    }

    //Generates the card graphics 
    public Button genCardView() {
        //The following objects are parts of the cards final look
        //It consits of the background(cardView), border(borderView) and icons
        Image cardImage = new Image(App.class.getResource(Constants.cardPathPrefix+imageLink).toExternalForm());
        ImageView cardView = new ImageView(cardImage);

        Image borderImage = new Image(App.class.getResource("cardParts/border.png").toExternalForm());
        ImageView borderView = new ImageView(borderImage);

        Image shieldImage = new Image(App.class.getResource("cardParts/shield.png").toExternalForm());
        ImageView shieldView = new ImageView(shieldImage);

        Text cardVal=new Text(String.valueOf(value));
        cardVal.setFont((Font.font("MedievalSharp",16)));

        StackPane shieldVal=new StackPane(shieldView,cardVal);

        VBox icons=new VBox(5);
        icons.setMaxWidth(shieldImage.getWidth());
        icons.getChildren().add(shieldVal);

        //TODO: ZMIEN TO JAK JUZ TO WRZUCICIE DO JSONA
        if(name.equals("1")) {
            Image eyeImage = new Image(App.class.getResource("cardParts/eye.png").toExternalForm());
            ImageView eyeView = new ImageView(eyeImage);
            icons.getChildren().add(eyeView);
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

        FadeTransition btnTrans=new FadeTransition(Duration.millis(500), btn);
        btnTrans.setFromValue(0.0);
        btnTrans.setToValue(1.0);
        btn.setGraphic(cardPane);
        btn.setStyle(Constants.DECK_STYLE);

        btnTrans.play();

        return btn;
    }
}
