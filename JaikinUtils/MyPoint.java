package JaikinUtils;

public class MyPoint {

    public float x;
    public float y;

    public MyPoint(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public static MyPoint fromAwt(java.awt.Point p) {
        return new MyPoint(p.x, p.y);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
