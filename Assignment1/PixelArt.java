import java.awt.*;

public class PixelArt {

    public static void drawScene(Graphics g, int groundY, int cameraY) {
        // === BACKGROUND MOUNTAINS ===
        g.setColor(new Color(60, 100, 60));
        int[] xMountains = {0, 150, 300, 450, 600};
        int[] yMountains = {
            groundY - cameraY, 
            groundY - 250 - cameraY, 
            groundY - 150 - cameraY, 
            groundY - 300 - cameraY, 
            groundY - cameraY
        };
        g.fillPolygon(xMountains, yMountains, xMountains.length);

        // === GRASS FIELD ===
        g.setColor(new Color(124, 252, 0)); // bright green
        g.fillRect(0, groundY - cameraY, 600, 150);

        // little highlights
        g.setColor(new Color(200, 255, 120));
        g.fillRect(100, groundY - cameraY + 50, 50, 10);
        g.fillRect(300, groundY - cameraY + 80, 60, 12);

        // === CASTLE ===
        drawCastle(g, 350, groundY - cameraY - 180);
    }

    private static void drawCastle(Graphics g, int baseX, int baseY) {
        // main tower
        g.setColor(new Color(170, 170, 200));
        g.fillRect(baseX, baseY, 60, 180);

        // windows
        g.setColor(Color.BLACK);
        g.fillRect(baseX + 20, baseY + 30, 20, 30);
        g.fillRect(baseX + 20, baseY + 80, 20, 30);

        // side building
        g.setColor(new Color(150, 150, 190));
        g.fillRect(baseX - 80, baseY + 60, 80, 120);

        // roof
        g.setColor(new Color(80, 80, 120));
        int[] xRoof = {baseX, baseX + 30, baseX + 60};
        int[] yRoof = {baseY, baseY - 40, baseY};
        g.fillPolygon(xRoof, yRoof, 3);

        // flag
        g.setColor(Color.BLACK);
        g.drawLine(baseX + 30, baseY - 40, baseX + 30, baseY - 70);
        g.setColor(Color.BLUE);
        g.fillRect(baseX + 30, baseY - 70, 20, 10);
    }
}
