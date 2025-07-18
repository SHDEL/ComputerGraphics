import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

import javax.swing.JPanel;
import javax.swing.JFrame;

import java.util.Queue;

public class Lab3_66050019 extends JPanel{
    public static void main(String[] args) {
        Lab3_66050019 m = new Lab3_66050019();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
    }
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);
        drawBezier(g);
        // drawpolygonLine(g);

    }
    private void drawpolygonLine(Graphics g){
        // int xPoly[] = {150, 250, 325, 375, 400, 275, 100};
        // int yPoly[] = {150, 100, 125, 225, 325, 375, 300};
        // Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        // g.drawPolygon(poly);

        //     Polygon poly = new Polygon();
        //     poly.addPoint(150, 150);
        //     poly.addPoint(250, 100);
        //     poly.addPoint(325, 125);
        //     poly.addPoint(375, 225);
        //     poly.addPoint(400, 325);
        //     poly.addPoint(275, 375);
        //     poly.addPoint(100, 300);
        //     g.drawPolygon(poly);

        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = buffer.createGraphics();

        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, 600, 600);

        int xPoly[] = {150, 250, 325, 375, 400, 275, 100};
        int yPoly[] = {150, 100, 125, 225, 325, 375, 300};
        Polygon poly = new Polygon(xPoly, yPoly, xPoly.length);
        g2.setColor(Color.black);
        g2.drawPolygon(poly);

        buffer = floodFill(buffer, 200, 150, Color.WHITE, Color.GREEN);
        g.drawImage(buffer, 0, 0, null);
    }
    public BufferedImage floodFill(BufferedImage img, int x, int y, Color target_colour, Color replacement_colour){
        int targetRGB = img.getRGB(x, y);
        int replacementRGB = replacement_colour.getRGB();
        

        Queue<Point> Q = new LinkedList<>();
        img.setRGB(x, y, replacementRGB);
        Q.add(new Point(x, y));

        while (!Q.isEmpty()) {
            Point p = Q.remove();
            int px = p.x, py = p.y;

            // South
            if (py + 1 < img.getHeight() && img.getRGB(px, py + 1) == targetRGB) {
                img.setRGB(px, py + 1, replacementRGB);
                Q.add(new Point(px, py + 1));
            }
            // North
            if (py - 1 >= 0 && img.getRGB(px, py - 1) == targetRGB) {
                img.setRGB(px, py - 1, replacementRGB);
                Q.add(new Point(px, py - 1));
            }
            // East
            if (px + 1 < img.getWidth() && img.getRGB(px + 1, py) == targetRGB) {
                img.setRGB(px + 1, py, replacementRGB);
                Q.add(new Point(px + 1, py));
            }
            // West
            if (px - 1 >= 0 && img.getRGB(px - 1, py) == targetRGB) {
                img.setRGB(px - 1, py, replacementRGB);
                Q.add(new Point(px - 1, py));
            }
        }
        return img;
    }
    private void drawBezier(Graphics g){
        //default
        // bezierCurve(g, 100, 500, 150, 100, 450, 100, 500, 500);
        // x2 -> x1 and x1 -> x2 
        // bezierCurve(g, 150, 100, 100, 500, 450, 100, 500, 500);
        // x2 -> x3 and x3 -> x2 move x4 and x1 close 
        bezierCurve(g, 200, 500, 450, 100, 150, 100, 400, 500);
    }
    private static void plot(Graphics g, int x, int y){
        g.fillRect(x, y, 1, 1);
    }
    private void bezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4 ){
        g.fillOval(x1, y1, 6, 6);
        g.drawString("x1", x1 + 5 , y1 + 15);
        g.fillOval(x2, y2, 6, 6);
        g.drawString("x2", x2 + 5 , y2 + 15);
        g.fillOval(x3, y3, 6, 6);
        g.drawString("x3", x3 + 5 , y3 + 15);
        g.fillOval(x4, y4, 6, 6);
        g.drawString("x4", x4 + 5 , y4 + 15);

        for (int i = 0; i <= 1000; i++) {
            double t = i / 1000.0;

            double xt = Math.pow(1 - t, 3) * x1
                      + 3 * t * Math.pow(1 - t, 2) * x2
                      + 3 * Math.pow(t, 2) * (1 - t) * x3
                      + Math.pow(t, 3) * x4;

            double yt = Math.pow(1 - t, 3) * y1
                      + 3 * t * Math.pow(1 - t, 2) * y2
                      + 3 * Math.pow(t, 2) * (1 - t) * y3
                      + Math.pow(t, 3) * y4;

            plot(g, (int) Math.round(xt), (int) Math.round(yt));
        }

    }
}
