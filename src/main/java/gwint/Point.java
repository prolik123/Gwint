package gwint;

public class Point {
    public int x;
    public int y;
    public int modyfierX = 0;
    public int modyfierY = 0;

    Point(int x,int y) {
        this.x = x;
        this.y = y;
    }

    Point(int x,int y,int modyfierX,int modyfierY) {
        this.x = x;
        this.y = y;
        this.modyfierX = modyfierX;
        this.modyfierY = modyfierY;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setModyfierX(int modyfierX) {
        this.modyfierX = modyfierX;
    }

    public void setModyfierY(int modyfierY) {
        this.modyfierY = modyfierY;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getModyfierX() {
        return modyfierX;
    }

    public int getModyfierY() {
        return modyfierY;
    }
}
