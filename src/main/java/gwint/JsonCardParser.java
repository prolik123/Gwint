package gwint;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import org.json.*;

    /// Get a random permutation of the list
    /// Collections.shuffle(result);

public class JsonCardParser {
    
    public static List<Card> getCardsList() {
        try{
            return getCardListWithExeptions();
        }
        catch(JSONException e){
            /// failure
            throw new RuntimeException("You got JSONExeption from JsonParser");
        }
        catch(FileNotFoundException e) {
            /// failure
            throw new RuntimeException("You got FileNotFoundException from JsonParser");
        }
    }

    public static List<Card> getCardListWithExeptions() throws JSONException, FileNotFoundException{

        List<Card> result = new ArrayList<>();

        /// getting array of Json Objects form cardConfig.json
        JSONArray arr = new JSONArray(new JSONTokener(new FileReader(
            App.class.getResource("cardConfig.json").getFile())));


        /// for each element of Json Array
        for(int k=0;k<arr.length();k++) {
            Card currentCard = new Card();
            /// Getting k-th Json value form the array
            JSONObject currentJsonValue = (JSONObject) arr.get(k);
            JSONObject currentJsonCard = (JSONObject) currentJsonValue.get("card");

            /// gettting all of aribiutes
            currentCard.name = getJsonStringAttribute(currentJsonCard,"name");
                
            currentCard.imageLink = getJsonStringAttribute(currentJsonCard, "link");

            currentCard.value = getJsonIntegerAttribute(currentJsonCard, "value");

            int quantity = getJsonIntegerAttribute(currentJsonCard, "quantity");
            
            /// add qunatity times k-th Card to List
            for(int j=0;j<quantity;j++)
                result.add(currentCard);
        }

        return result;
    }

    /// You must be sure that the attribute is String
    public static String getJsonStringAttribute(JSONObject object,String attributeName) {
        return (String) object.get(attributeName);
    }

    /// You must be sure that the attribute is Integer
    public static Integer getJsonIntegerAttribute(JSONObject object,String attributeName) {
        return Integer.parseInt((String) object.get(attributeName));
    }

}
