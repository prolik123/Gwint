package gwint;
import java.util.*;

public class Card {
    public String imageLink;
    public int value;
    public String name;
    public int boardType;
    public ArrayList<PlayInterface> effectArray = new ArrayList<>();

    //Debug mainly
    @Override
    public String toString() {
        return imageLink + " " + name + " "+ value + " " + boardType;
    }
}
