package gwint;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class JsonMaker {
    public static void applyDeckChanges(List<Integer> selectedCards, Object obj) {
        FileWriter file = null;
        JSONArray arr = new JSONArray();
        arr.addAll(selectedCards);
        try {
            file = new FileWriter(obj.getClass().getResource("deckConfig.json").getPath());
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

    public static void applyVolumeChanges(double backgroundMusicVolume, double soundEffectVolume, Object obj){
        FileWriter file = null;
        JSONArray arr = new JSONArray();
        arr.add(backgroundMusicVolume);
        arr.add(soundEffectVolume);
        try {
            file = new FileWriter(obj.getClass().getResource("volumeConfig.json").getPath());
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
