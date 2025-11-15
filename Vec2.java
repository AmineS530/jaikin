package jaikin;
public class Vec2 {
    final double x, y; 

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public Vec2 add(Vec2 other) {
        return new Vec2(this.x + other.x, this.y + other.y);
    }
    public Vec2 sub(Vec2 other) {
        return new Vec2(this.x - other.x, this.y - other.y);
    }
    public Vec2 scale(double scalar) {
        return new Vec2(this.x * scalar, this.y * scalar);
    }
}