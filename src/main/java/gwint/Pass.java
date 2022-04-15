package gwint;

import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Pass extends HBox {
    
    public Pass(double ratio) {
        Text pass=new Text();
        pass.setFill(Color.WHITE);
        pass.setText("Passed");
        pass.setFont(Font.font("MedievalSharp",40));

        getChildren().add(pass);
    }
    
}
