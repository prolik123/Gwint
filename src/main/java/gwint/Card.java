package gwint;

public class Card {
    public String imageLink;
    public int value;
    public String name;

    @Override
    public String toString() {
        return imageLink + " " + name + " "+ value;
    }
}
