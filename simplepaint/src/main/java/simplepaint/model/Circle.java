package simplepaint.model;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.*;;

public class Circle implements Shape{
    private Point center;
    private int radius;
    private Color color;

    public Circle(Point center, int radius, Color color){
        this.center =center;
        this.radius = radius;
        this.color = color;
    }


    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(center.x - radius, center.y - radius, radius*2,radius*2);
    }

    @Override
    public String getType() {
        return "circle";
    }

    @Override
    public boolean contains(Point p) {
        return center.distance(p) <= radius;
    }

    @Override
    public void move(int dx, int dy) {
        center.translate(dx, dy);
    }
}
