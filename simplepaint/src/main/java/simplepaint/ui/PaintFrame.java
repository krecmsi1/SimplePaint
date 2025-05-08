package simplepaint.ui;

import simplepaint.util.ShapeSerializer;

import javax.swing.*;

import simplepaint.model.Shape;
import simplepaint.model.Circle;
import simplepaint.model.Line;

import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.*;;

public class PaintFrame extends JFrame {

    private final List<Shape> shapes = new ArrayList<>();
    private Point start;
    private String mode = "Line";
    private Color color = Color.BLACK;
    private Color backColor = Color.WHITE;
    private boolean darkMode = false;

    public PaintFrame(){
        super("Draw");
        setSize(800,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        JPanel toolBar = new JPanel();
        JButton lineButton = new JButton("Line");
        JButton circleButton = new JButton("Circle");
        JButton clearButton = new JButton("Clear");
        JButton saveButton = new JButton("Save");
        JButton colorButton = new JButton("Color");
        JButton loadButton = new JButton("Load");
        JCheckBox darkModCheckBox = new JCheckBox("Dark mode");

        toolBar.add(lineButton);
        toolBar.add(circleButton);
        toolBar.add(clearButton);
        toolBar.add(saveButton);
        toolBar.add(loadButton);
        toolBar.add(colorButton);
        toolBar.add(darkModCheckBox);

        add(toolBar, BorderLayout.NORTH);

        DrawingPanel drawPanel = new DrawingPanel();

        add(drawPanel, BorderLayout.CENTER);

        lineButton.addActionListener(e -> mode = "Line");
        circleButton.addActionListener(e -> mode = "Circle");
        clearButton.addActionListener(e -> {
            shapes.clear();
            drawPanel.repaint();
        });
        saveButton.addActionListener(e-> ShapeSerializer.saveToJson(shapes));

        loadButton.addActionListener(e->{
            List<Shape> loaded = ShapeSerializer.loadFromJson();
            if(loaded != null){
                shapes.clear();
                shapes.addAll(loaded);
                drawPanel.repaint();
            }
        });

        colorButton.addActionListener(e->{
            color = JColorChooser.showDialog(this, "Choose a color", color);
        });

        darkModCheckBox.addActionListener(e->{
            darkMode = darkModCheckBox.isSelected();
            toogleDarkMode();
            drawPanel.setBackground(backColor);
            drawPanel.repaint();
        });

        setVisible(true);
    }


    private void toogleDarkMode(){
        if(darkMode){
            backColor = Color.DARK_GRAY;
            color = Color.WHITE;
        }
        else{
            backColor = Color.WHITE;
            color = Color.BLACK;
        }

        getContentPane().setBackground(backColor);
    }

    private class DrawingPanel extends JPanel{

        private Shape selectShape = null;
        private Point dragPoint = null;
        public DrawingPanel(){
            
            setBackground(Color.WHITE);
            addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent e){
                    start = e.getPoint();
                    for( int i = shapes.size() - 1;i >=0 ; i--){
                        if(shapes.get(i).contains(start)){
                            selectShape = shapes.get(i);
                            dragPoint = start;
                            break;
                        }
                    }
                }

                public void mouseReleased(MouseEvent e){
                    Point end = e.getPoint();
                    if(selectShape == null){
                        
                        Shape shape;
                        if("Line".equals(mode)){
                            shape = new Line(start, end, color);
                        }
                        else{
                            int radius = (int) start.distance(end);
                            shape = new Circle(start, radius, color);
                        }
                        shapes.add(shape);
                    }
                    
                    selectShape = null;
                    dragPoint = null;
                    repaint();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter() {
              public void mouseDragged(MouseEvent e){
                if(selectShape != null && dragPoint != null){
                    Point end = e.getPoint();
                    int dx = end.x - dragPoint.x;
                    int dy = end.y - dragPoint.y;
                    selectShape.move(dx, dy);
                    dragPoint = end;
                    repaint();
                }
              }  
            });
        }



        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for( Shape shape: shapes){
                shape.draw(g);
            }
        }
    }


}
