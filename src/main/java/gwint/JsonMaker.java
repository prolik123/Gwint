package gwint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonMaker {
    public static void applyDeckChanges(List<Integer> selectedCards) {
        FileWriter file = null;
        JSONArray arr = new JSONArray();
        arr.addAll(selectedCards);
        try {

            // Constructs a FileWriter given a file name, using the platform's default charset
            file = new FileWriter(Constants.pathToDeckConfig);
            file.write(arr.toJSONString());


        } catch (IOException e) {
            e.printStackTrace();

        } finally {

            try {
                file.flush();
                file.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
