package simplepaint;

import org.junit.jupiter.api.Test;

import simplepaint.model.Line;

import java.awt.Color;
import java.awt.Point;

import static org.junit.jupiter.api.Assertions.*;

public class ShapeTest {
    
    @Test
    public void testLineCreation(){
        Line line = new Line(new Point(0,0),new Point(20,20), Color.red);
        assertNotNull(line);
    }
    @Test
    public void testCircleRadius(){
        Point center = new Point(0,0);
        Point edge = new Point(3,5);
        int radius = (int) center.distance(edge);
        assertEquals(5,radius);
    }
}
