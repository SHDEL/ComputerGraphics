// Import the tools we need
import java.awt.*;   // For JFrame and JPanel (window + panel)
import javax.swing.*;      // For Graphics (drawing shapes, colors)

// Our class (a drawing panel) extends JPanel so we can paint on it
public class SimpleFrame extends JPanel {

    // Program starts here
    public static void main(String[] args) {
        // Create a new window (JFrame) and set its title
        JFrame frame = new JFrame("My Simple Frame");

        // Create our custom panel (the paper where we draw)
        SimpleFrame panel = new SimpleFrame();

        // Put the panel inside the window
        frame.add(panel);

        // Set the window size (600 pixels wide, 600 pixels tall)
        frame.setSize(600, 600);

        // Make the program stop when you press the red X
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Make the window actually show on screen
        frame.setVisible(true);
    }

    // This method is called automatically whenever the panel needs to be drawn
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Clears the screen before drawing (important for clean redraws)

        Graphics2D g2d = (Graphics2D) g;

        // ---- Ground Shadow ----
        drawGroundShadow(g2d);

        // ---- Character Shadow ----
        g2d.setColor(new Color(0, 0, 0, 80));
        int shadowOffsetX = -10;
        int shadowOffsetY = -10;
        drawHead(g2d, shadowOffsetX, shadowOffsetY);
        drawBody(g2d, shadowOffsetX, shadowOffsetY);
        drawLeftArm(g2d, -30, shadowOffsetX, shadowOffsetY);
        drawRightArm(g2d, -40, shadowOffsetX, shadowOffsetY);
        drawLegs(g2d, shadowOffsetX, shadowOffsetY);

        // ---- Decoration ----
        // Sword
        g.setColor(Color.BLACK);
        drawSword(g, 380, 250, Math.toRadians(45), 0.4);

        // Main character
        g2d.setColor(Color.BLACK);
        drawHead(g2d, 0, 0);
        drawBody(g2d, 0, 0);
        drawLeftArm(g2d, -30, 0, 0);
        drawRightArm(g2d, -40, 0, 0);
        drawLegs(g2d, 0, 0);

