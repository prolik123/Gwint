package gwint;

public class Card {
    public String imageLink;
    public int value;
    public String name;

    //Debug mainly
    @Override
    public String toString() {
        return imageLink + " " + name + " "+ value;
    }
}
