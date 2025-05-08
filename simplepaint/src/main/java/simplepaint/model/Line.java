package simplepaint.model;

import java.awt.*;

public class Line implements Shape {
    private Point start, end;
    private Color color;
    
    public Line(Point start, Point end, Color color){
        this.start = start;
        this.end = end;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawLine(start.x, start.y, end.x,end.y);
    }

    @Override
    public String getType() {
        return "line";
    }

    @Override
    public boolean contains(Point p) {
        double tolerance = 4;
        double distance = ptSegDist(start.x, start.y, end.x,end.y,p.x,p.y);
        return distance <= tolerance;
    }

    @Override
    public void move(int dx, int dy) {
        start.translate(dx, dy);
        end.translate(dx, dy);
    }

    private double ptSegDist(double x1, double y1, double x2, double y2, double px, double py) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        if (dx == 0 && dy == 0) {
            return Point.distance(x1, y1, px, py);
        }
        double t = ((px - x1) * dx + (py - y1) * dy) / (dx * dx + dy * dy);
        t = Math.max(0, Math.min(1, t));
        double projX = x1 + t * dx;
        double projY = y1 + t * dy;
        return Point.distance(projX, projY, px, py);
    }


    
}
