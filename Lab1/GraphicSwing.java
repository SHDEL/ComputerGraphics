package Lab1;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class GraphicSwing extends JPanel{
    public static void main(String[] args) {
        GraphicSwing m = new GraphicSwing();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("First Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
    }

    public void paintComponent(Graphics g){
        g.drawString("Hello", 40, 40);
        g.setColor(Color.BLUE);
        g.fillRect(130, 30, 100, 80);
        g.drawOval(30, 130, 50, 60);
        g.setColor(Color.RED);
        g.drawLine(0, 0, 200, 30);
        g.fillOval(130, 130, 50, 60);
        g.drawArc(30, 200, 40, 50, 90, 60);
        g.fillArc(30, 130, 40, 50, 180, 40);

        g.setColor(Color.BLACK);
        plot(g, 300, 300);
        
        g.setColor(Color.BLUE);
        plot(g, 310, 300);

        g.setColor(Color.GREEN);
        plot(g, 320, 300);

        g.setColor(Color.MAGENTA);
        plot(g, 330, 300);

        g.setColor(Color.ORANGE);
        plot(g, 340, 300);

        g.setColor(Color.PINK);
        plot(g, 350, 300);

        g.setColor(Color.RED);
        plot(g, 360, 300);

        g.setColor(Color.white);
        plot(g, 370, 300);

        g.setColor(Color.DARK_GRAY);
        plot(g, 380, 300);

        g.setColor(Color.GRAY);
        plot(g, 390, 300);

        for (int i = 0; i < 30; i++){
            for (int j = 0; j < 200; j++){
                if (i == j){
                    plot(g, i, j);
                }
            }
        }
        int x = 5;
        int y = 6;
        System.out.println("x + y = " + (x + y));
    }

    private void plot(Graphics g, int x, int y){
        g.fillRect(x, y, 1, 1);
    }
    
}