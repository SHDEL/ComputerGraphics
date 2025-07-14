package Lab2;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Lab1.GraphicSwing;
import java.awt.*;
public class LineMethod extends JPanel{

    public static void main(String[] args) {
        LineMethod m = new LineMethod();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
    public void paintComponent(Graphics g){
        g.setColor(Color.black);
        // naiveLineAlgo(g);

        // DDALineAlgo(g);
        
        BresenhamLineAlgo(g);
    }
    private static void naiveLineAlgo(Graphics g){
        naiveLine(g, 100, 100, 400, 200);
        naiveLine(g, 400, 300, 100, 200);
        naiveLine(g, 100, 100, 200, 400);
    }
    private static void DDALineAlgo(Graphics g){
        DDALine(g, 100, 100, 400, 200);
        DDALine(g, 400, 300, 100, 200);
        DDALine(g, 100, 100,200, 400);
    }
    private static void BresenhamLineAlgo(Graphics g){
        BresenhamLine(g, 100, 100, 400, 200);
        BresenhamLine(g, 400, 300, 100, 200);
        BresenhamLine(g, 100, 100, 200, 400);
    }
    private static void plot(Graphics g, int x, int y){
        g.fillRect(x, y, 1, 1);
    }
    public static void naiveLine(Graphics g, int x1, int y1, int x2, int y2){
        float dx = x2 - x1;
        float dy = y2 - y1;
        float b = y1 - (dy / dx) * x1;

        for (int x = x1; x < x2 ; x++){
            int y = (int) (Math.round((dy / dx) * x) + b);
            // method plot
            plot(g, x, y);
            
        }
    }
    public static void DDALine(Graphics g, int x1, int y1, int x2, int y2){
        float dx = x2 - x1;
        float dy = y2 - y1;
        int y = y1;
        float m = dy / dx;

        if (m >= 0  && m <= 1){
            for (int i = x1; i <= x2; i++){
                y = (int) (y + m);
                // plot method
                plot(g, i, Math.round(y));
                

            }
        }
        else if (m <= -1){
            for (int x = x2; x < x1; x++){
                y = (int) (y + m);
                // plot method
                plot(g, x, Math.round(y));
            }
        }
        else if (m > 1){
            int x;
            for (y = y1; y < y2; y++){
                x = (int) (y + 1 / m);
                // plot method
                plot(g, Math.round(x), y);
            }
        }
        else{
            int x;
            for (y = y2; y < y1; y++){
                x = (int) (y + 1 / m);
                //plot method
                plot(g, Math.round(x), y);
            }
        }
    
    }
    public static void BresenhamLine(Graphics g, int x1, int y1, int x2, int y2){
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        boolean isSwap = false;

        // swap dx and dy if slope > 1
        if (dy > dx) {
            int temp = dx;
            dx = dy;
            dy = temp;
            isSwap = true;
        }

        int D = 2 * dy - dx;
        int x = x1;
        int y = y1;

        for (int i = 1; i <= dx; i++) {
            // plot(x, y);
            plot(g, x, y);

            if (D >= 0) {
                if (isSwap) {
                    x += sx;
                } else {
                    y += sy;
                }
                D -= 2 * dx;
            }

            if (isSwap) {
                y += sy;
            } else {
                x += sx;
            }

            D += 2 * dy;
        }
    }
    
}