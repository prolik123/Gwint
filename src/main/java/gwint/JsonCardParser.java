package gwint;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Stack;
import org.json.*;

public class JsonCardParser {
    
    public static Stack<Card> getCardStack() throws JSONException, FileNotFoundException{
        System.out.println("WESZLEM");
        Stack<Card> result = new Stack<>();
        JSONArray temp = new JSONArray(new JSONTokener(new FileReader(
            App.class.getResource("cardConfig.json").getFile())));

        for(int k=0;k<temp.length();k++) {
            Card curr = new Card();
            JSONObject t = (JSONObject) temp.get(k);
            curr.name = (String) (((JSONObject)t.get("card")).get("name"));
            curr.imageLink = (String) (((JSONObject)t.get("card")).get("link"));
            curr.value = (Integer) (((JSONObject)t.get("card")).get("value"));
            int quantity = (Integer) (((JSONObject)t.get("card")).get("quantity"));
            System.out.println(curr);
            for(int j=0;j<quantity;j++)
                result.add(curr);
        }
        for (Card object : result) {
            System.out.println(object);
        }
        return result;
    }
}
