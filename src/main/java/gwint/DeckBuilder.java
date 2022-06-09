package gwint;

import javafx.geometry.Insets;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import org.apache.commons.collections4.BidiMap;

import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;


import java.net.URL;
import java.util.HashMap;

import java.util.List;
import java.util.ResourceBundle;

public class DeckBuilder implements Initializable {
    @FXML
    //TilePane tilePane;
    TilePane tilePane;
    @FXML
    Button playButton;
    @FXML
    Button backButton;

    private List <Card> cards;

    //map that has relation 1:1
    private BidiMap<Integer, Button> cardsOnPane;

    private List <Integer> selectedCards;

    private int howManySelected = 20;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tilePane.setAlignment(Pos.CENTER);
        cards = JsonCardParser.getCardsList();
        cardsOnPane = new DualHashBidiMap<>();


        selectedCards = JsonCardParser.getDeckConfigList();

        for (int i = 0; i < cards.size(); i++){
            Card card = cards.get(i);

            Button currentButton = card.genCardView();
            currentButton.setOnMouseClicked(null);
            currentButton.setPadding(new Insets(40, 40, 40 , 40));
            cardsOnPane.put(i, currentButton);
            currentButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if(!card.selected && howManySelected < Constants.maxNumberOfCardsInDeck) {
                        card.selected = true;
                        selectedCards.add(cardsOnPane.inverseBidiMap().get(currentButton));
                        howManySelected++;
                        Animations.scaleTo(currentButton, 1.5, 1.5, 100);
                        currentButton.setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.web("#D4AF37"), 10, 0.5, 0.0, 0.0));
                    }
                    else if(card.selected) {
                        card.selected = false;
                        selectedCards.remove((Object) cardsOnPane.inverseBidiMap().get(currentButton));
                        howManySelected--;
                        Animations.scaleTo(currentButton, 1.25, 1.25, 100);
                        currentButton.setEffect(null);
                    }
                    //System.out.println(selectedCards);
                }
            });
            currentButton.setScaleX(1.25);
            currentButton.setScaleY(1.25);
            tilePane.getChildren().add(currentButton);
        }
        SelectDeckFromConfig();
    }

    public void handleSaveButtonAction(ActionEvent event) {
        if (selectedCards.size() < Constants.minNumberOfCardsInDeck) return;
        JsonMaker.applyDeckChanges(selectedCards, this);
        App.switchScene("MainMenu");
    }

    //does not apply changes
    public void handleDiscardButtonAction(ActionEvent event) {
        App.switchScene("MainMenu");
    }

    public void handleEntered(MouseEvent e){

        Animations.scaleTo( e.getPickResult().getIntersectedNode(), 1.2, 1.2, 100);
    }

    public void handleExited(MouseEvent e){
        ScaleTransition btnTrans=new ScaleTransition(Duration.millis(100), (Node) e.getSource());
        btnTrans.setToX(1);
        btnTrans.setToY(1);
        btnTrans.play();
    }

    private void SelectDeckFromConfig(){
        if (selectedCards.size() < Constants.minNumberOfCardsInDeck) throw new RuntimeException("Wrong number of Cards in deckConfig.json");
        for (Integer i : selectedCards){
            cards.get(i).selected = true;
            cardsOnPane.get(i).setScaleX(1.5);
            cardsOnPane.get(i).setScaleY(1.5);
            cardsOnPane.get(i).setEffect(new InnerShadow(BlurType.GAUSSIAN, Color.web("#D4AF37"), 10, 0.5, 0.0, 0.0));
        }
    }
}
