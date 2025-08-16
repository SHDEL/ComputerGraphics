import javax.swing.*;
import java.awt.*;

public class aaa extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Smooth edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelW = getWidth();
        int panelH = getHeight();

        // Background (black room)
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, panelW, panelH);

        // Sizes
        int headSize = 180;
        int bodyWidth = headSize + 200;
        int bodyHeight = 300;

        // Position so bottom-right corner of body touches frame
        int bodyX = panelW - bodyWidth;
        int bodyY = panelH - (bodyHeight / 2);

        // Head position (center above body)
        int headX = bodyX + (bodyWidth - headSize) / 2;
        int headY = bodyY - headSize + 40;

        // --- Shadow (soft gray, offset) ---
        g2d.setColor(new Color(50, 50, 50, 180));
        g2d.fillOval(headX + 15, headY + 15, headSize, headSize);
        g2d.fillArc(bodyX + 15, bodyY + 15, bodyWidth, bodyHeight, 0, 180);

        
        // --- Person (dark gray) ---
        g2d.setColor(Color.DARK_GRAY);
        g2d.fillOval(headX, headY, headSize, headSize);
        g2d.fillArc(bodyX, bodyY, bodyWidth, bodyHeight, 0, 180);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Person");
        aaa panel = new aaa();

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
