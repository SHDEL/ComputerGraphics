import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class Lab4_66050019 extends JPanel{
    public static void main(String[] args) {
        Lab4_66050019 m = new Lab4_66050019();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        
    }
    public void paintComponent(Graphics g){
        
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        g2.setColor(Color.RED);

        // midpointCircle(g2, 200, 200, 100);
        midpointEllipse(g2, 200, 200, 120, 80);

    }
    public void midpointCircle(Graphics2D g, int xc, int yc, int r) {
        int x = 0;
        int y = r;
        int d = 1 - r;

        while (x <= y) {
            plot8CirclePoints(g, xc, yc, x, y);
            
            x++;
            if (d < 0) {
                d += 2 * x + 1;
            } else {
                y--;
                d += 2 * (x - y) + 1;
            }
        }
    }
    private void plot8CirclePoints(Graphics2D g, int xc, int yc, int x, int y) {
        g.drawLine(xc + x, yc + y, xc + x, yc + y);
        g.drawLine(xc - x, yc + y, xc - x, yc + y);
        g.drawLine(xc + x, yc - y, xc + x, yc - y);
        g.drawLine(xc - x, yc - y, xc - x, yc - y);
        g.drawLine(xc + y, yc + x, xc + y, yc + x);
        g.drawLine(xc - y, yc + x, xc - y, yc + x);
        g.drawLine(xc + y, yc - x, xc + y, yc - x);
        g.drawLine(xc - y, yc - x, xc - y, yc - x);

    }
    public void midpointEllipse(Graphics2D g, int xc, int yc, int a, int b) {
        int a2 = a * a;
        int b2 = b * b;
        int twoA2 = 2 * a2;
        int twoB2 = 2 * b2;

        // REGION 1
        int x = 0;
        int y = b;
        int Dx = 0;
        int Dy = twoA2 * y;
        int d1 = Math.round(b2 - a2 * b + 0.25f * a2);

        while (Dx <= Dy) {
            plot4EllipsePoints(g, xc, yc, x, y);

            x++;
            Dx += twoB2;
            d1 += Dx + b2;
            if (d1 >= 0) {
                y--;
                Dy -= twoA2;
                d1 -= Dy;
            }
        }

        // REGION 2
        x = a;
        y = 0;
        Dx = twoB2 * x;
        Dy = 0;
        int d2 = Math.round(a2 - b2 * a + 0.25f * b2);

        while (Dx >= Dy) {
            plot4EllipsePoints(g, xc, yc, x, y);

            y++;
            Dy += twoA2;
            d2 += Dy + a2;
            if (d2 >= 0) {
                x--;
                Dx -= twoB2;
                d2 -= Dx;
            }
        }
    }
    private void plot4EllipsePoints(Graphics2D g, int xc, int yc, int x, int y) {
        g.drawLine(xc + x, yc + y, xc + x, yc + y); // ล่างขวา
        g.drawLine(xc - x, yc + y, xc - x, yc + y); // ล่างซ้าย
        g.drawLine(xc + x, yc - y, xc + x, yc - y); // บนขวา
        g.drawLine(xc - x, yc - y, xc - x, yc - y); // บนซ้าย
    }


}
