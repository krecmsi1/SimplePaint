package simplepaint.model;

import java.awt.Graphics;
import java.awt.Point;

public interface Shape {
    void draw(Graphics g);
    String getType();
    boolean contains(Point p);
    void move(int dx, int dy);
}

