package gwint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonMaker {
    public void applyDeckChanges(List<Integer> selectedCards) {
        FileWriter file = null;
        JSONArray arr = new JSONArray();
        arr.addAll(selectedCards);
        try {
            file = new FileWriter(this.getClass().getResource("deckConfig.json").getPath());
            file.write(arr.toJSONString());
            
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
