package gwint;


public class Card {
    public String imageLink;
    public int value;
    public String name;
    public int boardType;

    @Override
    public String toString() {
        return imageLink + " " + name + " "+ value + " " + boardType;
    }
}
