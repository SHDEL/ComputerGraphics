import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Queue;
import java.util.Random;
import java.util.List;

public class Assignment1_66050019_66050066 extends JPanel implements Runnable{

    private double angle = 0; // มุมหมุนของ Portal
    private boolean isNoise = false; // แสดง Noise บนจอ
    private boolean isPortalShow = false; // แสดง Portal
    private double portalSize = 2; // ขนาดของเส้น Portal
    private double portalScale = 1; // ขนาดของ Portal ทั้งหมด
    private double personScale = 2; // ขนาดของ Person คนแรก
    private double fadeOpacity = 0.0; // Fade ดำ
    private boolean showFade = false; // แสดง Fade ดำ
    private List<Cloud> clouds; 
    private int cameraY = 0;
    private int cameraSpeed = 1;
    private int skyHeight = 800;
    private int groundHeight = 150;
    private boolean showNewWorld = false;

    public static void main(String[] args) {
        Assignment1_66050019_66050066 m = new Assignment1_66050019_66050066();

        JFrame f = new JFrame();
        f.add(m);
        f.setTitle("Swing");
        f.setSize(600,600);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);

        (new Thread(m)).start();
    }
    public Assignment1_66050019_66050066(){
        clouds = new ArrayList<>();
        // Upper clouds
        clouds.add(new Cloud(100, 100, 120, 60, 1));
        clouds.add(new Cloud(250, 200, 150, 80, 1));
        clouds.add(new Cloud(400, 50, 100, 50, 1));
        clouds.add(new Cloud(200, 150, 80, 40, 1));
        clouds.add(new Cloud(300, 70, 130, 60, 1));

        // Lower clouds near ground (adjustable)
        clouds.add(new Cloud(150, 400, 120, 60, 1));
        clouds.add(new Cloud(300, 450, 140, 70, 1));
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g); // ล้างพื้นหลัง
        
        BufferedImage buffer = new BufferedImage(601, 601, BufferedImage.TYPE_INT_ARGB);
        
        g.setColor(new Color(18, 23, 48));
        g.fillRect(0, 0, 600, 600);

        // วาดโต๊ะและจอ
        drawTable(g);
        
        drawMonitor(g);
        
        
        if (isNoise) {
            drawStaticNoise(g);
        }

        if (isPortalShow) {
            drawPortal(g, 300, 300, portalSize, portalScale, Color.blue);
            drawPortal(g, 300, 300, portalSize, portalScale, new Color(65, 41, 186));
            drawPortal(g, 300, 300, portalSize, portalScale, Color.black);
        }

        drawPersonSimple((Graphics2D) g, buffer, 450, 600);

        if (showFade) {
            drawFadeEffect(g);
        }
        

        if (showNewWorld){
            // วาดท้องฟ้า
            g.setColor(new Color(135, 206, 235));
            g.fillRect(0, -cameraY, getWidth(), skyHeight);

            // วาดเมฆ
            Graphics2D g1 = (Graphics2D)g.create();
            g1.setColor(Color.WHITE);
            for (Cloud cloud : clouds) {
                g1.translate(cloud.x, 0);
                midpointEllipseFill(g, (int)Math.round(cloud.x) + cloud.width/2, cloud.y - cameraY + cloud.height/2, cloud.width/2, cloud.height/2, Color.WHITE);
            }

            // วาดพื้นดิน
            g.setColor(new Color(34, 139, 34));
            int groundY = skyHeight;
            g.fillRect(0, groundY - cameraY, getWidth(), groundHeight);

            // วาดฉาก (Pixel Art)
            drawScene(g, groundY, cameraY);
            drawPerson(g);

        }
        
        

    }
    public void updateScene(double elapsedTime) {
        // Move clouds horizontally and cycle
        for (Cloud cloud : clouds) {
            cloud.x += 100 * elapsedTime / 1000.0;
                if (cloud.x > 600){
                    cloud.x = -cloud.width;

                }
        }

        // Move camera down until ground reaches bottom
        int totalSceneHeight = skyHeight + groundHeight;
        int panelHeight = getHeight();
        int maxCameraY = totalSceneHeight - panelHeight;

        if (cameraY < maxCameraY) {
            cameraY += cameraSpeed;
            if (cameraY > maxCameraY)
                cameraY = maxCameraY;
        }
    }
    @Override
    public void run() {

        double lastTime = System.currentTimeMillis();
        double currentTime, elapsedTime;
        double startTime = System.currentTimeMillis();
        while (true) {
            currentTime = System.currentTimeMillis();
            elapsedTime = (currentTime - lastTime);
            lastTime = currentTime;
            double time = (currentTime - startTime) / 1000.0;

            if (time >= 2.0){
                isNoise = true;
            }

            if (time >= 4.0 && portalSize < 25){
                isPortalShow = true;
                portalSize += 3 * elapsedTime / 1000;
                portalScale += 3 * elapsedTime / 1000;
                if (portalSize >= 25){
                    portalSize = 25;
                }
            }
            if (time >= 8.0 && fadeOpacity < 1.0){
                showFade = true;
                fadeOpacity += 1 * elapsedTime / 1000;
                if (fadeOpacity > 1.0) {
                    fadeOpacity = 1.0;
                }
            }

            if (fadeOpacity == 1.0){
                showNewWorld = true;
                updateScene(elapsedTime);
            }
            
            angle += 0.01; // ปรับมุมเพื่อหมุน
            try {
                Thread.sleep(1);
                
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            repaint();
            
        }
    }
    
    private void drawPortal(Graphics g, int cx, int cy, double size, double scale, Color color) {
        Graphics2D g3 = (Graphics2D) g;
        g3.setColor(color);
        g3.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        int layers = 50; // จำนวนเส้นโค้ง
        for (int i = 0; i < layers; i++) {
            
            double offsetAngle = angle + (i * Math.PI / 10); // มุมแต่ละเส้น
            double r = size - i * scale; // รัศมีแต่ละชั้น

            // จุดเริ่มและจุดสิ้นสุดของ bezier
            int x1 = (int) (cx + r * Math.cos(offsetAngle));
            int y1 = (int) (cy + r * Math.sin(offsetAngle));

            int x4 = (int) (cx + r * Math.cos(offsetAngle + Math.PI));
            int y4 = (int) (cy + r * Math.sin(offsetAngle + Math.PI));

            // จุด control ให้เยื้องออกไป เพื่อให้โค้ง
            int x2 = (int) (cx + (r + 30) * Math.cos(offsetAngle + Math.PI / 2));
            int y2 = (int) (cy + (r + 30) * Math.sin(offsetAngle + Math.PI / 2));

            int x3 = (int) (cx + (r + 30) * Math.cos(offsetAngle - Math.PI / 2));
            int y3 = (int) (cy + (r + 30) * Math.sin(offsetAngle - Math.PI / 2));

            // quadraticBezierCurve(g3, x1, y1, x2, y2, x4, y4);
            bezierCurve(g3, x1, y1, x2, y2, x3, y3, x4, y4, (int)size);
        }
    }

    private void drawFadeEffect(Graphics g) {
        fillRectCustom(g, 0, 0, getWidth(), getHeight(), new Color(0, 0, 0, (int) (fadeOpacity * 255)));
    }
    private void drawTable(Graphics g){
        fillRectCustom(g, 0, 380, 600, 220, new Color(145, 114, 0));
        
    }
    private void drawMonitor(Graphics g){
        Color monitor = new Color(47, 48, 48);
        
        // monitor stand
        fillRectCustom(g,290, 320, 20, 70, monitor);
        fillRectCustom(g, 250, 390, 100, 10, monitor);

        // monitor stroke
        fillRectCustom(g, 180, 200, 250, 150, monitor);

        // monitor panel
        fillRectCustom(g, 190, 210, 230, 130, Color.white);

        // พิกัดจอ
        int screenX = 190;
        int screenY = 210;
        int screenW = 300;
        int screenH = 300;

        
        // ===== วาดแสงขาวออกจากจอ =====
        drawGlow(g, screenX, screenY, screenW, screenH);

    }
    private void drawGlow(Graphics g, int x, int y, int w, int h) {
        Graphics2D g1 = (Graphics2D) g.create();
        // กำหนดศูนย์กลาง glow
        float centerX = x + w / 2f;
        float centerY = y + h / 2f;
        float radius = Math.max(w, h) * 1.5f; // รัศมีกว้างกว่า
        // ไล่สีจากขาวโปร่งใส -> โปร่งใสหมด
        RadialGradientPaint paint = new RadialGradientPaint(
            centerX, centerY, radius,
            new float[]{0f, 1f},
            new Color[]{new Color(255, 255, 255, 100), new Color(255, 255, 255, 0)}
        );
        g1.setPaint(paint);
        int[] xs = {x - w, x + 2*w, x + 2*w, x - w};
        int[] ys = {y - h, y - h, y + 2*h, y + 2*h};
        g1.fillPolygon(xs, ys, 4);
    }

    private void drawStaticNoise(Graphics g) {
        int x = 190;
        int y = 210;
        int w = 230;
        int h = 130;
        Random rand = new Random();
        for (int i = 0; i < w * h / 4; i++) { // ยิ่งจำนวนมาก noise ยิ่งละเอียด
            int px = x + rand.nextInt(w);
            int py = y + rand.nextInt(h);
            int gray = rand.nextInt(256); // ค่าความสว่าง
            g.setColor(new Color(gray, gray, gray));
            plot(g, px, py, 1);
        }
    }

    private void drawPersonSimple(Graphics2D g2, BufferedImage buffer, int centerX, int baseY) {
        Graphics2D g3 = buffer.createGraphics();

        int headRadius = (int) (50 * personScale);
        int bodyWidth = (int) (60 * personScale);
        int bodyHeight = (int) (100 * personScale);
        

        // --- วาดหัวด้วย midpointCircleFillBuffer (เต็มสี) ---
        midpointCircleFillBuffer(buffer, centerX, baseY - bodyHeight - headRadius, headRadius, Color.DARK_GRAY);

        // --- วาดลำตัว (Polygon) ---
        int[] xs = {centerX - bodyWidth / 2, centerX + bodyWidth / 2, centerX + bodyWidth / 2, centerX - bodyWidth / 2};
        int[] ys = {baseY - bodyHeight, baseY - bodyHeight, baseY, baseY};

        // ใช้สี DARK_GRAY โดยตรง
        g3.setColor(Color.DARK_GRAY);
        g3.fillPolygon(xs, ys, xs.length);

        // วาดกลับไป panel
        g2.drawImage(buffer, 0, 0, null);
    }



    private void fillRectCustom(Graphics g, int x, int y, int width, int height, Color color) {
        g.setColor(color);
        for (int j = 0; j < height; j++) {
            int yPos = y + j;
            // วาดเส้นแนวนอนจาก x -> x+width-1
            BresenhamLine(g, x, yPos, x + width - 1, yPos);
        }
    }
    private void drawScene(Graphics g, int groundY, int cameraY) {
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
        fillRectCustom(g,0, groundY - cameraY, 600, 150, new Color(124, 252, 0));

        // little highlights
        g.setColor(new Color(200, 255, 120));
        fillRectCustom(g, 100, groundY - cameraY + 50, 50, 10, new Color(200, 255, 120));
        fillRectCustom(g, 300, groundY - cameraY + 80, 60, 12,  new Color(200, 255, 120));

        // === CASTLE ===
        drawCastle(g, 350, groundY - cameraY - 180);
    }

    private void drawCastle(Graphics g, int baseX, int baseY) {
        // main tower
        g.setColor(new Color(170, 170, 200));
        fillRectCustom(g, baseX, baseY, 60, 180, new Color(170, 170, 200));

        // windows
        fillRectCustom(g, baseX + 20, baseY + 30, 20, 30, Color.BLACK);
        fillRectCustom(g, baseX + 20, baseY + 80, 20, 30, Color.BLACK);

        // side building
        fillRectCustom(g, baseX - 80, baseY + 60, 80, 120,new Color(150, 150, 190));

        // roof
        g.setColor(new Color(80, 80, 120));
        int[] xRoof = {baseX, baseX + 30, baseX + 60};
        int[] yRoof = {baseY, baseY - 40, baseY};
        g.fillPolygon(xRoof, yRoof, 3);

        // flag
        g.setColor(Color.BLACK);
        g.drawLine(baseX + 30, baseY - 40, baseX + 30, baseY - 70);
        g.setColor(Color.BLUE);
        fillRectCustom(g, baseX + 30, baseY - 70, 20, 10, Color.BLUE);
    }
    private void drawPerson(Graphics g){
        Graphics2D g2d = (Graphics2D) g;

        // ---- Ground Shadow ----
        // drawGroundShadow(g2d);

        // ---- Character Shadow ----
        // g2d.setColor(new Color(0, 0, 0, 80));
        // int shadowOffsetX = -10;
        // int shadowOffsetY = -10;
        // drawHead(g2d, shadowOffsetX, shadowOffsetY);
        // drawBody(g2d, shadowOffsetX, shadowOffsetY);
        // drawLeftArm(g2d, -30, shadowOffsetX, shadowOffsetY);
        // drawRightArm(g2d, -40, shadowOffsetX, shadowOffsetY);
        // drawLegs(g2d, shadowOffsetX, shadowOffsetY);

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
    }

    public static void BresenhamLine(Graphics g, int x1, int y1, int x2, int y2){
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        boolean isSwap = false;

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
            plot(g, x, y, 1);

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
    private static void plot(Graphics g, int x, int y, int size){
        g.fillRect(x, y, size, size);
    }
    private void bezierCurve(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size ){
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

            plot(g, (int) Math.round(xt), (int) Math.round(yt), size);
        }

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
    private void midpointCircleFillBuffer(BufferedImage buffer, int xc, int yc, int r, Color color) {
        int c = color.getRGB();
        for (int y = -r; y <= r; y++) {
            for (int x = -r; x <= r; x++) {
                if (x*x + y*y <= r*r) { // ถ้าอยู่ในวงกลม
                    int px = xc + x;
                    int py = yc + y;
                    if(px >=0 && px < buffer.getWidth() && py >=0 && py < buffer.getHeight())
                        buffer.setRGB(px, py, c);
                }
            }
        }
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
            // plot4EllipsePoints(g, xc, yc, x, y);
            BresenhamLine(g, xc - x, yc + y, xc + x, yc + y);
            BresenhamLine(g, xc - x, yc - y, xc + x, yc - y);

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
            // plot4EllipsePoints(g, xc, yc, x, y);
            BresenhamLine(g, xc - x, yc + y, xc + x, yc + y);
            BresenhamLine(g, xc - x, yc - y, xc + x, yc - y);

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
    public void midpointEllipseFill(Graphics g, int xc, int yc, int a, int b, Color color) {
        g.setColor(color);
        for (int y = -b; y <= b; y++) {
            // คำนวณ x ที่ขอบซ้าย/ขวา
            double x = a * Math.sqrt(1 - (y * y) / (double)(b * b));
            int xLeft = (int)Math.round(xc - x);
            int xRight = (int)Math.round(xc + x);
            BresenhamLine(g, xLeft, yc + y, xRight, yc + y);
        }
    }
    public BufferedImage FloodFill(BufferedImage m, int x, int y, Color targetColour, Color replacementColor) {
        Graphics2D g3 = m.createGraphics();
        Queue<Point> q = new LinkedList<>();

        if (m.getRGB(x, y) == targetColour.getRGB()) {
            g3.setColor(replacementColor);
            plot(g3, x, y, 1);
            q.add(new Point(x, y));
        }

        while (!q.isEmpty()) {
            Point p = q.poll();

            // s
            if (p.y < 600 && m.getRGB(p.x, p.y + 1) == targetColour.getRGB()) {
                g3.setColor(replacementColor);
                plot(g3, p.x, p.y + 1, 1);
                q.add(new Point(p.x, p.y + 1));
            }
            // n
            if (p.y > 0 && m.getRGB(p.x, p.y - 1) == targetColour.getRGB()) {
                g3.setColor(replacementColor);
                plot(g3, p.x, p.y - 1, 1);
                q.add(new Point(p.x, p.y - 1));
            }
            // e
            if (p.x < 600 && m.getRGB(p.x + 1, p.y) == targetColour.getRGB()) {
                g3.setColor(replacementColor);
                plot(g3, p.x + 1, p.y, 1);
                q.add(new Point(p.x + 1, p.y));
            }
            // w
            if (p.x > 0 && m.getRGB(p.x - 1, p.y) == targetColour.getRGB()) {
                g3.setColor(replacementColor);
                plot(g3, p.x - 1, p.y, 1);
                q.add(new Point(p.x - 1, p.y));
            }
        }
        return m;
    }
    public void drawArmor(Graphics g, int cx, int cy, double angle, double scale) {
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

    public void drawShield(Graphics g, int cx, int cy, double angle, double scale) {
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

    public void drawSword(Graphics g, int cx, int cy, double angle, double scale) {
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
    private static Point transform(int x, int y, int cx, int cy, double angle, double scale) {
        double sx = x * scale;
        double sy = y * scale;
        double rx = sx * Math.cos(angle) - sy * Math.sin(angle);
        double ry = sx * Math.sin(angle) + sy * Math.cos(angle);
        return new Point((int) (cx + rx), (int) (cy + ry));
    } 
}
class Cloud {
        double x;
        int y, width, height, speed;

        public Cloud(double x, int y, int width, int height, int speed) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
            this.speed = speed;
        } 
    }
    
