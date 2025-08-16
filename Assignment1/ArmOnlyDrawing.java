import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

public class ArmOnlyDrawing extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int panelW = getWidth();
        int panelH = getHeight();

        // Background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, panelW, panelH);

        // Arm properties
        int upperArmLength = 100;
        int forearmLength = 80;
        int armWidth = 30;
        int arcRadius = 15;

        // Shoulder position (back-left torso)
        int shoulderX = panelW/2 - 50;  // adjust to match your scene
        int shoulderY = panelH/2 + 50;

        // Upper arm: angled toward top-left (from back)
        double upperArmAngle = -Math.PI / 3;  // -60 degrees
        // Forearm: angled forward-right (on table)
        double forearmAngle = Math.PI / 6;    // +30 degrees

        // Draw arm
        AffineTransform old = g2d.getTransform();
        g2d.translate(shoulderX, shoulderY);
        g2d.rotate(upperArmAngle);
        g2d.setColor(Color.BLUE);
        g2d.fillRoundRect(-armWidth/2, 0, armWidth, upperArmLength, arcRadius, arcRadius);

        g2d.translate(0, upperArmLength);
        g2d.rotate(forearmAngle);
        g2d.fillRoundRect(-armWidth/2, 0, armWidth, forearmLength, arcRadius, arcRadius);

        g2d.setTransform(old);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Arm Only");
        ArmOnlyDrawing panel = new ArmOnlyDrawing();

        frame.add(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
