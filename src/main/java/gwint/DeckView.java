package gwint;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

public class DeckView {
    public double width;
    public double height;
    Text cnt;
    Button btn;

    public DeckView() {
        width=((Constants.height-84.0)/7.0/200.0)*150.0;
        height=(Constants.height-84.0)/7.0;

        cnt=new Text();
        cnt.setFont((Font.font("MedievalSharp",32)));
        cnt.setFill(Color.WHITE);
    }
    
    public Button genDeckView() {
        btn=new Button();
        setStyle("back.png");
        cnt.setText(String.valueOf(Constants.numberOfAllCards-Constants.defultNumberOfCards));

        return btn;
    }

    public void changeDeckVal(int newVal) {
        if(newVal>0) cnt.setText(String.valueOf(newVal));
        else btn.setVisible(false);
    }

    public Button genDeadView() {
        btn=new Button();
        setStyle("dead.png");
        btn.setVisible(false);
        cnt.setText(String.valueOf(0));

        return btn;
    }

    public void changeDeadVal(int newVal) {
        if(Integer.parseInt(cnt.getText())==0 && newVal!=0) btn.setVisible(true);
        cnt.setText(String.valueOf(Integer.parseInt(cnt.getText())+newVal));
    }

    void setStyle(String url) {
        ImageView imView=new ImageView(new Image(App.class.getResource(url).toExternalForm()));
        imView.setFitHeight(height);
        imView.setFitWidth(width);

        StackPane cardView=new StackPane(imView,cnt);
        StackPane.setAlignment(cnt, Pos.TOP_CENTER);
        StackPane.setMargin(cnt, new Insets(5));

        btn.setGraphic(cardView);
        btn.setStyle(Constants.DECK_STYLE);
    }
}   
