package Lab6;

import java.awt.*;
import java.awt.geom.AffineTransform;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Lab6_66050019 extends JPanel{

    public static void main(String[] args) {
        Lab6_66050019 m = new Lab6_66050019();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public void paintComponent(Graphics g){
        // task1(g);
        task2(g);
    }
    public void task1(Graphics g){
        int x = 200;
        int y = 200;
        int size = 200;
        double centerX = x + size / 2.0;
        double centerY = y + size / 2.0;
        Graphics2D g2 = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(1,0,0,1,100,100);
        at.rotate((Math.toRadians(30)), centerX, centerY);
        g2.setTransform(at);
        g2.drawRect(x,y,size,size);
    }
    public void task2(Graphics g){
        Graphics2D g2 = (Graphics2D) g.create();
        AffineTransform at = new AffineTransform(2, 0,0, 2,0,0);
        at.translate(-50, 50);
        g2.setTransform(at);
        g2.drawRect(200,200,200,200);
    }
}