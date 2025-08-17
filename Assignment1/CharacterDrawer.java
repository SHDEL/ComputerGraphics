import java.awt.*;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class CharacterDrawer extends JPanel{
    public static void main(String[] args) {
        CharacterDrawer m = new CharacterDrawer();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }    

    public void paintComponent(Graphics g){
        drawCharacter((Graphics2D)g, 250, 250, 1.0);
    }
    // Main method to draw whole character
    public static void drawCharacter(Graphics2D g2d, int offsetX, int offsetY, double scale) {
        // Ground Shadow
        // drawGroundShadow(g2d, offsetX, offsetY);


        // // ---- Character Shadow ----
        // g2d.setColor(new Color(0, 0, 0, 80));
        // int shadowOffsetX = -10;
        // int shadowOffsetY = -10;
        // drawHead(g2d, offsetX + shadowOffsetX, offsetY + shadowOffsetY);
        // drawBody(g2d, offsetX + shadowOffsetX, offsetY + shadowOffsetY);
        // drawLeftArm(g2d, -30, offsetX + shadowOffsetX, offsetY + shadowOffsetY);
        // drawRightArm(g2d, -40, offsetX + shadowOffsetX, offsetY + shadowOffsetY);
        // drawLegs(g2d, offsetX + shadowOffsetX, offsetY + shadowOffsetY);

        // ---- Main body ----
        g2d.setColor(Color.BLACK);
        drawHead(g2d, offsetX, offsetY - (int)(130 * scale), scale);
        drawBody(g2d, offsetX, offsetY);
        drawLeftArm(g2d, -30, offsetX, offsetY - 30);
        
        drawRightArm(g2d, -40, offsetX, offsetY - 20);
        drawLegs(g2d, offsetX, offsetY);

        // ---- Equipment ----
        drawSword(g2d, offsetX + 130, offsetY - 60, Math.toRadians(45), 0.4);
        drawShield(g2d, offsetX - 90, offsetY + 30, Math.toRadians(45), 0.4);
        // drawArmor(g2d, offsetX, offsetY - 20, 0, 0.4);
    }

    // === Drawing methods (copied & adapted from your code) ===

    private static Point transform(int x, int y, int cx, int cy, double angle, double scale) {
        double sx = x * scale;
        double sy = y * scale;
        double rx = sx * Math.cos(angle) - sy * Math.sin(angle);
        double ry = sx * Math.sin(angle) + sy * Math.cos(angle);
        return new Point((int) (cx + rx), (int) (cy + ry));
    }

    private static void drawGroundShadow(Graphics2D g2d, int offsetX, int offsetY) {
        int footX = 250 + offsetX;
        int footY = 510 + 90 + offsetY;

        int length    = 220;
        int thickness = 36;
        int distance  = 60;
        int n         = 120;

        double angleDeg = 200.0;
        double theta    = Math.toRadians(angleDeg);

        int a = length / 2;
        int b = thickness / 2;

        int cx = (int)(footX + Math.cos(theta) * distance);
        int cy = (int)(footY + Math.sin(theta) * distance);

        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; i++) {
            double t  = 2 * Math.PI * i / n;
            double px = a * Math.cos(t);
            double py = b * Math.sin(t);

            double rx =  px * Math.cos(theta) - py * Math.sin(theta);
            double ry =  px * Math.sin(theta) + py * Math.cos(theta);

            x[i] = (int)(cx + rx);
            y[i] = (int)(cy + ry);
        }

        g2d.setColor(new Color(0, 0, 0, 70));
        g2d.fillPolygon(x, y, n);
    }

    public static void drawHead(Graphics g, int cx, int cy, double scale) {
        int n = 360;
        int[] x = new int[n];
        int[] y = new int[n];
        int r = (int)(50 * scale);

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x[i] = (int)(cx + r * Math.cos(angle));
            y[i] = (int)(cy + r * Math.sin(angle));
        }
        g.fillPolygon(x, y, n);
    }

    public static void drawBody(Graphics g, int cx, int cy) {
        int a = (int)(70);
        int b = (int)(100);
        int n = 360;
        int[] x = new int[n];
        int[] y = new int[n];

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x[i] = (int)(cx + a * Math.cos(angle));
            y[i] = (int)(cy + b * Math.sin(angle));
        }
        g.fillPolygon(x, y, n);
    }

    public static void drawLegs(Graphics g, int cx, int cy) {
        int n = 360;
        // Left leg
        int dx1 = -40, dy1 = 120;
        int a1 = 25, b1 = 90;
        int cx1 = cx + (int)(dx1);
        int cy1 = cy + (int)(dy1);
        int[] x1 = new int[n];
        int[] y1 = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x1[i] = (int)(cx1 + a1 * Math.cos(angle));
            y1[i] = (int)(cy1 + b1 * Math.sin(angle));
        }
        g.fillPolygon(x1, y1, n);

        // Right leg
        int dx2 = 40, dy2 = 120;
        int a2 = 25, b2 = 90;
        int cx2 = cx + (int)(dx2);
        int cy2 = cy + (int)(dy2);
        int[] x2 = new int[n];
        int[] y2 = new int[n];
        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            x2[i] = (int)(cx2 + a2 * Math.cos(angle));
            y2[i] = (int)(cy2 + b2 * Math.sin(angle));
        }
        g.fillPolygon(x2, y2, n);
    }

    public static void drawLeftArm(Graphics g, double angleDeg, int cx, int cy) {
        int dx = -90, dy = 50; // ระยะห่างจากจุดกลาง
        int a = 60, b = 20;
        int n = 360;
        int cX = cx + (int)(dx);
        int cY = cy + (int)(dy);

        int[] x = new int[n];
        int[] y = new int[n];

        double theta = Math.toRadians(angleDeg);

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            double px = a * Math.cos(angle);
            double py = b * Math.sin(angle);
            double rx = px * Math.cos(theta) - py * Math.sin(theta);
            double ry = px * Math.sin(theta) + py * Math.cos(theta);
            x[i] = (int)(cX + rx);
            y[i] = (int)(cY + ry);
        }
        g.fillPolygon(x, y, n);
    }

    public static void drawRightArm(Graphics g, double angleDeg, int cx, int cy) {
        int dx = 90, dy = 0; // ระยะห่างจากจุดกลาง
        int a = 60, b = 20;
        int n = 360;
        int cX = cx + (int)(dx);
        int cY = cy + (int)(dy);

        int[] x = new int[n];
        int[] y = new int[n];
        double theta = Math.toRadians(angleDeg);

        for (int i = 0; i < n; i++) {
            double angle = 2 * Math.PI * i / n;
            double px = a * Math.cos(angle);
            double py = b * Math.sin(angle);
            double rx = px * Math.cos(theta) - py * Math.sin(theta);
            double ry = px * Math.sin(theta) + py * Math.cos(theta);
            x[i] = (int)(cX + rx);
            y[i] = (int)(cY + ry);
        }
        g.fillPolygon(x, y, n);
    }

    // === Equipment (Sword, Shield, Armor) ===
    public static void drawSword(Graphics g, int cx, int cy, double angle, double scale) {
        int guardY = 0;
        int shoulderY = -140;
        int tipY = -220;
        int handleBottom = 120;

        int[] bladeX = { -26, -24, -20, 0, 20, 24, 30 };
        int[] bladeY = { guardY, shoulderY, shoulderY - 20, tipY, shoulderY - 20, shoulderY, guardY };
        Polygon blade = new Polygon();
        for (int i = 0; i < bladeX.length; i++) {
            Point p = transform(bladeX[i], bladeY[i], cx, cy, angle, scale);
            blade.addPoint(p.x, p.y);
        }
        g.setColor(Color.LIGHT_GRAY);
        g.fillPolygon(blade);

        int[] fullerX = { -5, -2, 0, 2, 5 };
        int[] fullerY = { guardY, shoulderY - 20, tipY + 30, shoulderY - 20, guardY };
        Polygon fuller = new Polygon();
        for (int i = 0; i < fullerX.length; i++) {
            Point p = transform(fullerX[i], fullerY[i], cx, cy, angle, scale);
            fuller.addPoint(p.x, p.y);
        }
        g.setColor(new Color(150, 150, 150));
        g.fillPolygon(fuller);

        int[] guardX = { -80, -60, 60, 80, 60, -60 };
        int[] guardYArr = { guardY, guardY + 14, guardY + 14, guardY, guardY + 6, guardY + 6 };
        Polygon guard = new Polygon();
        for (int i = 0; i < guardX.length; i++) {
            Point p = transform(guardX[i], guardYArr[i], cx, cy, angle, scale);
            guard.addPoint(p.x, p.y);
        }
        g.setColor(Color.DARK_GRAY);
        g.fillPolygon(guard);

        int[] handleX = { -12, 12, 12, -12 };
        int[] handleY = { guardY + 14, guardY + 14, handleBottom, handleBottom };
        Polygon handle = new Polygon();
        for (int i = 0; i < handleX.length; i++) {
            Point p = transform(handleX[i], handleY[i], cx, cy, angle, scale);
            handle.addPoint(p.x, p.y);
        }
        g.setColor(new Color(90, 60, 30));
        g.fillPolygon(handle);

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

    public static void drawShield(Graphics g, int cx, int cy, double angle, double scale) {
        int topY = 0;
        int midY = 100;
        int bottomY = 220;

        int[] shieldX = { -70, -110, -70, 0, 70, 110, 80, 0 };
        int[] shieldY = { topY, midY, midY + 40, bottomY, midY + 40, midY, topY, topY };
        Polygon shield = new Polygon();
        for (int i = 0; i < shieldX.length; i++) {
            Point p = transform(shieldX[i], shieldY[i], cx, cy, angle, scale);
            shield.addPoint(p.x, p.y);
        }

        g.setColor(new Color(100, 120, 180));
        g.fillPolygon(shield);
        g.setColor(Color.DARK_GRAY);
        g.drawPolygon(shield);

        int crossWidth = 20;
        int crossHeight = 60;

        int[] vertX = { -crossWidth, crossWidth, crossWidth, -crossWidth };
        int[] vertY = { 40, 40, 40 + crossHeight, 40 + crossHeight };
        Polygon vertical = new Polygon();
        for (int i = 0; i < vertX.length; i++) {
            Point p = transform(vertX[i], vertY[i], cx, cy, angle, scale);
            vertical.addPoint(p.x, p.y);
        }

        int[] horizX = { -crossHeight, crossHeight, crossHeight, -crossHeight };
        int[] horizY = { 60, 60, 60 + crossWidth, 60 + crossWidth };
        Polygon horizontal = new Polygon();
        for (int i = 0; i < horizX.length; i++) {
            Point p = transform(horizX[i], horizY[i], cx, cy, angle, scale);
            horizontal.addPoint(p.x, p.y);
        }

        g.setColor(new Color(220, 190, 40));
        g.fillPolygon(vertical);
        g.fillPolygon(horizontal);
    }

    public static void drawArmor(Graphics g, int cx, int cy, double angle, double scale) {
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
}