        // Shield & Armor
        drawShield(g, 160, 340, Math.toRadians(45), 0.4);
        drawArmor(g, 250, 315, Math.toRadians(0), 0.4);
        // drawCape(g, 250, 315, Math.toRadians(0), 0.4);
    }

    // ---- Ground shadow (flat ellipse on the floor, angled up-left) ----
    private void drawGroundShadow(Graphics2D g2d) {
        int footX = 250; // midpoint between left(210) and right(290)
        int footY = 510 + 90; // ground contact

        int length = 220;   // total shadow length (major axis)
        int thickness = 36; // total shadow thickness (minor axis)
        int distance = 60;  // offset from feet
        int n = 120;        // polygon segments

        double angleDeg = 200.0;
        double theta = Math.toRadians(angleDeg);

        int a = length / 2;
        int b = thickness / 2;

        int cx = (int) (footX + Math.cos(theta) * distance);
        int cy = (int) (footY + Math.sin(theta) * distance);

        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; i++) {
            double t = 2 * Math.PI * i / n;
            double px = a * Math.cos(t);
            double py = b * Math.sin(t);

            double rx = px * Math.cos(theta) - py * Math.sin(theta);
            double ry = px * Math.sin(theta) + py * Math.cos(theta);

            x[i] = (int) (cx + rx);
            y[i] = (int) (cy + ry);
        }

        g2d.setColor(new Color(0, 0, 0, 70));
        g2d.fillPolygon(x, y, n);
    }

    private static Point transform(int x, int y, int cx, int cy, double angle, double scale) {
        double sx = x * scale;
        double sy = y * scale;
        double rx = sx * Math.cos(angle) - sy * Math.sin(angle);
        double ry = sx * Math.sin(angle) + sy * Math.cos(angle);
        return new Point((int) (cx + rx), (int) (cy + ry));
    }

    // ==== Drawing Methods ====

    // Cape (disabled)
    /*
    public static void drawCape(Graphics g, int cx, int cy, double angle, double scale) {
        int[] capeX = { -100, -160, -140, -120, -80, 0, 80, 120, 140, 160, 100, 0 };
        int[] capeY = { 0, 160, 140, 400, 520, 600, 520, 400, 280, 160, 0, -80 };

        Polygon cape = new Polygon();
        for (int i = 0; i < capeX.length; i++) {
            Point p = transform(capeX[i], capeY[i], cx, cy, angle, scale);
            cape.addPoint(p.x, p.y);
        }

        g.setColor(new Color(150, 0, 0));
        g.fillPolygon(cape);

        g.setColor(new Color(90, 0, 0));
        g.drawPolygon(cape);
    }
    */

    public static void drawArmor(Graphics g, int cx, int cy, double angle, double scale) {
        // Chest plate
        int[] chestX = { -90, -110, -100, -70, 0, 70, 100, 110, 90, 60, -60 };
        int[] chestY = { 0, 80, 180, 260, 300, 260, 180, 80, 0, -30, -30 };

        Polygon chest = new Polygon();
        for (int i = 0; i < chestX.length; i++) {
            Point p = transform(chestX[i], chestY[i], cx, cy, angle, scale);
            chest.addPoint(p.x, p.y);
        }

        g.setColor(new Color(160, 160, 170));
        g.fillPolygon(chest);
        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(chest);

        // Shoulder plates
        int[] leftShoulderX = { -140, -170, -150, -110 };
        int[] leftShoulderY = { -20, 40, 100, 60 };

        Polygon leftShoulder = new Polygon();
        for (int i = 0; i < leftShoulderX.length; i++) {
            Point p = transform(leftShoulderX[i], leftShoulderY[i], cx, cy, angle, scale);
            leftShoulder.addPoint(p.x, p.y);
        }

        int[] rightShoulderX = { 140, 170, 150, 110 };
        int[] rightShoulderY = { -20, 40, 100, 60 };

        Polygon rightShoulder = new Polygon();
        for (int i = 0; i < rightShoulderX.length; i++) {
            Point p = transform(rightShoulderX[i], rightShoulderY[i], cx, cy, angle, scale);
            rightShoulder.addPoint(p.x, p.y);
        }

        g.setColor(new Color(140, 140, 150));
        g.fillPolygon(leftShoulder);
        g.fillPolygon(rightShoulder);

        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(leftShoulder);
        g.drawPolygon(rightShoulder);
    }

    public static void drawShield(Graphics g, int cx, int cy, double angle, double scale) {
        int[] shieldX = { -70, -110, -70, 0, 70, 110, 80, 0 };
        int[] shieldY = { 0, 100, 140, 220, 140, 100, 0, 0 };

        Polygon shield = new Polygon();
        for (int i = 0; i < shieldX.length; i++) {
            Point p = transform(shieldX[i], shieldY[i], cx, cy, angle, scale);
            shield.addPoint(p.x, p.y);
        }

        g.setColor(new Color(100, 120, 180));
        g.fillPolygon(shield);

        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(shield);

        // Cross emblem
        int crossWidth = 20;
        int crossHeight = 60;

        int[] vertX = { -crossWidth, crossWidth, crossWidth, -crossWidth };
        int[] vertY = { 40, 40, 100, 100 };
        Polygon vertical = new Polygon();
        for (int i = 0; i < vertX.length; i++) {
            Point p = transform(vertX[i], vertY[i], cx, cy, angle, scale);
            vertical.addPoint(p.x, p.y);
        }

        int[] horizX = { -crossHeight, crossHeight, crossHeight, -crossHeight };
        int[] horizY = { 60, 60, 80, 80 };
        Polygon horizontal = new Polygon();
        for (int i = 0; i < horizX.length; i++) {
            Point p = transform(horizX[i], horizY[i], cx, cy, angle, scale);
            horizontal.addPoint(p.x, p.y);
        }

        g.setColor(new Color(220, 190, 40));
        g.fillPolygon(vertical);
        g.fillPolygon(horizontal);
    }

    public static void drawSword(Graphics g, int cx, int cy, double angle, double scale) {
        int guardY = 0;
        int shoulderY = -140;
        int tipY = -220;
        int handleBottom = 120;

        // Blade
        int[] bladeX = { -26, -24, -20, 0, 20, 24, 30 };
        int[] bladeY = { guardY, shoulderY, shoulderY - 20, tipY, shoulderY - 20, shoulderY, guardY };

        Polygon blade = new Polygon();
        for (int i = 0; i < bladeX.length; i++) {
            Point p = transform(bladeX[i], bladeY[i], cx, cy, angle, scale);
            blade.addPoint(p.x, p.y);
        }

        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(blade);

        // Fuller
        int[] fullerX = { -5, -2, 0, 2, 5 };
        int[] fullerY = { guardY, shoulderY - 20, tipY + 30, shoulderY - 20, guardY };

        Polygon fuller = new Polygon();
        for (int i = 0; i < fullerX.length; i++) {
            Point p = transform(fullerX[i], fullerY[i], cx, cy, angle, scale);
            fuller.addPoint(p.x, p.y);
        }

        g.setColor(new Color(150, 150, 150));
        g.fillPolygon(fuller);

        // Guard
        int[] guardX = { -80, -60, 60, 80, 60, -60 };
        int[] guardYArr = { guardY, guardY + 14, guardY + 14, guardY, guardY + 6, guardY + 6 };

        Polygon guard = new Polygon();
        for (int i = 0; i < guardX.length; i++) {
            Point p = transform(guardX[i], guardYArr[i], cx, cy, angle, scale);
            guard.addPoint(p.x, p.y);
        }

        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(guard);

        // Handle
        int[] handleX = { -12, 12, 12, -12 };
        int[] handleY = { guardY + 14, guardY + 14, handleBottom, handleBottom };

        Polygon handle = new Polygon();
        for (int i = 0; i < handleX.length; i++) {
            Point p = transform(handleX[i], handleY[i], cx, cy, angle, scale);
            handle.addPoint(p.x, p.y);
        }

        g.setColor(new Color(90, 60, 30));
        g.fillPolygon(handle);

        // Pommel
        int[] pommelX = { -16, 0, 16, 0 };
        int[] pommelY = { handleBottom, handleBottom + 24, handleBottom, handleBottom - 12 };

        Polygon pommel = new Polygon();
        for (int i = 0; i < pommelX.length; i++) {
            Point p = transform(pommelX[i], pommelY[i], cx, cy, angle, scale);
            pommel.addPoint(p.x, p.y);
        }

        g.setColor(Color.GRAY);
        g.fillPolygon(pommel);
    }

    private void drawLegs(Graphics g, int offsetX, int offsetY) {
        int n = 360;

        // Left leg
        int cx1 = 210 + offsetX, cy1 = 510 + offsetY;
        int a1 = 25, b1 = 90;

        int[] x1 = new int[n];
        int[] y1 = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x1[i] = (int) (cx1 + a1 * Math.cos(angle));
            y1[i] = (int) (cy1 + b1 * Math.sin(angle));
        }
        g.fillPolygon(x1, y1, n);

        // Right leg
        int cx2 = 290 + offsetX, cy2 = 510 + offsetY;
        int a2 = 25, b2 = 90;

        int[] x2 = new int[n];
        int[] y2 = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x2[i] = (int) (cx2 + a2 * Math.cos(angle));
            y2[i] = (int) (cy2 + b2 * Math.sin(angle));
        }
        g.fillPolygon(x2, y2, n);
    }

    private void drawRightArm(Graphics g, double angleDeg, int offsetX, int offsetY) {
        int cx = 330 + offsetX;
        int cy = 300 + offsetY;

        int a = 60;
        int b = 20;
        int n = 360;

        int[] x = new int[n];
        int[] y = new int[n];
        double theta = Math.toRadians(angleDeg);

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            double px = a * Math.cos(angle);
            double py = b * Math.sin(angle);

            double rx = px * Math.cos(theta) - py * Math.sin(theta);
            double ry = px * Math.sin(theta) + py * Math.cos(theta);

            x[i] = (int) (cx + rx);
            y[i] = (int) (cy + ry);
        }
        g.fillPolygon(x, y, n);
    }

    private void drawLeftArm(Graphics g, double angleDeg, int offsetX, int offsetY) {
        int cx = 160 + offsetX;
        int cy = 350 + offsetY;

        int a = 60;
        int b = 20;
        int n = 360;

        int[] x = new int[n];
        int[] y = new int[n];
        double theta = Math.toRadians(angleDeg);

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            double px = a * Math.cos(angle);
            double py = b * Math.sin(angle);

            double rx = px * Math.cos(theta) - py * Math.sin(theta);
            double ry = px * Math.sin(theta) + py * Math.cos(theta);

            x[i] = (int) (cx + rx);
            y[i] = (int) (cy + ry);
        }
        g.fillPolygon(x, y, n);
    }

    private void drawBody(Graphics g, int offsetX, int offsetY) {
        int cx = 250 + offsetX;
        int cy = 380 + offsetY;

        int a = 70;
        int b = 100;
        int n = 360;

        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x[i] = (int) (cx + a * Math.cos(angle));
            y[i] = (int) (cy + b * Math.sin(angle));
        }
        g.fillPolygon(x, y, n);
    }

    private void drawHead(Graphics g, int offsetX, int offsetY) {
        int n = 360;
        int r = 50;

        int cx = 250 + offsetX;
        int cy = 250 + offsetY;

        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x[i] = (int) (cx + r * Math.cos(angle));
            y[i] = (int) (cy + r * Math.sin(angle));
        }
        g.fillPolygon(x, y, n);
    }
}
