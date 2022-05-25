package gwint;

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.io.FileWriter;
import java.io.IOException;

import org.json.*;

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
        JSONArray arr = getJsonArray(Constants.pathToCardsConfig);

        /// for each element of Json Array
        for(int k=0;k<arr.length();k++) {
            /// Getting k-th Json value form the array
            JSONObject currentJsonValue = (JSONObject) arr.get(k);
            JSONObject currentJsonCard = (JSONObject) currentJsonValue.get("card");

            /// gettting all of aribiutes
            int quantity = getJsonIntegerAttribute(currentJsonCard, "quantity");

            for(int j=0;j<quantity;j++) {
                Card currentCard = new Card();

                currentCard.name = getJsonStringAttribute(currentJsonCard,"name");
                    
                currentCard.imageLink = getJsonStringAttribute(currentJsonCard, "link");

                currentCard.value = getJsonIntegerAttribute(currentJsonCard, "value");

                currentCard.boardType = getJsonIntegerAttribute(currentJsonCard, "board");
                
                for(int It = 0;It<Constants.effectName.length;It++) {
                    try{
                        String currInterface = getJsonStringAttribute(currentJsonCard, Constants.effectName[It]);
                        if(currInterface.equals("true")) {
                            Class<?>[] classArr = PlayInterfaces.class.getClasses();
                            for(Class<?> classIt:classArr) {
                                if(classIt.getName().equals(Constants.effectClassNames[It])) 
                                    currentCard.effectArray.add((PlayInterface)classIt.getConstructor().newInstance());
                            }
                        }
                    }
                    catch(Exception e) {
                        /// Card does not has that interface
                    }
                }

                result.add(currentCard);
            }
        }

        return result;
    }

    public static List<Integer> getDeckConfigList() {
        try{
            return getDeckConfigListWithExceptions();
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

    public static List<Integer> getDeckConfigListWithExceptions() throws JSONException, FileNotFoundException{
        List<Integer> result = new ArrayList<>();

        /// getting array of Json Objects form cardConfig.json
        JSONArray arr = getJsonArray(Constants.pathToDeckConfig);

        /// for each element of Json Array
        for(int k=0;k<arr.length();k++) {
           result.add(arr.getInt(k));
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

    /// Returns the json array from given path
    public static JSONArray getJsonArray(String path) throws JSONException, FileNotFoundException {
        return new JSONArray(new JSONTokener(new FileReader(
            App.class.getResource(path).getFile())));
    }


}
