package gwint;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import org.json.*;

public class JsonCardParser {
    
    public static List<Card> getCardList() throws JSONException, FileNotFoundException{

        List<Card> result = new ArrayList<>();

        /// getting array of Json Objects form cardConfig.json
        JSONArray arr = new JSONArray(new JSONTokener(new FileReader(
            App.class.getResource("cardConfig.json").getFile())));


        /// for each element of Json Array
        for(int k=0;k<arr.length();k++) {
            Card currentCard = new Card();
            /// Getting k-th Json value form the array
            JSONObject currentJsonValue = (JSONObject) arr.get(k);

            /// gettting all of aribiutes
            currentCard.name = (String) (((JSONObject)
                currentJsonValue.get("card")).get("name"));
                
            currentCard.imageLink = (String) (((JSONObject)
                currentJsonValue.get("card")).get("link"));

            currentCard.value = Integer.parseInt(((String) 
                (((JSONObject)currentJsonValue.get("card")).get("value"))));

            int quantity = Integer.parseInt((String) 
                (((JSONObject)currentJsonValue.get("card")).get("quantity")));
            
            /// add qunatity times k-th Card to List
            for(int j=0;j<quantity;j++)
                result.add(currentCard);
        }

        /// Get a random permutation of the list
        Collections.shuffle(result);
        return result;
    }
}