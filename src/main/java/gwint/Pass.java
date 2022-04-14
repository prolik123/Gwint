package gwint;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

public class Pass extends HBox {
    
    public Pass(double ratio) {
        Button passEntity=new Button();
        ImageView imView=new ImageView(new Image(App.class.getResource("pass.png").toExternalForm()));
        imView.setFitHeight(75*ratio);
        imView.setFitWidth(200*ratio);
        passEntity.setGraphic(imView);
        passEntity.setStyle(Constants.PASS_STYLE);
        //passEntity.setVisible(false);

        getChildren().add(passEntity);
    }
    
}
